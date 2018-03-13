package es.ucm.caviart

import es.ucm.caviart.ast.*
import es.ucm.caviart.iterativeweakening.Kappa
import es.ucm.caviart.iterativeweakening.Solution
import es.ucm.caviart.iterativeweakening.z3.CannotWeaken
import es.ucm.caviart.iterativeweakening.z3.Correct
import es.ucm.caviart.iterativeweakening.z3.KappaWeakened
import es.ucm.caviart.iterativeweakening.z3.Z3Goal
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test functions for the Z3WeakeningAlgorithm.kt module
 *
 * Created by manuel on 14/06/17.
 */

class KappasTest {
    private val kappa1 = Kappa("kappa1", listOf(HMTypedVar("nu", intType), HMTypedVar("n", intType)), listOf(
            PredicateApplication("<=", listOf(Literal("0", intType), Variable("nu"))),
            PredicateApplication("<=", listOf(Literal("0", intType), Variable("n"))),
            PredicateApplication("<", listOf(Variable("nu"), Variable("n"))),
            PredicateApplication("<=", listOf(Variable("nu"), Variable("n"))),
            PredicateApplication("<", listOf(Literal("0", intType), Variable("nu"))),
            PredicateApplication("<", listOf(Literal("1", intType), Variable("nu")))
    ))
    private val assertion1 = PredicateApplication("<=", listOf(Literal("0", intType), Variable("x")))
    private val assertion2 = PredicateApplication("=", listOf(Variable("xp"), FunctionApplication("+", listOf(Variable("x"), Literal("1", intType)))))
    private val conclusion = PredicateApplication("kappa1", listOf(Variable("xp"), Variable("n")))
    private val conclusion2 = PredicateApplication("<=", listOf(Literal("0", intType), Variable("xp")))
    private val wrongConclusion = PredicateApplication("<", listOf(Literal("1", intType), Variable("xp")))

    private val goalG1 = Z3Goal(
            "G1",
            "Goal for testing",
            listOf(assertion1, assertion2),
            conclusion,
            mapOf("x" to intType, "xp" to intType, "n" to intType),
            mapOf("kappa1" to kappa1),
            mapOf(),
            mapOf("kappa1" to setOf()),
            mapOf(),
            mapOf(),
            false, 5000
    )

    private val goalG2 = Z3Goal(
            "G2",
            "Goal for testing",
            listOf(assertion1, assertion2),
            conclusion2,
            mapOf("x" to intType, "xp" to intType, "n" to intType),
            mapOf("kappa1" to kappa1),
            mapOf(),
            mapOf("kappa1" to setOf()),
            mapOf(),
            mapOf(), false, 5000
    )

    private val goalG3 = Z3Goal(
            "G3",
            "Goal for testing",
            listOf(assertion1, assertion2),
            wrongConclusion,
            mapOf("x" to intType, "xp" to intType, "n" to intType),
            mapOf("kappa1" to kappa1),
            mapOf(),
            mapOf("kappa1" to setOf()),
            mapOf(),
            mapOf(), false, 5000
    )

    @Test fun namedKappasG1() {
        assertEquals(setOf("kappa1"), goalG1.mentionedKappas, "The only kappa appearing in G1 is kappa1")
        assertEquals("kappa1", goalG1.rhsKappa, "kappa1 appears in the rhs of G1")
    }

    @Test fun namedKappasG2() {
        assertEquals(setOf(), goalG2.mentionedKappas, "No kappas appearing in G2")
        assertEquals(null, goalG2.rhsKappa, "no kappa appears in the rhs of G2")
    }

    @Test fun checkG2Valid() {
        val solution = Solution(mutableMapOf(), mutableMapOf())
        assertTrue(goalG2.check(solution) is Correct, "G2 should be true under every solution")
    }

    @Test fun checkG3Wrong() {
        val solution = Solution(mutableMapOf(), mutableMapOf())
        assertTrue(goalG3.check(solution) is CannotWeaken, "G3 should not be true under every solution")
    }

    @Test fun checkG1EmptySolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf()), mutableMapOf())
        assertTrue(goalG1.check(solution) is Correct, "G1 should be true under the empty solution")
    }

    @Test fun checkG1FullSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(0, 1, 2, 3, 4)), mutableMapOf())
        assertTrue(goalG1.check(solution) is KappaWeakened, "G1 should be false under the full solution")
        assertEquals(setOf(0, 4), solution.kappas["kappa1"]!!, "kappa1 should have been weakened to (0, 4)")
    }

    @Test fun checkG1WrongSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(1)), mutableMapOf())
        assertTrue(goalG1.check(solution) is KappaWeakened, "G1 should be false under the solution kappa1 nu n = [0 <= n]")
        assertEquals(setOf(), solution.kappas["kappa1"]!!, "kappa1 should have been weakened to the empty solution")
    }

    @Test fun checkG1GoodSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(0)), mutableMapOf())
        assertTrue(goalG1.check(solution) is Correct, "G1 should be true under the solution kappa1 nu n = [0 <= nu]")
    }

    @Test fun checkG1BetterSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(4)), mutableMapOf())
        assertTrue(goalG1.check(solution) is Correct, "G1 should be true under the solution kappa1 nu n = [0 < nu]")
    }

    @Test fun checkG1UnnecessarilyBetterSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(0, 4)), mutableMapOf())
        assertTrue(goalG1.check(solution) is Correct, "G1 should be true under the solution kappa1 nu n = [0 <= nu, 0 < nu]")
    }

    @Test fun checkG1TooMuchSolutionSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(0, 4, 5)), mutableMapOf())
        assertTrue(goalG1.check(solution) is KappaWeakened, "G1 should be false under the solution kappa1 nu n = [0 <= nu, 0 < nu, 1 < nu]")
        assertEquals(setOf(0, 4), solution.kappas["kappa1"]!!, "kappa1 should have been weakened to [0 <= nu, 0 < nu]")
    }
}
