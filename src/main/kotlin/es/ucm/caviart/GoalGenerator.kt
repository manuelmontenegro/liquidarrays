package es.ucm.caviart

import java.util.*

class Goal(val description: String,
           val assumptions: List<Assertion>,
           val conclusion: Assertion,
           val environment: Map<String, HMType>)


private fun Type.toQualType(): QualType = when (this) {
    is QualType -> this
    is HMType -> QualType(FreshNameGenerator.nextName("_NU"), this, True())
    else -> throw InvalidASTException(this)
}

fun generateForAtomic(atomic: Atomic, localEnvironment: Map<String, HMType>): QualType = when (atomic) {
    is Literal -> {
        val newNu = FreshNameGenerator.nextName("_NU")
        QualType(newNu, atomic.type, PredicateApplication("=", listOf(Variable(newNu), atomic)))
    }

    is Variable -> {
        val newNu = FreshNameGenerator.nextName("_NU")
        QualType(newNu, localEnvironment[atomic.name]!!, PredicateApplication("=", listOf(Variable(newNu), atomic)))
    }

    else -> throw InvalidASTException(atomic)
}

fun generateForBindingExpression(expression: BindingExpression,
                                 lastEntry: EnvironmentEntry,
                                 globalEnvironment: GlobalEnvironment,
                                 localEnvironment: Map<String, HMType>,
                                 generatedGoals: MutableList<Goal>): List<QualType> = when (expression) {
    is Atomic -> listOf(generateForAtomic(expression, localEnvironment))


    is FunctionApplication -> {
        val signature = globalEnvironment.programFunctions[expression.name]!!

        val substitution: Substitution = signature.input.zip(expression.arguments).map { (parameter, argument) ->
            parameter.varName to argument
        }.toMap()

        expression.arguments.zip(signature.input).map { (argument, parameter) ->
            val typeArgument = generateForAtomic(argument, localEnvironment)
            if (parameter.type is QualType) {
                val conclusion = parameter.type.qualifier.applySubstitution(substitution + (parameter.type.nu to Variable(typeArgument.nu)))
                val description = "Precondition of parameter ${parameter.varName} in call to ${expression.name}"
                generatedGoals.add(entryToGoal(AssertionEntry(typeArgument.qualifier, lastEntry), conclusion, description))
            }
        }

        signature.output.map {
            val nuVar = FreshNameGenerator.nextName("_NU")
            if (it.type is QualType) {
                it.type.copy(nu = nuVar, qualifier = it.type.qualifier.applySubstitution(substitution + (it.type.nu to Variable(nuVar))))
            } else {
                QualType(nuVar, it.type.hmType, True())
            }
        }
    }

    is ConstructorApplication -> {
        generateForBindingExpression(
                FunctionApplication(expression.name, expression.arguments),
                lastEntry, globalEnvironment, localEnvironment, generatedGoals
        )
    }

    else -> throw InvalidASTException(expression)
}

