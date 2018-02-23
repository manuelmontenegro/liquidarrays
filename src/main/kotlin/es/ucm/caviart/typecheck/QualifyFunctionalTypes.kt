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
package es.ucm.caviart.typecheck

import es.ucm.caviart.ast.*
import es.ucm.caviart.utils.mapAccumLeft

// Sets with the kappa and mu definitions generated at the moment
// Global variables for convenience. They are initialized at the
// only public function of this module, so there should not be
// side effects from outside.
private var kappaGenerated: MutableSet<KappaDeclaration> = mutableSetOf()
private var muGenerated: MutableSet<MuDeclaration> = mutableSetOf()

/**
 * It adds a variable/type pair to the list of variables in scope. If it is
 * already in scope, it replaces the old variable.
 */
private fun addToScope(scope: List<HMTypedVar>, variable: String, type: HMType): List<HMTypedVar> =
        scope.filterNot { (v, _) -> v == variable } + listOf(HMTypedVar(variable, type))

/**
 * It returns a kappa definition with the given name and parameters, but without
 * specific Q-Sets
 */
private fun emptyKappa(name: String, nu: HMTypedVar, arguments: List<HMTypedVar>) =
        KappaDeclaration(name, nu, arguments, null)

/**
 * It returns a mu definition with the given name and parameters, but without
 * specific Q-Sets
 */
private fun emptyMu(name: String, nu: HMTypedVar, arguments: List<HMTypedVar>) =
        MuDeclaration(name, nu, arguments, null, null, null, null, null)

/**
 * It add a qualifier to a given type with a template variable. If the type is already qualified, it
 * leaves it untouched.
 */
private fun toQualType(typedVar: TypedVar, defName: String, scope: List<HMTypedVar>): Type {
    return if (typedVar.type is HMType) {
        // If the type is not already qualified, we add a qualifier, which is a mu or
        // kappa application depending on the HM type
        if (typedVar.type is ConstrType && typedVar.type.typeConstructor == "array") {
            // If the HM Type is array, we add a mu
            val name = "_mu_${defName}_${typedVar.varName}"
            val parameters = scope.toList()
            val mu = emptyMu(name, HMTypedVar("nu", typedVar.type), parameters)
            muGenerated.add(mu)
            QualType("nu", typedVar.type, PredicateApplication(name, listOf(Variable("nu")) + parameters.map { Variable(it.varName) }))

        } else {
            // If the HM Type is not array, we add a kappa
            val name = "_kappa_${defName}_${typedVar.varName}"
            val parameters = scope.toList()
            val kappa = emptyKappa(name, HMTypedVar("nu", typedVar.type), parameters)
            kappaGenerated.add(kappa)
            QualType("nu", typedVar.type, PredicateApplication(name, listOf(Variable("nu")) + parameters.map { Variable(it.varName) }))
        }
    } else {
        typedVar.type
    }
}


/**
 * It modifies the types of a function definition so all its types
 * become qualified.
 */
private fun qualifyFunctionDefinition(
        definition: FunctionDefinition,
        scope: List<HMTypedVar>,
        externals: Map<String, FunctionalType>,
        globalEnvironment: GlobalEnvironment
        ): FunctionDefinition {

    val (scopeWithInput, parameters) = definition.inputParams.mapAccumLeft(scope) { sc, parameter ->
        Pair(addToScope(sc, parameter.varName, parameter.type.hmType),
                TypedVar(parameter.varName, toQualType(parameter, definition.name, sc)))
    }

    val expression = qualifyExpression(definition.body, scopeWithInput, externals, globalEnvironment)

    val (_, results) = definition.outputParams.mapAccumLeft(scopeWithInput) { sc, result ->
        Pair(addToScope(sc, result.varName, result.type.hmType),
                TypedVar(result.varName, toQualType(result, definition.name, sc)))
    }

    globalEnvironment.programFunctions[definition.name] = FunctionalType(input = parameters, output = results)
    return definition.copy(inputParams = parameters, outputParams = results, body = expression).addPropertiesFrom(definition)
}

/**
 * It traverses the expression in order to qualify the types contained within the
 * letfuns.
 */
private fun qualifyExpression(expression: Term, scope: List<HMTypedVar>, externals: Map<String, FunctionalType>, globalEnvironment: GlobalEnvironment): Term =
        when (expression) {
            is BindingExpression -> expression

            is Let -> {
                val newScope = expression.bindings.fold(scope) { sc, binding ->
                    addToScope(sc, binding.varName, binding.HMType)
                }
                expression.copy(mainExpression = qualifyExpression(expression.mainExpression, newScope, externals, globalEnvironment)).addPropertiesFrom(expression)
            }

            is LetFun -> {
                expression.copy(
                        defs = expression.defs.map {
                            qualifyFunctionDefinition(it, scope, externals, globalEnvironment)
                        },
                        mainExpression = qualifyExpression(expression.mainExpression, scope, externals, globalEnvironment)
                ).addPropertiesFrom(expression)
            }

            is Case -> {
                expression.copy(
                        branches = expression.branches.map { (pat, exp) ->
                            val newScope = if (pat is ConstructorPattern) {
                                val constrType = externals[pat.constructorName]
                                        ?: throw UndefinedConstructorException(pat.line, pat.column, pat.constructorName)
                                pat.constructorArgs.zip(constrType.input).fold(scope) { sc, (varName, typedVar) ->
                                    addToScope(sc, varName, typedVar.type.hmType)
                                }
                            } else {
                                scope
                            }
                            CaseBranch(pat, qualifyExpression(exp, newScope, externals, globalEnvironment))
                        },
                        defaultBranch = if (expression.defaultBranch != null) qualifyExpression(expression.defaultBranch, scope, externals, globalEnvironment) else null
                ).addPropertiesFrom(expression)
            }


            else -> throw InvalidASTException(expression)
        }

/**
 * It traverses all the definitions in the given verification unit and qualifies
 * the types of their input and output parameters, creating new kappa and mu
 * predicates if neccessary.
 *
 * @param verificationUnit Verification unit to decorate.
 * @return Verification unit with the transformed definitions and the generated
 *         kappa and mu declarations.
 */
fun qualifyVerificationUnit(verificationUnit: VerificationUnit, globalEnvironment: GlobalEnvironment): VerificationUnit {
    kappaGenerated = mutableSetOf()
    muGenerated = mutableSetOf()


    val newDefs = verificationUnit.definitions.map { qualifyFunctionDefinition(it, listOf(), verificationUnit.external, globalEnvironment) }

    return verificationUnit.copy(
            kappaDeclarations = verificationUnit.kappaDeclarations + kappaGenerated,
            muDeclarations = verificationUnit.muDeclarations + muGenerated,
            definitions = newDefs
    ).addPropertiesFrom(verificationUnit)
}

