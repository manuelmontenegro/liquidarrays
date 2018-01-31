/*
    Copyright (c) 2018 Manuel Montenegro

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
*/
@file:Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")

package es.ucm.caviart.typecheck

import es.ucm.caviart.ast.*


/**
 * Environment with the types of the external components needed to typecheck
 * a program: built-in and external functions, predicates, etc.
 */
data class GlobalEnvironment(
        /**
         * Uninterpreted functions occurring in assertions. For example, the
         * arithmetic operators.
         */
        val logicFunctions: Map<String, UninterpretedFunctionType>,

        /**
         * Uninterpreted predicates occurring in assertions. For example, the
         * relational operators (less than, greater than, etc.)
         */
        val logicPredicates: Map<String, List<HMType>>,

        /**
         * Qualified types of *program* functions. It is a mutable map since we
         * add functions as we traverse the definitions of the AST.
         */
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
    is ConstrType -> ConstrType(type.typeConstructor, type.arguments.map { instantiate(it, mapping) })
    else -> throw InvalidASTException(type)
}

/**
 * It typechecks a function definition and returns its functional type
 *
 * @param definition Definition to check
 * @param globalEnvironment Environment with the types of function definitions (even those that are not in scope)
 * @param environment Environment with local variables.
 */
fun checkFunctionDefinition(definition: FunctionDefinition,
                            globalEnvironment: GlobalEnvironment,
                            environment: Map<String, HMType>): FunctionalType {

    // We check the types of the input parameters and add them to the environment
    val envWithInput = definition.inputParams.fold(environment) { env, (v, type) ->
        checkType(type, globalEnvironment, env)
        env + (v to type.hmType)
    }

    // We check the precondition
    checkAssertion(definition.precondition, globalEnvironment, envWithInput)

    // Now we add the types of the output parameters
    val envWithOutput = definition.outputParams.fold(envWithInput) { env, (v, type) ->
        checkType(type, globalEnvironment, env)
        env + (v to type.hmType)
    }


    // We check the postcondition
    checkAssertion(definition.postcondition, globalEnvironment, envWithOutput)


    // We check the body of the definition, and obtain the type of each result.
    val expTypes = checkExpression(definition.body, globalEnvironment, envWithInput)

    // The number of results given by the expression must be the same as the
    // number of output parameters. Otherwise report an exception.
    if (expTypes.size != definition.outputParams.size) {
        throw NumResultsMismatchException(definition.line, definition.column, definition.outputParams.size, expTypes.size)
    }

    // The HM types of the expressions and the output parameters must be equal.
    val allEqual = expTypes.zip(definition.outputParams.map { it.type.hmType }).all { (t1, t2) -> t1 == t2 }
    if (!allEqual) {
        throw NoInstanceResultsException(definition.line, definition.column, definition.outputParams.map { it.type.hmType }, expTypes)
    }

    return FunctionalType(definition.inputParams, definition.outputParams)
}


