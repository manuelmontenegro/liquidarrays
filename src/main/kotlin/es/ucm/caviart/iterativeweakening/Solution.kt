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

/**
 * This package provides definitions for classes that model the Kappa and Mu
 * tempate variables. They are similar to the KappaDefinition and MuDefinition
 * classes of Syntax.kt, but these ones provide extra information about the
 * QE sets (namely, which arrays are accesed by each qualifier).
 */

package es.ucm.caviart.iterativeweakening

import es.ucm.caviart.ast.Assertion
import es.ucm.caviart.ast.HMType
import es.ucm.caviart.ast.HMTypedVar

/**
 * Kappa template predicates
 *
 * @param name Name of the definition
 * @param arguments List of arguments (including the initial `nu`)
 * @param qStar List of all available qualifiers. The kappa will be instantiated
 *              to a subset of these predicates.
 */
data class Kappa(val name: String,
                 val arguments: List<HMTypedVar>,
                 val qStar: List<Assertion>)

/**
 * Mu template predicates
 *
 * @param name Name of the definition
 * @param arguments List of arguments (including the initial `nu`)
 * @param boundVar1 Name of the bound variable in the QI and QE set, and the first bound variable in QII and QEE
 * @param boundVar2 Name of the second bound variable in the QII and QEE sets
 * @param qI List of all available index qualifiers for singly-indexed qualifiers
 * @param qE List of all available array qualifiers for singly-indexed qualifiers
 * @param qII List of all available index qualifiers for doubly-indexed qualifiers
 * @param qEE List of all available array qualifiers for doubly-indexed qualifiers
 * @param qLen List of all available qualifiers for denoting the array length
 */
data class Mu(val name: String,
              val arguments: List<HMTypedVar>,
              val boundVar1: String,
              val boundVar2: String,
              val qI: List<Assertion>,
              val qE: List<QEStarElement>,
              val qII: List<Assertion>,
              val qEE: List<QEEStarElement>,
              val qLen: List<Assertion>)

/**
 * This class contains a qualifier plus all the array accesses that the qualifier involves.
 * Each array is associated with the type of its elements.
 */
data class QEStarElement(val qualifier: Assertion, val arrayNames: List<Pair<String, HMType>>)

/**
 * This class contains a qualifier plus all the array accesses that the qualifier involves.
 *
 * `arrayNames1` contains the accesses indexed by the first bound variable, and `arrayNames2` contains
 * the accesses indexed by the second bound variable.
 *
 * Each array is associated with the type of its elements.
 */
data class QEEStarElement(val qualifier: Assertion, val arrayNames1: List<Pair<String, HMType>>, val arrayNames2: List<Pair<String, HMType>>)


/**
 * A solution of the iterative weakening algorithm.
 *
 * It maps each kappa and mu to its corresponding substitution, which is
 * represented respectively by instances of KappaSolution and MuSolution.
 */
data class Solution(val kappas: MutableMap<String, KappaSolution>,
                    val mus: MutableMap<String, MuSolution>)

/**
 * A KappaSolution is just a set of the positions of qualifiers
 * taken from the qStar list of the corresponding Kappa.
 */
typealias KappaSolution = Set<Int>

/**
 * The same for MuSolution, but there could be more than one single
 * refinement, and more than one double refinement. Each refinement
 * is of the form: `\forall i. lhs ==> rhs`.
 */
data class MuSolution(
        val singleRefinements: List<Refinement>,
        val doubleRefinements: List<Refinement>,
        val qLen: Set<Int>)

data class Refinement(val lhs: Set<Int>, val rhs: Set<Int>)


