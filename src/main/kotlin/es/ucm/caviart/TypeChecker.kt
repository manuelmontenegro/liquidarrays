@file:Suppress("NAME_SHADOWING")

package es.ucm.caviart


data class GlobalEnvironment(
        val logicFunctions: Map<String, UninterpretedFunctionType>,
        val logicPredicates: Map<String, List<HMType>>,
        val programFunctions: MutableMap<String, FunctionalType>
)


fun findInstanceOf(type1: HMType, type2: HMType): Map<String, HMType>? {
    val mapping: MutableMap<String, HMType> = mutableMapOf()

    fun findInstanceRec(type1: HMType, type2: HMType): Boolean = when (type1) {
        is VarType -> {
            val previousType = mapping[type1.variable]
            when (previousType) {
                null -> {
                    mapping[type1.variable] = type2
                    true
                }
                type2 -> true
                else -> false
            }
        }

        is ConstrType -> when {
            type2 !is ConstrType || type2.typeConstructor != type1.typeConstructor -> false
            else -> type1.arguments.zip(type2.arguments).all { (t1, t2) -> findInstanceRec(t1, t2) }
        }

        else -> throw InvalidASTException(type1)
    }

    return if (findInstanceRec(type1, type2)) mapping else null
}


fun instantiate(type: HMType, mapping: Map<String, HMType>): HMType = when (type) {
    is VarType -> mapping[type.variable] ?: type
    is ConstrType -> type.copy(arguments = type.arguments.map { instantiate(it, mapping) })
    else -> throw InvalidASTException(type)
}

fun checkFunctionDefinition(definition: FunctionDefinition,
                            globalEnvironment: GlobalEnvironment,
                            environment: Map<String, HMType>): FunctionalType {
    val envWithInput = definition.inputParams.fold(environment) { env, (v, type) ->
        checkType(type, globalEnvironment, env)
        env + (v to type.hmType)
    }

    checkAssertion(definition.precondition, globalEnvironment, envWithInput)

    val expTypes = checkExpression(definition.body, globalEnvironment, envWithInput)

    if (expTypes.size != definition.outputParams.size) {
        throw NumResultsMismatchException(definition.line, definition.column, definition.outputParams.size, expTypes.size)
    }

    val envWithOutput = definition.outputParams.fold(envWithInput) { env, (v, type) ->
        checkType(type, globalEnvironment, env)
        env + (v to type.hmType)
    }

    if (findInstanceOf(
            ConstrType("dummy", expTypes),
            ConstrType("dummy", definition.outputParams.map { it.type.hmType })
    ) == null) {
        throw NoInstanceResultsException(definition.line, definition.column, definition.outputParams.map { it.type.hmType }, expTypes)
    }

    checkAssertion(definition.postcondition, globalEnvironment, envWithOutput)

    return FunctionalType(definition.inputParams, definition.outputParams)
}


fun checkExpression(expression: Term,
                    globalEnvironment: GlobalEnvironment,
                    environment: Map<String, HMType>): List<HMType> {
    val line = expression.line
    val column = expression.column
    return when (expression) {
        is Literal ->
            listOf(expression.type)

        is Variable ->
            listOf(environment[expression.name] ?: throw UndefinedVariableException(line, column, expression.name))

        is FunctionApplication -> {
            handleApplication(line, column, expression.name, expression.arguments, globalEnvironment, environment)
        }

        is ConstructorApplication -> {
            handleApplication(line, column, expression.name, expression.arguments, globalEnvironment, environment)
        }

        is Tuple -> {
            expression.arguments.flatMap { checkExpression(it, globalEnvironment, environment) }
        }

        is Let -> {
            val typesBinding = checkExpression(expression.bindingExpression, globalEnvironment, environment)

            if (expression.bindings.size != typesBinding.size) {
                throw LetNumberBindingsMismatchException(line, column, expression.bindings.size, typesBinding.size)
            }

            findInstanceOf(
                    ConstrType("dummy", typesBinding),
                    ConstrType("dummy", expression.bindings.map { it.HMType })
            ) ?: throw LetTypeMismatchException(line, column, expression.bindings.map { it.HMType }, typesBinding)

            checkExpression(expression.mainExpression, globalEnvironment, environment + expression.bindings.map { Pair(it.varName, it.HMType) }.toMap())
        }

        is LetFun -> {
            expression.defs.forEach {
                if (it.name in globalEnvironment.programFunctions.keys) throw DuplicateFunctionDefinition(it.line, it.column, it.name)
                globalEnvironment.programFunctions.put(it.name, FunctionalType(it.inputParams, it.outputParams))
            }

            expression.defs.forEach { checkFunctionDefinition(it, globalEnvironment, environment) }

            checkExpression(expression.mainExpression, globalEnvironment, environment)
        }

        is Case -> {
            val typeDiscriminant = checkExpression(expression.discriminant, globalEnvironment, environment)[0]
            val typeBranches = expression.branches.map {
                val newEnvironment = extendEnvironment(it.pattern, typeDiscriminant, globalEnvironment, environment)
                checkExpression(it.expression, globalEnvironment, newEnvironment)
            }

            val typeBranchesInclDefault = if (expression.defaultBranch != null) {
                typeBranches + listOf(checkExpression(expression.defaultBranch, globalEnvironment, environment))
            } else {
                typeBranches
            }

            var mostSpecificResult = typeBranches[0]
            typeBranchesInclDefault.subList(1, typeBranches.size).forEach {
                if (findInstanceOf(
                        ConstrType("dummy", it),
                        ConstrType("dummy", mostSpecificResult)
                ) == null) {
                    if (findInstanceOf(
                            ConstrType("dummy", mostSpecificResult),
                            ConstrType("dummy", it)
                    ) == null) {
                        throw IncompatibleCaseBranchesException(expression.line, expression.column, it, mostSpecificResult)
                    } else {
                        mostSpecificResult = it
                    }
                }
            }

            mostSpecificResult
        }


        else -> throw InvalidASTException(expression)
    }
}