/**
 * It typechecks an expression and returns the types of each of its components.
 * If the expression is not a tuple, it returns a singleton list.
 *
 * @param expression Expression to check
 * @param globalEnvironment Environment with the types of function definitions (even those that are not in scope)
 * @param environment Environment with local variables.
 */
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
            // We gather the types of each component. Each one should be a singleton list, since
            // they are atomic expressions
            expression.arguments.flatMap { checkExpression(it, globalEnvironment, environment) }
        }

        is Let -> {
            // We get the types of the bound expression
            val typesBinding = checkExpression(expression.bindingExpression, globalEnvironment, environment)

            // There must be as many types as bound variables, and the types must be the same
            if (expression.bindings.size != typesBinding.size) {
                throw LetNumberBindingsMismatchException(line, column, expression.bindings.size, typesBinding.size)
            }

            val allEqual = expression.bindings.map { it.HMType }.zip(typesBinding).all { (t1, t2) -> t1 == t2 }

            if (!allEqual) {
                throw LetTypeMismatchException(line, column, expression.bindings.map { it.HMType }, typesBinding)
            }

            // We add the types of the bound variables to the local environment, and check the main expression
            checkExpression(expression.mainExpression, globalEnvironment,
                    environment + expression.bindings.map { it.varName to it.HMType }.toMap())
        }

        is LetFun -> {
            // We add the types of the functions to the environment, and check whether there are duplicate names
            expression.defs.forEach {
                if (it.name in globalEnvironment.programFunctions.keys) throw DuplicateFunctionDefinition(it.line, it.column, it.name)
                globalEnvironment.programFunctions[it.name] = FunctionalType(it.inputParams, it.outputParams)
            }

            // With all the new functions in the environment, we check each of them
            expression.defs.forEach { checkFunctionDefinition(it, globalEnvironment, environment) }

            // And lastly the main expression
            checkExpression(expression.mainExpression, globalEnvironment, environment)

            // We do not remove the types of the functions, since we want the environment to contain the types
            // of all the functions in the program; no matter whether they are in scope or not.
        }

        is Case -> {
            // Typecheck the discriminant.
            val typeDiscriminant = checkExpression(expression.discriminant, globalEnvironment, environment)[0]

            if (expression.branches.isEmpty()) {
                throw NoBranchesException(expression.line, expression.column)
            }
            // Typecheck each branch, and obtain the list of types
            val typeBranches = expression.branches.map {
                // Add the pattern variables to the environment
                val newEnvironment = extendEnvironment(it.pattern, typeDiscriminant, globalEnvironment, environment)
                // And typecheck the expression of that branch
                checkExpression(it.expression, globalEnvironment, newEnvironment)
            }

            // We add the type of the 'default' branch, if there is any
            val typeBranchesInclDefault = if (expression.defaultBranch != null) {
                typeBranches + listOf(checkExpression(expression.defaultBranch, globalEnvironment, environment))
            } else {
                typeBranches
            }

            // We check whether all the branches return the same number of types
            typeBranchesInclDefault.zipWithNext().forEach { (types1, types2) ->
                val valid = (types1.size == types2.size) and (types1.zip(types2).all { (t1, t2) -> t1 == t2 })
                if (!valid) {
                    throw IncompatibleCaseBranchesException(expression.line, expression.column, types1, types2)
                }
            }

            // Since all the branches have the same type, we return the type of the first one
            typeBranchesInclDefault[0]
        }


        else -> throw InvalidASTException(expression)
    }
}

/**
 * It returns an extended environment with the variables given in the pattern.
 *
 * @param pattern Pattern whose variables will be included in the result environment
 * @param typeDiscriminant Type of the case discriminant
 * @param globalEnvironment Environment with function definitions
 * @param environment Environment with locals
 */
private fun extendEnvironment(pattern: Pattern, typeDiscriminant: HMType, globalEnvironment: GlobalEnvironment, environment: Map<String, HMType>): Map<String, HMType> {
    return when (pattern) {
        // If it is a literal, we leave the environment as is
        is LiteralPattern -> environment

        // If it is a constructor pattern
        is ConstructorPattern -> {
            // We obtain its return types
            val signature = globalEnvironment.programFunctions[pattern.constructorName]
                    ?: throw UndefinedConstructorException(pattern.line, pattern.column, pattern.constructorName)

            // There must be only one return type in the constructor's signature
            if (signature.output.size != 1) {
                throw MultipleOutputConstructorException(pattern.line, pattern.column, pattern.constructorName)
            }

            // The number of the parameters of the constructor must match the number of arguments of the pattern
            if (signature.input.size != pattern.constructorArgs.size) {
                throw WrongNumberArgumentsException(pattern.line, pattern.column, pattern.constructorName, signature.input.size, pattern.constructorArgs.size)
            }

            if (signature.output[0].type.hmType != typeDiscriminant) {
                throw PatternTypeMismatchException(pattern.line, pattern.column, signature.output[0].type.hmType, typeDiscriminant)
            }

            // We add the new bindings to the environment
            val newBindings = pattern.constructorArgs.zip(signature.input.map { it.type.hmType }).toMap()
            environment + newBindings
        }
        else -> throw InvalidASTException(pattern)
    }
}

/**
 * It typechecks a function application or a constructor application.
 *
 * @param line Line in the CLIR code where the function/constructor application is located.
 * @param column Column in the CLIR code where the function/constructor application is located.
 * @param name Name of the function or constructor being applied
 * @param arguments List of arguments given to the function or constructor
 * @param globalEnvironment Environment with all function definitions
 * @param environment Local environment with variables.
 */
private fun handleApplication(line: Int,
                              column: Int,
                              name: String,
                              arguments: List<BindingExpression>,
                              globalEnvironment: GlobalEnvironment,
                              environment: Map<String, HMType>): List<HMType> {

    // We obtain the signature of the function
    val signature = globalEnvironment.programFunctions[name] ?:
            throw UndefinedFunctionException(line, column, name)

    // If it does not match the number of arguments, throw an exception
    if (signature.input.size != arguments.size) {
        throw WrongNumberArgumentsException(line, column, name, signature.input.size, arguments.size)
    }

    // Now we get the types of the arguments. All of them must be singleton lists.
    val types = arguments.flatMap { checkExpression(it, globalEnvironment, environment) }

    // The HM types of the parameters must be equal to those in the signature
    val allEqual = signature.input.map { it.type.hmType }.zip(types).all { (t1, t2) -> t1 == t2 }

    if (!allEqual) {
        throw InputTypeMismatchException(line, column, name, signature.input.map { it.type.hmType }, types)
    }

    // We return the HM types of the output
    return signature.output.map { it.type.hmType }
}

