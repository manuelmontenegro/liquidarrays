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

@file:Suppress("MemberVisibilityCanBePrivate")

package es.ucm.caviart.goal

/**
 * This module implements the goal generator systems for CLIR programs
 * annotated with Liquid types.
 *
 * It assumes that all the function definitions are annotated with qualified
 * types, where qualifiers can be assertions or applications of template variables.
 */


import es.ucm.caviart.utils.FreshNameGenerator
import es.ucm.caviart.ast.*
import es.ucm.caviart.qstar.Substitution
import es.ucm.caviart.qstar.applySubstitution
import es.ucm.caviart.typecheck.GlobalEnvironment

/**
 * An instances of this class represents a generated goal.
 *
 * @param name Identifier for the goal
 * @param description A description of why this goal was generated
 * @param assumptions Assumptions of the goal
 * @param conclusion Assertion to be proved from the assumptions
 * @param environment Hindley Milner types of all the variables occurring in the goal
 */
data class Goal(val name: String,
                val description: String,
                val assumptions: List<Assertion>,
                val conclusion: Assertion,
                val environment: Map<String, HMType>) {
    override fun toString(): String {
        val envStr = if (environment.isEmpty()) ""
        else """
            |
            |
            |**For all**:
            |
            ${environment.toList().joinToString("\n") { "|  * `${it.first}` of type `${it.second.toSExp().toString()}`" }}
        """.trimMargin("|")

        val assumptionsStr = if (assumptions.isEmpty()) ""
        else """
            |
            |
            |**such that**:
            |
            ${assumptions.toList().joinToString("\n") { "|  * `${it.toSExp().toString()}`" }}
        """.trimMargin("|")

        return """
            |## Goal `$name`
            |
            |$description
            """.trimMargin() + envStr + assumptionsStr + """
            |
            |
            |**Prove:** `${conclusion.toSExp().toString()}`
        """.trimMargin("|")
    }
}


/**
 * It converts a type into a qualified type, introducing
 * the `true` qualifier if necessary.
 */
private fun Type.toQualType(): QualType = when (this) {
    is QualType -> this
    is HMType -> QualType(FreshNameGenerator.nextName("_NU"), this, True())
    else -> throw InvalidASTException(this)
}

/**
 * It returns the qualified type of an atomic expression.
 */
fun generateForAtomic(atomic: Atomic, localEnvironment: List<EnvironmentEntry>): QualType = when (atomic) {
    is Literal -> {
        // Qualified type of a literal: { nu | nu = c }
        val newNu = FreshNameGenerator.nextName("_NU")
        QualType(newNu, atomic.type, PredicateApplication("=", listOf(Variable(newNu), atomic)))
    }

    is Variable -> {
        // Qualified type of a variable: { nu | nu = x }
        val newNu = FreshNameGenerator.nextName("_NU")
        // We look for the variable into the environment
        val hmType: HMType =
                (localEnvironment.findLast { it is VariableEntry && it.variable == atomic.name } as VariableEntry).type.HMType
        QualType(newNu, hmType, PredicateApplication("=", listOf(Variable(newNu), atomic)))
    }

    else -> throw InvalidASTException(atomic)
}

/**
 * It returns the qualified type(s) of a binding expression, possibly
 * generating goals as a side effect.
 *
 * @param expression Expression to analyze
 * @param localEnvironment Environment with the qualified types of the variables in scope, and the assertions that
 *                          hold in that scope
 * @param globalEnvironment Environment with the types of global definitions
 * @param generatedGoals The (mutable) list of generated goals.
 *
 * @return List of qualified types of the expression (singleton list if it is not a tuple)
 */