fun generateForExpression(functionName: String,
                          expression: Term,
                          lastEntry: EnvironmentEntry,
                          globalEnvironment: GlobalEnvironment,
                          localEnvironment: Map<String, HMType>,
                          generatedGoals: MutableList<Goal>,
                          expectedResults: List<QualType>) {
    when (expression) {
        is BindingExpression -> {
            val types = generateForBindingExpression(expression, lastEntry, globalEnvironment, localEnvironment, generatedGoals)
            types.zip(expectedResults).forEachIndexed { index, (type, expected) ->
                val description = "The type of ${expression.toSExp()} must match the type of the result #$index of $functionName"
                val conclusion = expected.qualifier.applySubstitution(mapOf(expected.nu to Variable(type.nu)))
                generatedGoals.add(
                        entryToGoal(AssertionEntry(type.qualifier, lastEntry), conclusion, description)
                )
            }
        }


        is Let -> {
            val bindingTypes = generateForBindingExpression(expression.bindingExpression, lastEntry, globalEnvironment, localEnvironment, generatedGoals)
            val nextEntry = expression.bindings.zip(bindingTypes).fold(lastEntry) { lEntry, (binding, type) ->
                VariableEntry(binding.varName, type, lEntry)
            }
            generateForExpression(functionName, expression.mainExpression, nextEntry, globalEnvironment, localEnvironment, generatedGoals, expectedResults)
        }

        is LetFun -> {
            expression.defs.forEach {
                generateForDefinition(it, lastEntry, globalEnvironment, localEnvironment, generatedGoals)
            }

            generateForExpression(functionName, expression.mainExpression, lastEntry, globalEnvironment, localEnvironment, generatedGoals, expectedResults)
        }

        is Case -> {
            val discriminantHMType = generateForAtomic(expression.discriminant, localEnvironment)
            expression.branches.forEach {
                when (it.pattern) {
                    is LiteralPattern -> {
                        val nextEntry = AssertionEntry(PredicateApplication("=", listOf(expression.discriminant, Literal(it.pattern.literal, discriminantHMType.HMType))), lastEntry)
                        generateForExpression(functionName, it.expression, nextEntry, globalEnvironment, localEnvironment, generatedGoals, expectedResults)
                    }

                    is ConstructorPattern -> {
                        val signature = globalEnvironment.programFunctions[it.pattern.constructorName]!!
                        val substitution: Substitution = signature.input.map { it.varName }.zip(it.pattern.constructorArgs.map { Variable(it) }).toMap()
                        val entryAfterVariables = it.pattern.constructorArgs.zip(signature.input).fold(lastEntry) { entry, (patVar, typedParam) ->
                            VariableEntry(patVar, if (typedParam.type is QualType)
                                typedParam.type.copy(qualifier = typedParam.type.qualifier.applySubstitution(substitution))
                            else
                                QualType(FreshNameGenerator.nextName("_NU"), typedParam.type as HMType, True()), entry)
                        }
                        val outputType = signature.output[0].type
                        val nextEntry = AssertionEntry(
                                if (outputType is QualType)
                                    outputType.qualifier.applySubstitution(substitution)
                                else
                                    True(), entryAfterVariables
                        )
                        val newBindings = it.pattern.constructorArgs.zip(signature.input.map { it.type.hmType }).toMap()
                        generateForExpression(functionName, it.expression, nextEntry, globalEnvironment, localEnvironment + newBindings, generatedGoals, expectedResults)
                    }

                    else -> throw InvalidASTException(it.pattern)
                }
            }
        }
    }
}


fun generateForDefinition(definition: FunctionDefinition,
                          lastEntry: EnvironmentEntry,
                          globalEnvironment: GlobalEnvironment,
                          localEnvironment: Map<String, HMType>,
                          generatedGoals: MutableList<Goal>) {
    val nextEntry = definition.inputParams.fold(lastEntry) { entry, inputParam ->
        val qualType = inputParam.type.toQualType()
        val conclusion = qualType.qualifier
        val description = "Precondition of ${definition.name} must imply the qualifier of its parameter ${inputParam.varName}"
        val newGoal = entryToGoal(AssertionEntry(definition.precondition, entry), conclusion, description)
        generatedGoals.add(newGoal)
        VariableEntry(inputParam.varName, qualType, entry)
    }

    val endEntry = definition.outputParams.fold(nextEntry) { entry, outputParam ->
        val qualType = outputParam.type.toQualType()
        VariableEntry(outputParam.varName, qualType, entry)
    }

    val conclDescription = "The qualified type of the result of ${definition.name} must imply its postcondition"
    generatedGoals.add(entryToGoal(endEntry, definition.postcondition, conclDescription))

    val newBindings = definition.inputParams.map { it.varName to it.type.hmType }.toMap()

    generateForExpression(definition.name, definition.body, nextEntry, globalEnvironment,
            localEnvironment + newBindings, generatedGoals, definition.outputParams.map { it.type.toQualType() })

}

fun entryToGoal(entry: EnvironmentEntry, conclusion: Assertion, description: String): Goal {
    val entryStack = Stack<EnvironmentEntry>()

    var currentEntry: EnvironmentEntry? = entry
    while (currentEntry != null) {
        entryStack.push(currentEntry)
        currentEntry = currentEntry.next
    }

    val assumptions = mutableListOf<Assertion>()
    val environment = mutableMapOf<String, HMType>()

    while (!entryStack.empty()) {
        val currentEntry = entryStack.pop()
        when (currentEntry) {
            is VariableEntry -> {
                val qualType = currentEntry.type
                environment.put(currentEntry.variable, qualType.HMType)
                assumptions.add(qualType.qualifier.applySubstitution(mapOf(qualType.nu to Variable(currentEntry.variable))))

                if (qualType.HMType is ConstrType && qualType.HMType.typeConstructor == "array") {
                    val lenVar = "_${currentEntry.variable}_len"
                    environment.put(lenVar, ConstrType("int"))
                    assumptions.add(PredicateApplication(">=", listOf(Variable(lenVar), Literal("0", ConstrType("int")))))
                }
            }

            is AssertionEntry -> {
                assumptions.add(currentEntry.assertion)
            }
        }
    }

    return Goal(description, assumptions, conclusion, environment)
}
