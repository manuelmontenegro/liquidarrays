package es.ucm.caviart

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Test functions for the Goals.kt module
 *
 * Created by manuel on 14/06/17.
 */

class GoalsTest {
    val kappa1 = Kappa("kappa1", listOf(TypedVar("nu", Type("int")), TypedVar("n", Type("int"))), listOf(
            PredicateApplication("<=", listOf(Literal("0", Type("int")), Variable("nu"))),
            PredicateApplication("<=", listOf(Literal("0", Type("int")), Variable("n"))),
            PredicateApplication("<", listOf(Variable("nu"), Variable("n"))),
            PredicateApplication("<=", listOf(Variable("nu"), Variable("n"))),
            PredicateApplication("<", listOf(Literal("0", Type("int")), Variable("nu"))),
            PredicateApplication("<", listOf(Literal("1", Type("int")), Variable("nu")))
            ))
    val assertion1 = PredicateApplication("<=", listOf(Literal("0", Type("int")), Variable("x")))
    val assertion2 = PredicateApplication("=", listOf(Variable("xp"), FunctionApplication("+", listOf(Variable("x"), Literal("1", Type("int"))))))
    val conclusion = PredicateApplication("kappa1", listOf(Variable("xp"), Variable("n")))
    val conclusion2 = PredicateApplication("<=", listOf(Literal("0", Type("int")), Variable("xp")))
    val wrongConclusion = PredicateApplication("<", listOf(Literal("1", Type("int")), Variable("xp")))

    val G1 = Goal(
            "G1",
            listOf(assertion1, assertion2),
            conclusion,
            mapOf("x" to Type("int"), "xp" to Type("int"), "n" to Type("int")),
            mapOf("kappa1" to kappa1),
            mapOf("kappa1" to UninterpretedFunctionType(listOf(Type("int"), Type("int")), Type("bool")))
    )

    val G2 = Goal(
            "G2",
            listOf(assertion1, assertion2),
            conclusion2,
            mapOf("x" to Type("int"), "xp" to Type("int"), "n" to Type("int")),
            mapOf("kappa1" to kappa1),
            mapOf("kappa1" to UninterpretedFunctionType(listOf(Type("int"), Type("int")), Type("bool")))
    )

    val G3 = Goal(
            "G3",
            listOf(assertion1, assertion2),
            wrongConclusion,
            mapOf("x" to Type("int"), "xp" to Type("int"), "n" to Type("int")),
            mapOf("kappa1" to kappa1),
            mapOf("kappa1" to UninterpretedFunctionType(listOf(Type("int"), Type("int")), Type("bool")))
    )

    @Test fun namedKappasG1() {
        assertEquals(setOf("kappa1"), G1.namedKappas, "The only kappa appearing in G1 is kappa1")
        assertEquals("kappa1", G1.rhsKappa, "kappa1 appears in the rhs of G1")
    }

    @Test fun namedKappasG2() {
        assertEquals(setOf(), G2.namedKappas, "No kappas appearing in G2")
        assertEquals(null, G2.rhsKappa, "no kappa appears in the rhs of G2")
    }

    @Test fun checkG2Valid() {
        val solution = Solution(mutableMapOf())
        assertTrue(G2.check(solution) is Correct, "G2 should be true under every solution")
    }

    @Test fun checkG3Wrong() {
        val solution = Solution(mutableMapOf())
        assertTrue(G3.check(solution) is CannotWeaken, "G3 should not be true under every solution")
    }

    @Test fun checkG1EmptySolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf()))
        assertTrue(G1.check(solution) is Correct, "G1 should be true under the empty solution")
    }

    @Test fun checkG1FullSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(0, 1, 2, 3, 4)))
        assertTrue(G1.check(solution) is KappaWeakened, "G1 should be false under the full solution")
        assertEquals(setOf(0, 4), solution.kappas["kappa1"]!!, "kappa1 should have been weakened to (0, 4)")
    }

    @Test fun checkG1WrongSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(1)))
        assertTrue(G1.check(solution) is KappaWeakened, "G1 should be false under the solution kappa1 nu n = [0 <= n]")
        assertEquals(setOf<Int>(), solution.kappas["kappa1"]!!, "kappa1 should have been weakened to the empty solution")
    }

    @Test fun checkG1GoodSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(0)))
        assertTrue(G1.check(solution) is Correct, "G1 should be true under the solution kappa1 nu n = [0 <= nu]")
    }

    @Test fun checkG1BetterSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(4)))
        assertTrue(G1.check(solution) is Correct, "G1 should be true under the solution kappa1 nu n = [0 < nu]")
    }

    @Test fun checkG1UnnecessarilyBetterSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(0, 4)))
        assertTrue(G1.check(solution) is Correct, "G1 should be true under the solution kappa1 nu n = [0 <= nu, 0 < nu]")
    }

    @Test fun checkG1TooMuchSolutionSolution() {
        val solution = Solution(mutableMapOf("kappa1" to mutableSetOf(0, 4, 5)))
        assertTrue(G1.check(solution) is KappaWeakened, "G1 should be false under the solution kappa1 nu n = [0 <= nu, 0 < nu, 1 < nu]")
        assertEquals(setOf(0, 4), solution.kappas["kappa1"]!!, "kappa1 should have been weakened to [0 <= nu, 0 < nu]")
    }
}