/**
 * It typechecks an assertion.
 *
 * @param assertion Assertion to check
 * @param globalEnvironment Environment with the types of function definitions (even those that are not in scope)
 * @param environment Environment with local variables.
 */

fun checkAssertion(assertion: Assertion,
                   globalEnvironment: GlobalEnvironment,
                   environment: Map<String, HMType>) {

    when (assertion) {
        is True -> return

        is False -> return

        is BooleanVariable -> {
            // If it is a boolean variable, it must be of type 'bool'
            val varType = environment[assertion.name]
                    ?: throw UndefinedVariableException(assertion.line, assertion.column, assertion.name)
            if (varType !is ConstrType || varType.typeConstructor != "bool") {
                throw WrongPredicateVariableException(assertion.line, assertion.column, assertion.name, varType)
            }
        }

        is PredicateApplication -> {
            // We get the types of the predicate being applied
            val expectedTypes = globalEnvironment.logicPredicates[assertion.name]
                    ?: throw UndefinedPredicateException(assertion.line, assertion.column, assertion.name)

            // We transform the types of the uninterpreted symbols into functional types to typecheck
            // the arguments.
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

            if (!expectedTypes.zip(argTypes).all { (t1, t2) -> t1 == t2 }) {
                throw InputTypeMismatchException(assertion.line, assertion.column, assertion.name, expectedTypes, argTypes)
            }
        }

        is Not -> checkAssertion(assertion.assertion, globalEnvironment, environment)

        is And -> assertion.conjuncts.forEach { checkAssertion(it, globalEnvironment, environment) }

        is Or -> assertion.disjuncts.forEach { checkAssertion(it, globalEnvironment, environment) }

        is Implication -> assertion.operands.forEach { checkAssertion(it, globalEnvironment, environment) }

        is Iff -> assertion.operands.forEach { checkAssertion(it, globalEnvironment, environment) }

        is ForAll -> checkAssertion(assertion.assertion, globalEnvironment, environment + assertion.boundVars.map { it.varName to it.HMType })

        is Exists -> checkAssertion(assertion.assertion, globalEnvironment, environment + assertion.boundVars.map { it.varName to it.HMType })

        else -> throw InvalidASTException(assertion)
    }
}

/**
 * It checks whether the qualifier of a qualified type is correct.
 *
 * @param type Type to check
 * @param globalEnvironment Environment with the types of function definitions (even those that are not in scope)
 * @param environment Environment with local variables.
 *
 */
fun checkType(type: Type,
              globalEnvironment: GlobalEnvironment,
              environment: Map<String, HMType>) {
    if (type is QualType) {
        checkAssertion(type.qualifier, globalEnvironment, environment + (type.nu to type.HMType))
    }
}

/**
 * It checks whether the qualifiers of a functional type are correct
 *
 * @param type Type to check
 * @param globalEnvironment Environment with the types of function definitions (even those that are not in scope)
 *
 */
fun checkFunctionalType(
            type: FunctionalType,
            globalEnvironment: GlobalEnvironment) {

    // We check the types of the input parameters and add them to the environment
    val envWithInput = type.input.fold(emptyMap<String, HMType>()) { env, (v, type) ->
        checkType(type, globalEnvironment, env)
        env + (v to type.hmType)
    }

    type.output.fold(envWithInput) { env, (v, type) ->
        checkType(type, globalEnvironment, env)
        env + (v to type.hmType)
    }

}

/**
 * It checks whether a generic qualifier is well formed (i.e. all the variables in the qualifier are in scope)
 *
 * @param qualifier Generic Qualifier to check
 * @param globalEnvironment Environment with the types of function definitions (even those that are not in scope)
 *
 */
fun checkGenericQualifier(qualifier: GenericQualifier, globalEnvironment: GlobalEnvironment, localEnvironment: Map<String, HMType>) {
    val newLocalEnv = localEnvironment + qualifier.markers.map { it.varName to it.HMType }.toMap() + mapOf(qualifier.nu.varName to qualifier.nu.HMType)
    checkAssertion(qualifier.assertion, globalEnvironment, newLocalEnv)
}

/**
 * It typechecks a kappa declaration
 *
 * @param kappaDeclaration Kappa declration to check
 * @param globalEnvironment Environment with the types of function definitions
 */
