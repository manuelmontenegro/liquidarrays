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

package es.ucm.caviart.iterativeweakening.z3

import es.ucm.caviart.ast.*
import es.ucm.caviart.goal.Goal

/**
 * This module replaces all the calls to len() function in goals
 * and kappas by its corresponding variables _length.
 *
 * It adds the necessary variables to the environment of goals.
 */

fun String.toLenVar() = "${this}_length"

/**
 * It adds the `x_length` variables to the goal in order to replace
 * all the occurrences of `len` in the given goal
 */
fun Goal.removeLen(): Goal {

    // Given the variables with an array type,
    // we create an environment with the new length variables, each one of type int

    val arrayVars =
            this.environment.filter { (_, type) -> type is ConstrType && type.typeConstructor == "array" }
                    .map { (name, _) -> name.toLenVar() to intType }
                    .toMap()

    // Each of these variables must be greater or equal than zero

    val lenAssumptions =
            arrayVars.keys.map { PredicateApplication(">=", listOf(Variable(it), Literal("0", intType))) }

    return this.copy(
            assumptions = lenAssumptions + this.assumptions.map { it.removeLen() },
            conclusion = this.conclusion.removeLen(),
            environment = this.environment + arrayVars
    )
}


/**
 * This function changes the applications of len(x) by the x_length variable
 */
private fun BindingExpression.removeLen(): BindingExpression = when {

    this is FunctionApplication && this.name == "len" -> {
        val argument = this.arguments[0] as Variable
        Variable(argument.name.toLenVar())
    }

    else -> this
}

private fun Assertion.removeLen(): Assertion = when (this) {
    is PredicateApplication -> PredicateApplication(this.name, this.arguments.map { it.removeLen() })

    is Not -> Not(this.assertion.removeLen())

    is And -> And(this.conjuncts.map { it.removeLen() })

    is Or -> Or(this.disjuncts.map { it.removeLen() })

    is Implication -> Implication(this.operands.map { it.removeLen() })

    is Iff -> Iff(this.operands.map { it.removeLen() })

    is ForAll -> ForAll(this.boundVars, this.assertion.removeLen())

    is Exists -> Exists(this.boundVars, this.assertion.removeLen())

    else -> this

}