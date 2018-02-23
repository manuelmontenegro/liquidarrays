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

import es.ucm.caviart.ast.*
import es.ucm.caviart.qstar.applySubstitution
import es.ucm.caviart.utils.FreshNameGenerator

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
 * It converts a kappa declaration into an object of the `Kappa` class.
 */
fun fromKappaDeclaration(kappaDeclaration: KappaDeclaration): Kappa {
    if (kappaDeclaration.qSet == null) {
        throw RuntimeException("Kappa declaration does not have qSet")
    }
    return Kappa(kappaDeclaration.name,
            listOf(kappaDeclaration.nuVar) + kappaDeclaration.parameters,
            kappaDeclaration.qSet.toList()
    )
}

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
 * It converts a mu declaration into an object of the `Kappa` class.
 */
fun fromMuDeclaration(muDeclaration: MuDeclaration): Mu {
    if (muDeclaration.qISet == null) {
        throw RuntimeException("Mu declaration does not have qI set")
    }

    if (muDeclaration.qESet == null) {
        throw RuntimeException("Mu declaration does not have qE set")
    }

    if (muDeclaration.qIISet == null) {
        throw RuntimeException("Mu declaration does not have qII set")
    }

    if (muDeclaration.qEESet == null) {
        throw RuntimeException("Mu declaration does not have qEE set")
    }

    if (muDeclaration.qLenSet == null) {
        throw RuntimeException("Mu declaration does not have qLen set")
    }

    val environment = listOf(muDeclaration.nuVar) + muDeclaration.parameters
    val environmentMap = environment.map { it.varName to it.HMType }.toMap()

    // The Kappa and Mu classes have a single index variable in all the qualifiers.
    // But in the KappaDeclaration and MuDeclaration, each qualifier may have its
    // own index variables, so we choose two arbitrary names for each index and rename
    // the variables of all qualifiers in the KappaDeclaration

    // We prefer the names 'i' and 'j', but if they are already taken, we generate
    // fresh names
    val boundVar1 = if ("i" !in environmentMap.keys) "i" else FreshNameGenerator.nextName("_I")
    val boundVar2 = if ("j" !in environmentMap.keys) "j" else FreshNameGenerator.nextName("_J")

    val newQEs = muDeclaration.qESet.map { it.assertion.applySubstitution(mapOf(it.boundVar to Variable(boundVar1))) }
    val newQEEs = muDeclaration.qEESet.map { it.assertion.applySubstitution(mapOf(it.boundVar1 to Variable(boundVar1), it.boundVar2 to Variable(boundVar2))) }

    return Mu(
            name = muDeclaration.name,
            arguments = environment,
            boundVar1 = boundVar1,
            boundVar2 = boundVar2,
            qI = muDeclaration.qISet.map { it.assertion.applySubstitution(mapOf(it.boundVar to Variable(boundVar1))) },
            qE = newQEs.map {
                val arrayAccess = it.findArrayAccesses(boundVar1, environmentMap)
                QEStarElement(it, arrayAccess.toList())
            },
            qII = muDeclaration.qIISet.map { it.assertion.applySubstitution(mapOf(it.boundVar1 to Variable(boundVar1), it.boundVar2 to Variable(boundVar2))) },
            qEE = newQEEs.map {
                val arrayAccess1 = it.findArrayAccesses(boundVar1, environmentMap)
                val arrayAccess2 = it.findArrayAccesses(boundVar2, environmentMap)
                QEEStarElement(it, arrayAccess1.toList(), arrayAccess2.toList())
            },
            qLen = muDeclaration.qLenSet.toList()
    )
}


/**
 * This class contains a qualifier plus all the array accesses that the qualifier involves.
 *
 * Warning! After the Z3 len removal phase, the arrayNames will be transformed into variables denoting
 * the array lengths
 */
data class QEStarElement(val qualifier: Assertion, val arrayNames: List<String>)

/**
 * This class contains a qualifier plus all the array accesses that the qualifier involves.
 *
 * `arrayNames1` contains the accesses indexed by the first bound variable, and `arrayNames2` contains
 * the accesses indexed by the second bound variable.
 *
 * Warning! After the Z3 len removal phase, the arrayNames will be transformed into variables denoting
 * the array lengths
 */
data class QEEStarElement(val qualifier: Assertion, val arrayNames1: List<String>, val arrayNames2: List<String>)


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


