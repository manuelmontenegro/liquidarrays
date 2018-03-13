package es.ucm.caviart

import es.ucm.caviart.ast.*
import es.ucm.caviart.iterativeweakening.*
import es.ucm.caviart.iterativeweakening.z3.Correct
import es.ucm.caviart.iterativeweakening.z3.MuWeakened
import es.ucm.caviart.iterativeweakening.z3.Z3Goal
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by manuel on 20/06/17.
 */

class MusTest {
    private val goalG1 = Z3Goal(
            "G1",
            "Goal for testing",
            listOf(
                    PredicateApplication(">=", listOf(Variable("a_len"), Literal("0", intType))),
                    PredicateApplication(">=", listOf(Variable("n"), Literal("0", intType))),
                    PredicateApplication("mu1", listOf(Variable("a"), Variable("n"), Variable("a_len"))),
                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("a"), Variable("n"))), Literal("0", intType)))
            ),
            PredicateApplication("mu1", listOf(Variable("a"), FunctionApplication("+", listOf(Variable("n"), Literal("1", intType))), Variable("a_len"))),
            mapOf("a" to ConstrType("array", listOf(intType)), "n" to intType, "a_len" to intType),
            mapOf(),
            mapOf(
                    "mu1" to Mu("mu1",
                            listOf(HMTypedVar("nu", ConstrType("array", listOf(intType))), HMTypedVar("m", intType), HMTypedVar("nu_len", intType)),
                            "j1", "j2",
                            listOf(
                                    PredicateApplication("<", listOf(Variable("j1"), Variable("m"))),
                                    PredicateApplication("<=", listOf(Variable("j1"), Variable("m"))),
                                    PredicateApplication(">", listOf(Variable("j1"), Variable("m")))
                            ),
                            listOf(
                                    QEStarElement(PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Literal("0", intType))), listOf("nu_len")),
                                    QEStarElement(PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Variable("m"))), listOf("nu_len"))
                            ),
                            listOf(),
                            listOf(),
                            listOf()
                    )
            ),
            mapOf(),
            mapOf("mu1" to setOf(2)),
            mapOf(), false, 5000
    )


    private val goalG2 = Z3Goal(
            "G2",
            "Goal for testing",
            listOf(
                    PredicateApplication(">=", listOf(Variable("a_len"), Literal("0", intType))),
                    PredicateApplication(">", listOf(Variable("n"), Literal("0", intType))),
                    PredicateApplication("mu1", listOf(Variable("a"), Variable("n"), Variable("a_len"))),
                    PredicateApplication("=", listOf(Variable("k"), FunctionApplication("-", listOf(Variable("n"), Literal("1", intType))))),
                    PredicateApplication("<=", listOf(FunctionApplication("get-array", listOf(Variable("a"), Variable("k"))), FunctionApplication("get-array", listOf(Variable("a"), Variable("n")))))
            ),
            PredicateApplication("mu1", listOf(Variable("a"), FunctionApplication("+", listOf(Variable("n"), Literal("1", intType))), Variable("a_len"))),
            mapOf("a" to ConstrType("array", listOf(intType)), "n" to intType, "k" to intType, "a_len" to intType),
            mapOf(),
            mapOf("mu1" to Mu(
                    "mu1",
                    listOf(HMTypedVar("nu", ConstrType("array", listOf(intType))), HMTypedVar("n", intType), HMTypedVar("nu_len", intType)),
                    "j1",
                    "j2",
                    listOf(),
                    listOf(),
                    listOf(PredicateApplication("<=", listOf(Variable("j1"), Variable("j2"))), PredicateApplication("=", listOf(Variable("j1"), Variable("j2"))), PredicateApplication("<", listOf(Variable("j2"), Variable("n")))),
                    listOf(
                            QEEStarElement(
                                    PredicateApplication("<=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), FunctionApplication("get-array", listOf(Variable("nu"), Variable("j2"))))),
                                    listOf("nu_len"),
                                    listOf("nu_len")
                            ),
                            QEEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Literal("0", intType))),
                                    listOf("nu_len"),
                                    listOf()
                            )),
                    listOf()
            )),
            mapOf(),
            mapOf("mu1" to setOf(2)),
            mapOf(), false, 5000
    )

    private val goalG3 = Z3Goal(
            "G3",
            "Goal for testing",
            listOf(
                    PredicateApplication("mu1", listOf(Variable("a"), Variable("m"), Variable("a_len")))
            ),
            ForAll(listOf(HMTypedVar("j1", intType), HMTypedVar("j2", intType)),
                    Implication(listOf(
                            PredicateApplication("<=", listOf(Literal("0", intType), Variable("j1"))),
                            PredicateApplication("<=", listOf(Variable("j1"), Variable("j2"))),
                            PredicateApplication("<", listOf(Variable("j2"), Variable("a_len"))),
                            PredicateApplication("<=", listOf(FunctionApplication("get-array", listOf(Variable("a"), Variable("j1"))), FunctionApplication("get-array", listOf(Variable("a"), Variable("j2")))))
                    ))
            ),
            mapOf("a" to ConstrType("array", listOf(intType)), "m" to intType, "a_len" to intType),
            mapOf(),
            mapOf("mu1" to Mu(
                    "mu1",
                    listOf(HMTypedVar("nu", ConstrType("array", listOf(intType))), HMTypedVar("m", intType), HMTypedVar("nu_len", intType)),
                    "j1", "j2",
                    listOf(
                            PredicateApplication("<=", listOf(Variable("j1"), Variable("m"))),
                            PredicateApplication(">", listOf(Variable("j1"), Variable("m")))
                    ),
                    listOf(
                            QEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Literal("0", intType))),
                                    listOf("nu_len")
                            ),
                            QEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Literal("1", intType))),
                                    listOf("nu_len")
                            )
                    ),
                    listOf(),
                    listOf(),
                    listOf()
            )),
            mapOf(),
            mapOf("mu1" to setOf(2)),
            mapOf(), false, 5000
    )

    private val assumptionsG4 = listOf(
            PredicateApplication(">=", listOf(Variable("a_len"), Literal("0", intType))),
            PredicateApplication(">=", listOf(Variable("n"), Literal("0", intType))),
            PredicateApplication("<", listOf(Variable("n"), Variable("a_len"))),
            ForAll(listOf(HMTypedVar("i", intType)), Implication(listOf(
                    PredicateApplication("<=", listOf(Literal("0", intType), Variable("i"))),
                    PredicateApplication("<=", listOf(Variable("i"), Variable("n"))),
                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("a"), Variable("i"))), Literal("0", intType)))
            )))
    )
    private val conclusionG4 = PredicateApplication("mu1", listOf(Variable("a"), Variable("n"), Variable("a_len")))
    private val environmentG4 = mapOf("n" to intType, "a" to ConstrType("array", listOf(intType)), "a_len" to intType)
    private val declarationMapG4 = mapOf<String, UninterpretedFunctionType>()

    private val goalG4 = Z3Goal("G4",
            "Goal for testing",
            assumptionsG4,
            conclusionG4,
            environmentG4,
            mapOf(),
            mapOf("mu1" to Mu("mu1",
                    listOf(HMTypedVar("nu", ConstrType("array", listOf(intType))), HMTypedVar("n", intType), HMTypedVar("nu_len", intType)),
                    "i", "j",
                    listOf(PredicateApplication("<=", listOf(Variable("i"), Variable("n")))),
                    listOf(
                            QEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("i"))), Literal("0", intType))),
                                    listOf("nu_len")
                            ),
                            QEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("i"))), Literal("1", intType))),
                                    listOf("nu_len")
                            )
                    ),
                    listOf(),
                    listOf(),
                    listOf())),
            mapOf(),
            mapOf("mu1" to setOf(2)),
            declarationMapG4, false, 5000
    )

    private val goalG5 = Z3Goal("G5",
            "Goal for testing",
            assumptionsG4,
            conclusionG4,
            environmentG4,
            mapOf(),
            mapOf("mu1" to Mu("mu1", listOf(
                    HMTypedVar("nu", ConstrType("array", listOf(intType))),
                    HMTypedVar("n", intType),
                    HMTypedVar("nu_len", intType)
            ),
                    "i", "j",
                    listOf(
                            PredicateApplication("<=", listOf(Variable("i"), Variable("n"))),
                            PredicateApplication("<", listOf(Variable("i"), Variable("n")))
                    ),
                    listOf(
                            QEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("i"))), Literal("0", intType))),
                                    listOf("nu_len")
                            ),
                            QEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("i"))), Literal("1", intType))),
                                    listOf("nu_len")
                            )
                    ),
                    listOf(),
                    listOf(),
                    listOf())),
            mapOf(),
            mapOf("mu1" to setOf(2)),
            declarationMapG4, false, 5000
    )


    @Test
    fun checkSolMu() {
        val solution = Solution(
                mutableMapOf(),
                mutableMapOf("mu1" to
                        MuSolution(mutableListOf(
                                Refinement(mutableSetOf(0), mutableSetOf(0))
                        ), mutableListOf(), mutableSetOf())
                )
        )
        val result = goalG1.check(solution)
        assertTrue(result is Correct, "G1: solution mu1 a m = [forall i. i < m -> a[i] = 0] is correct")
    }

    @Test
    fun checkSolMu2() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf("mu1" to
                        MuSolution(
                                mutableListOf(),
                                mutableListOf(
                                        Refinement(mutableSetOf(0, 2), mutableSetOf(0))
                                ),
                                mutableSetOf()
                        )
                )
        )
        val result = goalG2.check(solution)
        assertTrue(result is Correct, "G2: solution mu1 nu n = [forall j1, j2. 0 <= j1 <= j2 < n  -> nu[j1] <= nu[j2]] is correct")
    }

    @Test
    fun checkSolMu3() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf(
                        "mu1" to MuSolution(
                                mutableListOf(
                                        Refinement(mutableSetOf(0), mutableSetOf(0)),
                                        Refinement(mutableSetOf(1), mutableSetOf(1))
                                ),
                                mutableListOf(),
                                mutableSetOf()
                        )
                )
        )
        val result = goalG3.check(solution)
        assertTrue(result is Correct, "G3: solution mu1 nu n = [forall j1. j1 <= m -> nu[j1] = 0] && [forall j1. j1 > m -> nu[j1] = 1]")
    }

    @Test
    fun checkSolMu4() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf(
                        "mu1" to MuSolution(
                                listOf(
                                        Refinement(setOf(), setOf(0)),
                                        Refinement(setOf(), setOf(1))
                                ),
                                listOf(),
                                setOf()
                        )
                )
        )
        val result = goalG4.check(solution)
        assertEquals(MuWeakened("mu1"), result, "G4 must have been weakened")
        assertEquals(MuSolution(listOf(Refinement(setOf(0), setOf(0))), listOf(), setOf()), solution.mus["mu1"])
    }

    @Test fun checkSolMu5() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf(
                        "mu1" to MuSolution(
                                listOf(
                                        Refinement(setOf(), setOf(0)),
                                        Refinement(setOf(), setOf(1))
                                ),
                                listOf(),
                                setOf()
                        )
                )
        )
        val result = goalG5.check(solution)
        assertEquals(MuWeakened("mu1"), result, "G4 must have been weakened")
        assertEquals(MuSolution(listOf(Refinement(setOf(0), setOf(0)), Refinement(setOf(1), setOf(0))), listOf(), setOf()), solution.mus["mu1"])
    }


    private val auxG6 = { qii: List<Assertion> -> Z3Goal("G6",
            "Goal for testing",
            listOf(
                    PredicateApplication(">=", listOf(Variable("a_len"), Literal("0", intType))),
                    ForAll(
                            listOf(HMTypedVar("i", intType), HMTypedVar("j", intType)),
                            Implication(
                                    Or(
                                            And(
                                                    PredicateApplication("=", listOf(Variable("i"), Literal("1", intType))),
                                                    PredicateApplication("=", listOf(Variable("j"), Literal("2", intType)))
                                            ),
                                            And(
                                                    PredicateApplication("=", listOf(Variable("i"), Literal("1", intType))),
                                                    PredicateApplication("=", listOf(Variable("j"), Literal("3", intType)))
                                            ),
                                            And(
                                                    PredicateApplication("=", listOf(Variable("i"), Literal("2", intType))),
                                                    PredicateApplication("=", listOf(Variable("j"), Literal("3", intType)))
                                            ),
                                            PredicateApplication("=", listOf(Variable("i"), Variable("j")))
                                    ),
                                    PredicateApplication("<=", listOf(
                                            FunctionApplication("get-array", listOf(Variable("a"), Variable("i"))),
                                            FunctionApplication("get-array", listOf(Variable("a"), Variable("j")))
                                    ))
                            )
                    )
            ),
            PredicateApplication("mu1", listOf(Variable("a"), Variable("a_len"))),
            mapOf("a" to ConstrType("array", listOf(intType)), "a_len" to intType),
            mapOf(),
            mapOf("mu1" to Mu("mu1", listOf(HMTypedVar("nu", ConstrType("array", listOf(intType))), HMTypedVar("nu_len", intType)),
                    "i", "j",
                    listOf(),
                    listOf(),
                    qii,
                    listOf(
                            QEEStarElement(
                                    PredicateApplication("<=", listOf(
                                            FunctionApplication("get-array", listOf(Variable("nu"), Variable("i"))),
                                            FunctionApplication("get-array", listOf(Variable("nu"), Variable("j")))
                                    )),
                                    listOf("nu_len"),
                                    listOf("nu_len")
                            ),
                            QEEStarElement(
                                    PredicateApplication("<", listOf(
                                            FunctionApplication("get-array", listOf(Variable("nu"), Variable("i"))),
                                            FunctionApplication("get-array", listOf(Variable("nu"), Variable("j")))
                                    )),
                                    listOf("nu_len"),
                                    listOf("nu_len")
                            )
                    ),
                    listOf()
            )),
            mapOf(),
            mapOf("mu1" to setOf(1)),
            mapOf(), false, 5000
    )}


    private val goalG6 = auxG6(listOf(
            PredicateApplication("<=", listOf(Variable("i"), Variable("j")))
    ))


    private val goalG7 = auxG6(listOf(
            PredicateApplication("<=", listOf(Literal("1", intType), Variable("i"))),
            PredicateApplication("<=", listOf(Variable("i"), Variable("j"))),
            PredicateApplication("<=", listOf(Variable("j"), Literal("3", intType))),
            PredicateApplication("<", listOf(Variable("i"), Variable("j"))),
            And(
                    PredicateApplication("<", listOf(Literal("0", intType), Variable("i"))),
                    PredicateApplication("<", listOf(Variable("j"), Literal("4", intType))),
                    PredicateApplication("<=", listOf(Variable("i"), Variable("j")))
            )
    ))



    @Test fun checkSolMu6() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf(
                        "mu1" to MuSolution(
                                listOf(),
                                listOf(
                                        Refinement(setOf(), setOf(0)),
                                        Refinement(setOf(), setOf(1))
                                ),
                                setOf()
                        )
                )
        )
        val result = goalG6.check(solution)
        assertEquals(MuWeakened("mu1"), result, "With G6 solution must have been weakened")
        assertEquals(listOf(), solution.mus["mu1"]!!.doubleRefinements, "All refinements must have been erased")
    }

    @Test fun checkSolMu7() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf(
                        "mu1" to MuSolution(
                                listOf(),
                                listOf(
                                        Refinement(setOf(0, 1, 2), setOf(0))
                                ),
                                setOf()
                        )
                )
        )
        val result = goalG7.check(solution)
        assertTrue(result is Correct, "Correct solution with G7 must be valid")
    }

    @Test fun checkSolMu8() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf(
                        "mu1" to MuSolution(
                                listOf(),
                                listOf(
                                        Refinement(setOf(0, 1, 2), setOf(0)),
                                        Refinement(setOf(0, 1, 2), setOf(1))
                                ),
                                setOf()
                        )
                )
        )
        val result = goalG7.check(solution)
        assertEquals(MuWeakened("mu1"), result, "G7: A redundant qualifier in QEE must have been weakened")
        assertEquals(listOf(Refinement(setOf(0, 1, 2), setOf(0))), solution.mus["mu1"]!!.doubleRefinements,
                "G7: Redundant qualifier of QEE must have been removed")
    }

    @Test fun checkSolMu9() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf(
                        "mu1" to MuSolution(
                                listOf(),
                                listOf(
                                        Refinement(setOf(0), setOf(0)),
                                        Refinement(setOf(0, 1, 2), setOf(1))
                                ),
                                setOf()
                        )
                )
        )
        val result = goalG7.check(solution)
        assertEquals(MuWeakened("mu1"), result, "G7: mu1 must have been weakened")
        assertEquals(listOf(
                Refinement(setOf(0, 4), setOf(0)),
                Refinement(setOf(0, 1, 2), setOf(0)),
                Refinement(setOf(0, 2, 3), setOf(0))
                ), solution.mus["mu1"]!!.doubleRefinements, "G7: Weakened mu1 is correct")
    }

    @Test fun checkSolMu10() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf(
                        "mu1" to MuSolution(
                                listOf(),
                                listOf(
                                        Refinement(setOf(), setOf(0))
                                ),
                                setOf()
                        )
                )
        )
        val result = goalG7.check(solution)
        assertEquals(MuWeakened("mu1"), result, "G7: mu1 must have been weakened")
        assertEquals(listOf(
                Refinement(setOf(4), setOf(0)),
                Refinement(setOf                (0, 1, 2), setOf(0)),
                Refinement(setOf(0, 2, 3), setOf(0))
        ), solution.mus["mu1"]!!.doubleRefinements, "G7: Weakened mu1 is correct")
    }

}