fun generateForBindingExpression(expression: BindingExpression,
                                 localEnvironment: List<EnvironmentEntry>,
                                 globalEnvironment: GlobalEnvironment,
                                 generatedGoals: MutableList<Goal>): List<TypedVar> = when (expression) {
    is Atomic -> listOf(TypedVar(FreshNameGenerator.nextName("_X"), generateForAtomic(expression, localEnvironment)))


    is FunctionApplication -> {
        val signature = globalEnvironment.programFunctions[expression.name]!!

        // We generate a substitution from the parameters to the actual arguments
        val substitution: Substitution = signature.input.zip(expression.arguments).map { (parameter, argument) ->
            parameter.varName to argument
        }.toMap()

        // For each pair (parameter, argument)
        expression.arguments.zip(signature.input).map { (argument, parameter) ->
            // We obtain its qualified type
            val typeArgument = generateForAtomic(argument, localEnvironment)

            if (parameter.type is QualType) {
                // If the type of the parameter is qualified, we must add a goal.
                // The type of the argument must be a subtype of the parameter

                // We transform the type of the parameter so that it has the variables of the arguments, including
                // the nu of the argument.
                val conclusion = parameter.type.qualifier.applySubstitution(substitution + (parameter.type.nu to Variable(typeArgument.nu)))
                val description = "Precondition of parameter ${parameter.varName} in call to ${expression.name}"

                // And generate the goal
                generatedGoals.add(environmentToGoal(localEnvironment + VariableEntry(typeArgument.nu, typeArgument), conclusion, description))
            }
        }

        // Now we have to generate the type of the result. If the result is a tuple
        // type, we must change the replace of the variables denoting each component
        // of the tuple with fresh names. The variable `currentSubstitution` will maintain
        // the substitution with those new bindings.
        var currentSubstitution = substitution
        signature.output.map {
            val componentVar = FreshNameGenerator.nextName("_X")
            if (it.type is QualType) {
                val newQualifier = it.type.qualifier.applySubstitution(currentSubstitution)
                currentSubstitution += it.varName to Variable(componentVar)
                // We put the new qualified component, with the fresh name
                TypedVar(componentVar, QualType(it.type.nu, it.type.HMType, newQualifier))
            } else {
                // The same, but with a `true` qualifier.
                TypedVar(componentVar, QualType(FreshNameGenerator.nextName("_NU"), it.type.hmType, True()))
            }
        }
    }

    is Tuple -> {
        expression.arguments.map { TypedVar(FreshNameGenerator.nextName("_X"), generateForAtomic(it, localEnvironment)) }
    }

    is ConstructorApplication -> {
        generateForBindingExpression(
                FunctionApplication(expression.name, expression.arguments),
                localEnvironment,
                globalEnvironment, generatedGoals
        )
    }

    else -> throw InvalidASTException(expression)
}

/**
 * It generates the goals given a CLIR expression, given a set of expected
 * results. The goals reflect the fact that the inferred types are subtypes
 * of the expected results.
 *
 * @param functionName Name of the function in which this expression is contained
 * @param expression Expression to analyze
 * @param localEnvironment Environment with the variables in scope and assertions
 * @param globalEnvironment Global environment with already defined functions
 * @param generatedGoals List in which the goals will be inserted
 * @param expectedResults Expected types of the expressions. The inferred types
 *                        must be subtypes of these expected types, and goals will
 *                        be generated in order to ensure this
 */