fun checkKappaDeclaration(kappaDeclaration: KappaDeclaration, globalEnvironment: GlobalEnvironment): List<HMType> {
    val localEnvironment = (kappaDeclaration.parameters + kappaDeclaration.nuVar).map { it.varName to it.HMType }.toMap()
    kappaDeclaration.qSet?.forEach { checkAssertion(it, globalEnvironment, localEnvironment) }
    return listOf(kappaDeclaration.nuVar.HMType) + kappaDeclaration.parameters.map { it.HMType }
}

/**
 * It typechecks a mu declaration
 *
 * @param muDeclaration Kappa declration to check
 * @param globalEnvironment Environment with the types of function definitions
 */
fun checkMuDeclaration(muDeclaration: MuDeclaration, globalEnvironment: GlobalEnvironment): List<HMType> {
    val localEnvironment = (muDeclaration.parameters + muDeclaration.nuVar).map { it.varName to it.HMType }.toMap()
    muDeclaration.qISet?.forEach { checkAssertion(it.assertion, globalEnvironment, localEnvironment + (it.boundVar to intType)) }
    muDeclaration.qESet?.forEach { checkAssertion(it.assertion, globalEnvironment, localEnvironment + (it.boundVar to intType)) }
    muDeclaration.qIISet?.forEach { checkAssertion(it.assertion, globalEnvironment, localEnvironment + (it.boundVar1 to intType) + (it.boundVar2 to intType)) }
    muDeclaration.qEESet?.forEach { checkAssertion(it.assertion, globalEnvironment, localEnvironment + (it.boundVar1 to intType) + (it.boundVar2 to intType)) }
    muDeclaration.qLenSet?.forEach { checkAssertion(it, globalEnvironment, localEnvironment) }
    return listOf(muDeclaration.nuVar.HMType) + muDeclaration.parameters.map { it.HMType }
}


/**
 * It typechecks a whole verification unit.
 *
 * @param verificationUnit Verification unit to check
 * @param globalEnvironment Environment with the types of function definitions
 */
fun checkVerificationUnit(verificationUnit: VerificationUnit, globalEnvironment: GlobalEnvironment) {

    // We make a copy of the (mutable) global environment, so that different calls to checkVerificationUnit
    // do not interfere with each other
    val globalEnvironmentCopy = globalEnvironment.copy(programFunctions = globalEnvironment.programFunctions.toMutableMap())

    // We check the types of external function definitions
    verificationUnit.external.forEach {(_, funType) ->
        checkFunctionalType(funType, globalEnvironmentCopy)
    }

    // We add the types of external function definitions to the environment
    verificationUnit.external.forEach { (name, type) ->
        globalEnvironmentCopy.programFunctions[name] = type
    }

    // We check the generic sets: Q and QLen
    verificationUnit.qSet.union(verificationUnit.qLenSet).forEach {
        val localEnv = it.markers.map { typedVar -> typedVar.varName to typedVar.HMType }.toMap() + mapOf(it.nu.varName to it.nu.HMType)
        checkAssertion(it.assertion, globalEnvironmentCopy, localEnv)
    }

    // We check the generic singly-quantified sets: QI and QE
    verificationUnit.qISet.union(verificationUnit.qESet).forEach {
        val localEnv = it.markers.map { typedVar -> typedVar.varName to typedVar.HMType }.toMap() + mapOf(it.nu.varName to it.nu.HMType, it.boundVar to intType)
        checkAssertion(it.assertion, globalEnvironmentCopy, localEnv)
    }

    // We check the generic doubly-quantified sets: QII and QEE
    verificationUnit.qIISet.union(verificationUnit.qEESet).forEach {
        val localEnv = it.markers.map { typedVar -> typedVar.varName to typedVar.HMType }.toMap() +
                mapOf(it.nu.varName to it.nu.HMType, it.boundVar1 to intType, it.boundVar2 to intType)
        checkAssertion(it.assertion, globalEnvironmentCopy, localEnv)
    }

    // We check the kappa definitions and obtain the types of their parameters
    val kappaTypes = verificationUnit.kappaDeclarations.map {
        it.name to checkKappaDeclaration(it, globalEnvironmentCopy)
    }.toMap()

    // We check the mu definitions and obtain the types of their parameters
    val muTypes = verificationUnit.muDeclarations.map {
        it.name to checkMuDeclaration(it, globalEnvironmentCopy)
    }

    // We add the types of the top-level function definitions to the environment
    verificationUnit.definitions.forEach {
        globalEnvironmentCopy.programFunctions[it.name] = FunctionalType(it.inputParams, it.outputParams)
    }

    // Now we add the user provided kappa and mu definitions to the environment
    val newGlobalEnvironment = globalEnvironmentCopy.copy(logicPredicates = globalEnvironmentCopy.logicPredicates + kappaTypes.toMap() + muTypes.toMap())

    // Finally, we typecheck the top-level function definitions
    verificationUnit.definitions.forEach {
        checkFunctionDefinition(it, newGlobalEnvironment, mapOf())
    }
}

