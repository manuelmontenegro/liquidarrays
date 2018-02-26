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
import es.ucm.caviart.iterativeweakening.Kappa
import es.ucm.caviart.iterativeweakening.Mu

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
 *
 * @param kappaIndices A map that gives, for each kappa, the positions of the parameters that have array type
 * @param muIndices A map that gives, for each mu, the positions of the parameters that have array type
 *
 */
fun Goal.removeLen(kappaIndices: Map<String, Set<Int>>, muIndices: Map<String, Set<Int>>): Goal {

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
            assumptions = lenAssumptions + this.assumptions.map { it.removeLen(kappaIndices, muIndices) },
            conclusion = this.conclusion.removeLen(kappaIndices, muIndices),
            environment = this.environment + arrayVars
    )
}

/**
 * Given a list of typed variables, it returns a list with the indices
 * of those variables with an array type.
 *
 *
 */
fun getArrayParams(arguments: List<HMTypedVar>): Set<Int> = arguments.mapIndexed { idx, elem -> idx to elem }
        .filter { (_, hmvar) -> hmvar.HMType is ConstrType && hmvar.HMType.typeConstructor == "array" }
        .map { (idx, _) -> idx }
        .toSet()


/**
 * The same as `Goal.removeLen()`, but it adds fresh arguments to the parameters
 * of a Kappa (as many as parameters of array type).
 *
 * @param kappaIndices A map that gives, for each kappa, the positions of the parameters that have array type
 * @param muIndices A map that gives, for each mu, the positions of the parameters that have array type
 *
 * @return The updated kappa together with the newly generated parameters
 *
 */
fun Kappa.removeLen(kappaIndices: Map<String, Set<Int>>, muIndices: Map<String, Set<Int>>): Pair<Kappa, List<HMTypedVar>> {

    val arrayLenVars =
            this.arguments
                    .filter { (_, type) -> type is ConstrType && type.typeConstructor == "array" }
                    .map { HMTypedVar(it.varName.toLenVar(), intType) }

    return this.copy(
            arguments = this.arguments + arrayLenVars,
            qStar = this.qStar.map { it.removeLen(kappaIndices, muIndices) }
    ) to arrayLenVars
}

/**
 * The same as `Kappa.removeLen()`, but applied to Mu.
 *
 * @param kappaIndices A map that gives, for each kappa, the positions of the parameters that have array type
 * @param muIndices A map that gives, for each mu, the positions of the parameters that have array type
 *
 * @return The updated mu together with the newly generated parameters
 *
 */
fun Mu.removeLen(kappaIndices: Map<String, Set<Int>>, muIndices: Map<String, Set<Int>>): Pair<Mu, List<HMTypedVar>> {
    val arrayLenVars =
            this.arguments
                    .filter { (_, type) -> type is ConstrType && type.typeConstructor == "array" }
                    .map { HMTypedVar(it.varName.toLenVar(), intType) }

    return this.copy(
            arguments = this.arguments + arrayLenVars,
            qI = this.qI.map { it.removeLen(kappaIndices, muIndices) },
            qE = this.qE.map { it.copy(qualifier = it.qualifier.removeLen(kappaIndices, muIndices), arrayNames = it.arrayNames.map { it.toLenVar() }) },
            qII = this.qII.map { it.removeLen(kappaIndices, muIndices) },
            qEE = this.qEE.map { it.copy(qualifier = it.qualifier.removeLen(kappaIndices, muIndices), arrayNames1 = it.arrayNames1.map { it.toLenVar() }, arrayNames2 = it.arrayNames2.map { it.toLenVar() }) },
            qLen = this.qLen.map { it.removeLen(kappaIndices, muIndices) }
    ) to arrayLenVars
}


/**
 * This function changes the applications of len(x) by the x_length variable
 *
 */
private fun BindingExpression.removeLen(): BindingExpression = when {

    this is FunctionApplication && this.name == "len" -> {
        val argument = this.arguments[0] as Variable
        Variable(argument.name.toLenVar())
    }


    else -> this
}

/**
 * This function changes the applications of len(x) by the x_length variable
 *
 * @param kappaIndices A map that gives, for each kappa, the positions of the parameters that have array type
 * @param muIndices A map that gives, for each mu, the positions of the parameters that have array type
 */
private fun Assertion.removeLen(kappaIndices: Map<String, Set<Int>>, muIndices: Map<String, Set<Int>>): Assertion = when {

    this is PredicateApplication && kappaIndices[this.name] != null -> {
        val indices = kappaIndices[this.name]!!
        val arrayArguments =
                this.arguments
                        .filterIndexed { idx, _ -> idx in indices }
                        .map { (it as Variable).name }
        this.copy(arguments = this.arguments + arrayArguments.map { Variable(it.toLenVar()) })
    }

    this is PredicateApplication && muIndices[this.name] != null -> {
        val indices = muIndices[this.name]!!
        val arrayArguments =
                this.arguments
                        .filterIndexed { idx, _ -> idx in indices }
                        .map { (it as Variable).name }

        this.copy(arguments = this.arguments + arrayArguments.map { Variable(it.toLenVar()) })
    }

    this is PredicateApplication -> PredicateApplication(this.name, this.arguments.map { it.removeLen() })


    this is Not -> Not(this.assertion.removeLen(kappaIndices, muIndices))

    this is And -> And(this.conjuncts.map { it.removeLen(kappaIndices, muIndices) })

    this is Or -> Or(this.disjuncts.map { it.removeLen(kappaIndices, muIndices) })

    this is Implication -> Implication(this.operands.map { it.removeLen(kappaIndices, muIndices) })

    this is Iff -> Iff(this.operands.map { it.removeLen(kappaIndices, muIndices) })

    this is ForAll -> ForAll(this.boundVars, this.assertion.removeLen(kappaIndices, muIndices))

    this is Exists -> Exists(this.boundVars, this.assertion.removeLen(kappaIndices, muIndices))

    else -> this

}