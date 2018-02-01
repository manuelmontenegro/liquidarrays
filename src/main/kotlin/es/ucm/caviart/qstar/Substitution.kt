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
package es.ucm.caviart.qstar

import es.ucm.caviart.FreshNameGenerator
import es.ucm.caviart.ast.*

/**
 * A substitution is a mapping from variable names to atomic expressions.
 */
typealias Substitution = Map<String, Atomic>


/**
 * It applies a substitution to a given assertion.
 *
 * @param substitution Substitution to apply
 * @return An assertion in which the variables of the substitution have been
 * replaced by their corresponding values in the substitution
 */
fun Assertion.applySubstitution(substitution: Substitution) : Assertion = when(this) {
    is True -> this

    is False -> this

    is BooleanVariable -> {
        // We look the variable in the substitution
        val replacement = substitution[this.name]
        if (replacement == null) {
            // If it does not appear in the substitution, we leave it as is
            BooleanVariable(this.name)
        } else {
            // We replace the variable by its value (we have to convert
            // atomic expressions into assertions)
            when (replacement) {
                is Literal -> {
                    when (replacement.value) {
                        "true" -> True()
                        "false" -> False()
                        else -> throw InvalidASTException(replacement)
                    }
                }

                is Variable -> BooleanVariable(replacement.name)

                else -> throw InvalidASTException(replacement)
            }

        }
    }

    is PredicateApplication -> PredicateApplication(this.name, this.arguments.map { it.applySubstitution(substitution) })

    is Not -> Not(assertion.applySubstitution(substitution))

    is And -> And(conjuncts.map { it.applySubstitution(substitution) })

    is Or -> Or(disjuncts.map { it.applySubstitution(substitution) })

    is Implication -> Implication(operands.map { it.applySubstitution(substitution) })

    is Iff -> Iff(operands.map { it.applySubstitution(substitution) })

    is ForAll -> {
        // We avoid name capture by considering all the bound variables that appear in the range of the
        // substitution, and renaming them if necessary
        val (newVars, newSubstitution) = rebind(boundVars.map { it.varName }, substitution, "_I")
        ForAll(newVars.zip(boundVars).map { (nv, tv) -> HMTypedVar(nv, tv.HMType) }, assertion.applySubstitution(newSubstitution))
    }

    is Exists -> {
        // We avoid name capture by considering all the bound variables that appear in the range of the
        // substitution, and renaming them if necessary
        val (newVars, newSubstitution) = rebind(boundVars.map { it.varName }, substitution, "_I")
        Exists(newVars.zip(boundVars).map { (nv, tv) -> HMTypedVar(nv, tv.HMType) }, assertion.applySubstitution(newSubstitution))
    }

    else -> throw InvalidASTException(this)
}

/**
 * The same as `Assertion.applySubstitution()` but for atomic expressions.
 */
private fun Atomic.applySubstitution(substitution: Substitution): Atomic = when(this) {
    is Literal -> this

    is Variable -> substitution[name] ?: this

    else -> throw InvalidASTException(this)
}

/**
 * The same as `Assertion.applySubstitution()` but for binding expressions.
 */
private fun BindingExpression.applySubstitution(substitution: Substitution) : BindingExpression = when(this) {
    is Atomic -> this.applySubstitution(substitution)

    is FunctionApplication -> FunctionApplication(name, arguments.map { it.applySubstitution(substitution) })

    is ConstructorApplication -> ConstructorApplication(name, arguments.map { it.applySubstitution(substitution) })

    is Tuple -> Tuple(arguments.map { it.applySubstitution(substitution) })

    else -> throw InvalidASTException(this)
}


/**
 * Given a list of variables and a substitution, it checks which of the variables in the list
 * are transformed by the substitution. For all those, fresh names are generated and the substitution
 * is updated such that it transforms the old variables into these fresh names
 *
 * It returns the input list of variables (some of them may have been renamed), and the resulting
 * substitution.
 */
private fun rebind(variables: List<String>, substitution: Substitution, prefix: String): Pair<List<String>, Substitution> {
    var currentSubst = substitution

    val newVariables = variables.map {
        // If the variable is in the domain of the substitution we remove the binding
        // of the substitution.
        if (it in substitution.keys) {
            currentSubst -= it
        }
        // If the variable is in the range of the substitution we update the substitution
        // in order to avoid name capture. The variable is now replaced by a fresh one.
        if (it in substitution.values.filter { it is Variable }.map { (it as Variable).name }) {
            val fresh = FreshNameGenerator.nextName(prefix)
            currentSubst += it to Variable(fresh)
            fresh
        } else {
            it
        }
    }
    return Pair(newVariables, currentSubst)
}