open class TypeCheckerException(message: String) : RuntimeException(message)

class UndefinedConstructorException(val line: Int, val column: Int, val constrName: String) :
        TypeCheckerException("In L$line, C$column: Undefined constructor $constrName")

class NumResultsMismatchException(val line: Int, val column: Int, val expected: Int, val given: Int) :
        TypeCheckerException("In L$line, C$column: Mismatch in number of results (expected: $expected, given: $given)")

class NoInstanceResultsException(val line: Int, val column: Int, val declared: List<HMType>, val inferred: List<HMType>) :
        TypeCheckerException("In L$line, C$column: Declared types must be equal or more specific than the inferred ones.\n" +
                "Declared: ${declared.map { it.toSExp() }.joinToString(" ")}\n" +
                "Inferred: ${inferred.map { it.toSExp() }.joinToString(" ")}")

class UndefinedVariableException(val line: Int, val column: Int, val variable: String) :
        TypeCheckerException("In L$line, C$column: Undefined variable $variable")

class UndefinedFunctionException(val line: Int, val column: Int, val function: String) :
        TypeCheckerException("In L$line, C$column: Undefined function $function")

class WrongNumberArgumentsException(val line: Int, val column: Int, val function: String, val expected: Int, val given: Int) :
        TypeCheckerException("In L$line, C$column: wrong number of arguments given to $function\nExpected: $expected\nGiven: $given")

class InputTypeMismatchException(val line: Int, val column: Int, val function: String, val expected: List<HMType>, val given: List<HMType>) :
        TypeCheckerException("In L$line, C$column: input type parameter mismatch in application of $function\n" +
                "Expected: ${expected.map { it.toSExp() }.joinToString(" ")}\n" +
                "Given: ${given.map { it.toSExp() }.joinToString(" ")}")

class LetNumberBindingsMismatchException(val line: Int, val column: Int, val lhs: Int, val rhs: Int) :
        TypeCheckerException("In L$line, C$column: number of results of let expression do not match the number of bindings\nLeft: $lhs\nRight: $rhs")

class LetTypeMismatchException(val line: Int, val column: Int, val lhs: List<HMType>, val rhs: List<HMType>) :
        TypeCheckerException("In L$line, C$column: let assignment type mismatch\n" +
                "Left: ${lhs.map { it.toSExp() }.joinToString(" ")}\n" +
                "Right: ${rhs.map { it.toSExp() }.joinToString(" ")}")

class MultipleOutputConstructorException(val line: Int, val column: Int, val constrName: String) :
        TypeCheckerException("In L$line, C$column: multiple-output constructor $constrName")


class PatternTypeMismatchException(val line: Int, val column: Int, val typePattern: HMType, val typeDiscriminant: HMType) :
        TypeCheckerException("In L$line C$column: the type of the pattern is not compatible with the type of the discriminant\n" +
                "Pattern: ${typePattern.toSExp()}\n" +
                "Discriminant: ${typeDiscriminant.toSExp()}")

class IncompatibleCaseBranchesException(val line: Int, val column: Int, val branch1Type: List<HMType>, val branch2Type: List<HMType>) :
        TypeCheckerException("In L$line, C$column: incompatible case branches\n" +
                "Branch: ${branch1Type.map { it.toSExp() }.joinToString(" ")}\n" +
                "against: ${branch2Type.map { it.toSExp() }.joinToString(" ")}")

class NoBranchesException(val line: Int, val column: Int) :
        TypeCheckerException("In L$line, C$column: a case must have at least one non-default branch")

class DuplicateFunctionDefinition(val line: Int, val column: Int, val name: String) :
        TypeCheckerException("In L$line, C$column: duplicate function name $name\n" +
                "Function names must be pairwise distinct *even if* they are defined in different scopes")

class WrongPredicateVariableException(val line: Int, val column: Int, val name: String, val type: HMType) :
        TypeCheckerException("In L$line, C$column: predicate variable $name must be of type bool, but it is ${type.toSExp()}")

class UndefinedPredicateException(val line: Int, val column: Int, val predicate: String) :
        TypeCheckerException("In L$line, C$column: Unknown predicate $predicate")