private fun extendEnvironment(pattern: Pattern, typeDiscriminant: HMType, globalEnvironment: GlobalEnvironment, environment: Map<String, HMType>): Map<String, HMType> {
    return when (pattern) {
        is LiteralPattern -> environment
        is ConstructorPattern -> {
            val signature = globalEnvironment.programFunctions[pattern.constructorName]
                    ?: throw UndefinedConstructorException(pattern.line, pattern.column, pattern.constructorName)

            if (signature.output.size > 1) {
                throw MultipleOutputConstructorException(pattern.line, pattern.column, pattern.constructorName)
            }

            if (signature.input.size != pattern.constructorArgs.size) {
                throw WrongNumberArgumentsException(pattern.line, pattern.column, pattern.constructorName, signature.input.size, pattern.constructorArgs.size)
            }

            val outputConstructorType = signature.output[0].type.hmType
            val instantiation = findInstanceOf(outputConstructorType, typeDiscriminant)
                    ?: throw PatternTypeMismatchException(pattern.line, pattern.column, outputConstructorType, typeDiscriminant)

            val instantiatedParams = signature.input.map { instantiate(it.type.hmType, instantiation) }
            val newBindings = pattern.constructorArgs.zip(instantiatedParams)
            environment + newBindings
        }
        else -> throw InvalidASTException(pattern)
    }
}

private fun handleApplication(line: Int, column: Int, name: String, arguments: List<BindingExpression>, globalEnvironment: GlobalEnvironment, environment: Map<String, HMType>): List<HMType> {
    val signature = globalEnvironment.programFunctions[name] ?:
            throw UndefinedFunctionException(line, column, name)

    if (signature.input.size != arguments.size) {
        throw WrongNumberArgumentsException(line, column, name, signature.input.size, arguments.size)
    }

    val types = arguments.flatMap { checkExpression(it, globalEnvironment, environment) }

    val instance = findInstanceOf(
            ConstrType("dummy", signature.input.map { it.type.hmType }),
            ConstrType("dummy", types)
    ) ?: throw InputTypeMismatchException(line, column, name, signature.input.map { it.type.hmType }, types)

    return signature.output.map { instantiate(it.type.hmType, instance) }
}

fun checkAssertion(assertion: Assertion,
                   globalEnvironment: GlobalEnvironment,
                   environment: Map<String, HMType>) {

    when (assertion) {
        is True -> return

        is False -> return

        is BooleanVariable -> {
            val varType = environment[assertion.name]
                    ?: throw UndefinedVariableException(assertion.line, assertion.column, assertion.name)
            if (varType !is ConstrType || varType.typeConstructor != "bool") {
                throw WrongPredicateVariableException(assertion.line, assertion.column, assertion.name, varType)
            }
        }

        is BooleanEquality -> {
            checkAssertion(assertion.assertion1, globalEnvironment, environment)
            checkAssertion(assertion.assertion2, globalEnvironment, environment)
        }

        is PredicateApplication -> {
            val expectedTypes = globalEnvironment.logicPredicates[assertion.name]
                    ?: throw UndefinedPredicateException(assertion.line, assertion.column, assertion.name)

            val newGlobalEnvironment = globalEnvironment.copy(
                    programFunctions = globalEnvironment.logicFunctions.mapValues {
                        FunctionalType(it.value.argumentTypes.map { TypedVar("in", it) },
                                listOf(TypedVar("out", it.value.resultType)))
                    }.toMutableMap()
            )

            val argTypes = assertion.arguments.map {
                checkExpression(it, newGlobalEnvironment, environment)[0]
            }

            if (argTypes.size != expectedTypes.size) {
                throw WrongNumberArgumentsException(assertion.line, assertion.column, assertion.name, expectedTypes.size, argTypes.size)
            }

            if (findInstanceOf(ConstrType("dummy", expectedTypes), ConstrType("dummy", argTypes)) == null) {
                throw InputTypeMismatchException(assertion.line, assertion.column, assertion.name, expectedTypes, argTypes)
            }
        }

        is Not -> checkAssertion(assertion.assertion, globalEnvironment, environment)

        is And -> assertion.conjuncts.forEach { checkAssertion(it, globalEnvironment, environment) }

        is Or -> assertion.disjuncts.forEach { checkAssertion(it, globalEnvironment, environment) }

        is Implication -> assertion.operands.forEach { checkAssertion(it, globalEnvironment, environment) }

        is Iff -> assertion.operands.forEach { checkAssertion(it, globalEnvironment, environment) }

        is ForAll -> checkAssertion(assertion.assertion, globalEnvironment, environment + assertion.boundVars.map { Pair(it.varName, it.HMType) })

        is Exists -> checkAssertion(assertion.assertion, globalEnvironment, environment + assertion.boundVars.map { Pair(it.varName, it.HMType) })

        else -> throw InvalidASTException(assertion)
    }
}