fun generateForExpression(functionName: String,
                          expression: Term,
                          localEnvironment: List<EnvironmentEntry>,
                          globalEnvironment: GlobalEnvironment,
                          generatedGoals: MutableList<Goal>,
                          expectedResults: List<TypedVar>) {
    when (expression) {
        is BindingExpression -> {
            // First we infer the types of the binding expression
            val typedVars = generateForBindingExpression(expression, localEnvironment, globalEnvironment, generatedGoals)

            // Now we have to prove that these are subtypes of the expected results

            // We generate a substitution from the names of the components in the expected results tuple to
            // the names in the inferred tuple
            val substitution: Substitution = expectedResults.map { it.varName }.zip(typedVars.map { Variable(it.varName) }).toMap()

            // The `currentEnvironment` variable will also contain the components of the tuple as they are traversed
            var currentEnvironment = localEnvironment
            typedVars.zip(expectedResults).forEachIndexed { index, (typedVar, expected) ->
                if (expected.type is QualType) {
                    val description = "The type of ${expression.toSExp()} must match the type of the result #${index + 1} of $functionName"
                    val conclusion =
                            expected.type.qualifier.applySubstitution(substitution + mapOf(expected.type.nu to Variable(typedVar.varName)))
                    currentEnvironment += VariableEntry(typedVar.varName, typedVar.type as QualType)
                    generatedGoals.add(
                            environmentToGoal(currentEnvironment, conclusion, description)
                    )
                }
            }
        }


        is Let -> {
            // We infer the types of the binding expression
            val bindingTypedVars = generateForBindingExpression(expression.bindingExpression, localEnvironment, globalEnvironment, generatedGoals)

            // We have to add the bound variables to the environment, while replacing the component variables
            // of the inferred tuples for the bound variables in the let expression.
            var currentSubst: Substitution = mapOf()
            var newEnvironment = localEnvironment

            expression.bindings.zip(bindingTypedVars).forEach { (binding, typedVar) ->
                val newType = if (typedVar.type is QualType) {
                    val newQualifier = typedVar.type.qualifier.applySubstitution(currentSubst + mapOf(typedVar.type.nu to Variable(binding.varName)))
                    currentSubst += typedVar.varName to Variable(binding.varName)
                    QualType(typedVar.type.nu, typedVar.type.HMType, newQualifier)
                } else (typedVar.type as QualType)
                newEnvironment += VariableEntry(binding.varName, newType)
            }

            // We analyze the main expression with the updated environment
            generateForExpression(functionName, expression.mainExpression, newEnvironment, globalEnvironment, generatedGoals, expectedResults)
        }

        is LetFun -> {
            // We just generate the goals for each definition and for the main expressions.
            // All the newly defined functions are already in the global environemnt, so we
            // don't have to update it before analyzing the main goal.
            expression.defs.forEach {
                generateForDefinition(it, localEnvironment, globalEnvironment, generatedGoals)
            }

            generateForExpression(functionName, expression.mainExpression, localEnvironment, globalEnvironment, generatedGoals, expectedResults)
        }

        is Case -> {
            val discriminantHMType = generateForAtomic(expression.discriminant, localEnvironment)

            expression.branches.forEach {

                when (it.pattern) {
                    is LiteralPattern -> {
                        // In case of literal patterns, we add the equation x = p to the environment where x
                        // is the discriminant and p is the pattern
                        val nextEntry = when {
                            expression.discriminant is Literal && expression.discriminant.value == it.pattern.literal ->
                                AssertionEntry(True())

                            expression.discriminant is Literal && expression.discriminant.value != it.pattern.literal ->
                                AssertionEntry(False())

                            expression.discriminant is Variable && it.pattern.literal == "true" ->
                                AssertionEntry(BooleanVariable(expression.discriminant.name))

                            expression.discriminant is Variable && it.pattern.literal == "false" ->
                                AssertionEntry(Not(BooleanVariable(expression.discriminant.name)))

                            else -> AssertionEntry(PredicateApplication("=", listOf(expression.discriminant, Literal(it.pattern.literal, discriminantHMType.HMType))))
                        }
                        generateForExpression(functionName, it.expression, localEnvironment + nextEntry, globalEnvironment, generatedGoals, expectedResults)
                    }

                    is ConstructorPattern -> {
                        // In case of constructor patterns, we have to apply the substitution from the
                        // variables in the signature of the constructor to the pattern variables.

                        // First we obtain the signature
                        val signature = globalEnvironment.programFunctions[it.pattern.constructorName]!!

                        // We generate an initial substitution from the input variables in the signature to
                        // the pattern variables.
                        val substitution: Substitution = signature.input.map { it.varName }.zip(it.pattern.constructorArgs.map { Variable(it) }).toMap()

                        var environmentWithPatternVariables = localEnvironment
                        it.pattern.constructorArgs.zip(signature.input).forEach { (patVar, typedParam) ->
                            environmentWithPatternVariables += VariableEntry(patVar,
                                    if (typedParam.type is QualType)
                                        QualType(typedParam.type.nu, typedParam.type.HMType, typedParam.type.qualifier.applySubstitution(substitution))
                                    else
                                        QualType(FreshNameGenerator.nextName("_NU"), typedParam.type as HMType, True())
                            )
                        }

                        // We assume that the output type has only one component
                        val outputType = signature.output[0].type
                        val newEnvironment =
                                if (outputType is QualType) {
                                    environmentWithPatternVariables +
                                            AssertionEntry(outputType.qualifier.applySubstitution(substitution))
                                } else environmentWithPatternVariables
                        generateForExpression(functionName, it.expression, newEnvironment, globalEnvironment, generatedGoals, expectedResults)
                    }

                    else -> throw InvalidASTException(it.pattern)
                }
            }

            if (expression.defaultBranch != null) {
                generateForExpression(functionName, expression.defaultBranch, localEnvironment, globalEnvironment, generatedGoals, expectedResults)
            }
        }
    }
}

