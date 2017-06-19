package es.ucm.caviart

/**
 * Created by manuel on 14/06/17.
 */

data class Kappa(val name: String, val arguments: List<TypedVar>, val qStar: List<Assertion>)

data class Mu(val name: String,
              val arguments: List<TypedVar>,
              val boundVar1: String,
              val boundVar2: String,
              val qI: List<Assertion>,
              val qE: List<QEStarElement>,
              val qII: List<Assertion>,
              val qEE: List<QEEStarElement>,
              val qLen: List<Assertion>)

data class QEStarElement(val qualifier: Assertion, val arrayNames: List<String>)
data class QEEStarElement(val qualifier: Assertion, val arrayNames1: List<String>, val arrayNames2: List<String>)


data class Solution(val kappas: MutableMap<String, KappaSolution>,
                    val mus: MutableMap<String, MuSolution>)

typealias KappaSolution = MutableSet<Int>

data class MuSolution(
        val singleRefinements: List<Refinement>,
        val doubleRefinements: List<Refinement>,
        val qLen: MutableSet<Int>)

data class Refinement(val lhs: MutableSet<Int>, val rhs: MutableSet<Int>)