fun checkType(type: Type,
              globalEnvironment: GlobalEnvironment,
              environment: Map<String, HMType>) {
    if (type is QualType) {
        checkAssertion(type.qualifier, globalEnvironment, environment + (type.nu to type.HMType))
    }
}


class UndefinedConstructorException(val line: Int, val column: Int, val constrName: String) :
        RuntimeException("In L$line, C$column: Undefined constructor $constrName")

class NumResultsMismatchException(val line: Int, val column: Int, val expected: Int, val given: Int) :
        RuntimeException("In L$line, C$column: Mismatch in number of results (expected: $expected, given: $given)")

class NoInstanceResultsException(val line: Int, val column: Int, val declared: List<HMType>, val inferred: List<HMType>) :
        RuntimeException("In L$line, C$column: Declared types must be equal or more specific than the inferred ones.\n" +
                "Declared: ${declared.map { it.toSExp() }.joinToString(" ")}\n" +
                "Inferred: ${inferred.map { it.toSExp() }.joinToString(" ")}")

class UndefinedVariableException(val line: Int, val column: Int, val variable: String) :
        RuntimeException("In L$line, C$column: Undefined variable $variable")

class UndefinedFunctionException(val line: Int, val column: Int, val function: String) :
        RuntimeException("In L$line, C$column: Undefined function $function")

class WrongNumberArgumentsException(val line: Int, val column: Int, val function: String, val expected: Int, val given: Int) :
        RuntimeException("In L$line, C$column: wrong number of arguments given to $function\nExpected: $expected\nGiven: $given")

class InputTypeMismatchException(val line: Int, val column: Int, val function: String, val expected: List<HMType>, val given: List<HMType>) :
        RuntimeException("In L$line, C$column: input type parameter mismatch in application of $function\n" +
                "Expected: ${expected.map { it.toSExp() }.joinToString(" ")}\n" +
                "Given: ${given.map { it.toSExp() }.joinToString(" ")}")

class LetNumberBindingsMismatchException(val line: Int, val column: Int, val lhs: Int, val rhs: Int) :
        RuntimeException("In L$line, C$column: number of results of let expression do not match the number of bindings\nLeft: $lhs\nRight: $rhs")

class LetTypeMismatchException(val line: Int, val column: Int, val lhs: List<HMType>, val rhs: List<HMType>) :
        RuntimeException("In L$line, C$column: let assignment type mismatch\n" +
                "Left: ${lhs.map { it.toSExp() }.joinToString(" ")}\n" +
                "Right: ${rhs.map { it.toSExp() }.joinToString(" ")}")

class MultipleOutputConstructorException(val line: Int, val column: Int, val constrName: String) :
        RuntimeException("In L$line, C$column: multiple-output constructor $constrName")


class PatternTypeMismatchException(val line: Int, val column: Int, val typePattern: HMType, val typeDiscriminant: HMType) :
        RuntimeException("In L$line C$column: the type of the pattern is not compatible with the type of the discriminant\n" +
                "Pattern: ${typePattern.toSExp()}\n" +
                "Discriminant: ${typeDiscriminant.toSExp()}")

class IncompatibleCaseBranchesException(val line: Int, val column: Int, val branch1Type: List<HMType>, val branch2Type: List<HMType>) :
        RuntimeException("In L$line, C$column: incompatible case branches\n" +
                "Branch: ${branch1Type.map { it.toSExp() }.joinToString(" ")}\n" +
                "against: ${branch2Type.map { it.toSExp() }.joinToString(" ")}")

class DuplicateFunctionDefinition(val line: Int, val column: Int, val name: String) :
        RuntimeException("In L$line, C$column: duplicate function name $name\n" +
                "Function names must be pairwise distinct *even if* they are defined in different scopes")

class WrongPredicateVariableException(val line: Int, val column: Int, val name: String, val type: HMType) :
        RuntimeException("In L$line, C$column: predicate variable $name must be of type bool, but it is ${type.toSExp()}")

class UndefinedPredicateException(val line: Int, val column: Int, val predicate: String) :
        RuntimeException("In L$line, C$column: Unknown predicate $predicate")