/**
 * It generates the goals for a given function definition.
 *
 * @param definition Definition to analyze
 * @param localEnvironment Environment with variables in scope and assertions
 * @param globalEnvironment Global environment with the types of already defined functions
 * @param generatedGoals The list in which the new goals will be added
 */
fun generateForDefinition(definition: FunctionDefinition,
                          localEnvironment: List<EnvironmentEntry>,
                          globalEnvironment: GlobalEnvironment,
                          generatedGoals: MutableList<Goal>) {

    val preliminaryEnvironment: List<EnvironmentEntry> =
            localEnvironment + definition.inputParams.map { VariableEntry(it.varName, (it.type as QualType).copy(qualifier = True())) }

    // We add the function parameters to the environment and generate
    // the goals `precondition ==> qualifier of the parameter`
    val nextEnvironment = definition.inputParams.fold(preliminaryEnvironment) { env, inputParam ->
        // We replace the nu variable in the qualifier by the name of the
        // coresponding parameter, obtaining a `conclusion`
        val qualType = inputParam.type.toQualType()
        val conclusion = qualType.qualifier.applySubstitution(mapOf(qualType.nu to Variable(inputParam.varName)))

        // The precondition must imply the qualifier of the parameter
        val description = "Precondition of ${definition.name} must imply the qualifier of its parameter ${inputParam.varName}"
        val newGoal = environmentToGoal(env + AssertionEntry(definition.precondition), conclusion, description)
        generatedGoals.add(newGoal)

        // Finally, we add the parameter to the environment
        env + VariableEntry(inputParam.varName, qualType)
    }

    // Now we generate the goal that forces the output parameters to imply the postcondition
    val endEnvironment = definition.outputParams.fold(nextEnvironment) { env, outputParam ->
        val qualType = outputParam.type.toQualType()
        env + VariableEntry(outputParam.varName, qualType)
    }
    val conclDescription = "The qualified type of the result of ${definition.name} must imply its postcondition"
    generatedGoals.add(environmentToGoal(endEnvironment, definition.postcondition, conclDescription))

    // Finally, we generate the goals for the body of the functions's body
    generateForExpression(definition.name, definition.body, nextEnvironment, globalEnvironment, generatedGoals, definition.outputParams)
}

/**
 * It returns a `Goal` object from a given environment and conclusion
 */
fun environmentToGoal(localEnvironment: List<EnvironmentEntry>, conclusion: Assertion, description: String): Goal {

    val assumptions = mutableListOf<Assertion>()
    val environmentHM = mutableMapOf<String, HMType>()

    // We transform the localEnvironment into a list of assertions and generate
    // the Hindley-Milner environment
    localEnvironment.forEach {
        when (it) {
            is VariableEntry -> {

                val qualType = it.type
                environmentHM[it.variable] = qualType.HMType

                // We generate the assumption by substituting the nu variable of the
                // qualifier by the name of the variable.
                assumptions.add(qualType.qualifier.applySubstitution(mapOf(qualType.nu to Variable(it.variable))))

                /*
                // REMOVED: This is a Z3 specific detail. It should not be here
                // The Z3WeakeningAlgorithm module will take care of this
                //

                // If the current variable (say `x`) is of an array type, we have to add a suplementary
                // _x_len variable to hold its length. This variable is assumed to be greater than 0
                if (qualType.HMType is ConstrType && qualType.HMType.typeConstructor == "array") {
                    val lenVar = "_${it.variable}_len"
                    environmentHM[lenVar] = ConstrType("int")
                    assumptions.add(PredicateApplication(">=", listOf(Variable(lenVar), Literal("0", ConstrType("int")))))
                }
                */
            }

            is AssertionEntry -> {
                assumptions.add(it.assertion)
            }
        }
    }

    return Goal(FreshNameGenerator.nextName("G"), description, assumptions, conclusion, environmentHM)
}
