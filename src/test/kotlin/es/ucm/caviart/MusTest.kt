package es.ucm.caviart

import org.junit.Test
import java.util.function.Predicate
import kotlin.test.assertTrue

/**
 * Created by manuel on 20/06/17.
 */

class MusTest {
    val G1 = Goal(
            "G1",
            listOf(
                    PredicateApplication(">=", listOf(Variable("n"), Literal("0", Type("int")))),
                    PredicateApplication("mu1", listOf(Variable("a"), Variable("n"))),
                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("a"), Variable("n"))), Literal("0", Type("int"))))
            ),
            PredicateApplication("mu1", listOf(Variable("a"), FunctionApplication("+", listOf(Variable("n"), Literal("1", Type("int")))))),
            mapOf("a" to Type("array", listOf(Type("int"))), "n" to Type("int")),
            mapOf(),
            mapOf(
                    "mu1" to Mu("mu1",
                            listOf(TypedVar("nu", Type("array", listOf(Type("int")))), TypedVar("m", Type("int"))),
                            "j1", "j2",
                            listOf(
                                    PredicateApplication("<", listOf(Variable("j1"), Variable("m"))),
                                    PredicateApplication("<=", listOf(Variable("j1"), Variable("m"))),
                                    PredicateApplication(">", listOf(Variable("j1"), Variable("m")))
                            ),
                            listOf(
                                    QEStarElement(PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Literal("0", Type("int")))), listOf(Pair("nu", Type("int")))),
                                    QEStarElement(PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Variable("m"))), listOf(Pair("nu", Type("int"))))
                            ),
                            listOf(),
                            listOf(),
                            listOf()
                    )
            ),
            mapOf("mu1" to UninterpretedFunctionType(listOf(Type("array", listOf(Type("int"))), Type("int")), Type("bool")))
    )

    val G2 = Goal(
            "G2",
            listOf(
                    PredicateApplication(">", listOf(Variable("n"), Literal("0", Type("int")))),
                    PredicateApplication("mu1", listOf(Variable("a"), Variable("n"))),
                    PredicateApplication("<=", listOf(FunctionApplication("get-array", listOf(Variable("a"), FunctionApplication("-", listOf(Variable("n"), Literal("1", Type("int")))))), FunctionApplication("get-array", listOf(Variable("a"), Variable("n")))))
            ),
            PredicateApplication("mu1", listOf(Variable("a"), FunctionApplication("+", listOf(Variable("n"), Literal("1", Type("int")))))),
            mapOf("a" to Type("array", listOf(Type("int"))), "n" to Type("int")),
            mapOf(),
            mapOf("mu1" to Mu(
                    "mu1",
                    listOf(TypedVar("nu", Type("array", listOf(Type("int")))), TypedVar("n", Type("int"))),
                    "j1",
                    "j2",
                    listOf(),
                    listOf(),
                    listOf(PredicateApplication("<=", listOf(Variable("j1"), Variable("j2"))), PredicateApplication("=", listOf(Variable("j1"), Variable("j2"))), PredicateApplication("<", listOf(Variable("j2"), Variable("n")))),
                    listOf(
                            QEEStarElement(
                                    PredicateApplication("<=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), FunctionApplication("get-array", listOf(Variable("nu"), Variable("j2"))))),
                                    listOf(Pair("nu", Type("int"))),
                                    listOf(Pair("nu", Type("int")))
                            ),
                            QEEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Literal("0", Type("int")))),
                                    listOf(Pair("nu", Type("int"))),
                                    listOf()
                            )),
                    listOf()
            )),
            mapOf("mu1" to UninterpretedFunctionType(listOf(Type("array", listOf(Type("int"))), Type("int")), Type("bool")))
    )

    val G3 = Goal(
            "G3",
            listOf(
                    PredicateApplication("mu1", listOf(Variable("a"), Variable("m")))
            ),
            ForAll(listOf(TypedVar("j1", Type("int")), TypedVar("j2", Type("int"))),
                    Implication(listOf(
                            PredicateApplication("<=", listOf(Literal("0", Type("int")), Variable("j1"))),
                            PredicateApplication("<=", listOf(Variable("j1"), Variable("j2"))),
                            PredicateApplication("<", listOf(Variable("j2"), FunctionApplication("len", listOf(Variable("a"))))),
                            PredicateApplication("<=", listOf(FunctionApplication("get-array", listOf(Variable("a"), Variable("j1"))), FunctionApplication("get-array", listOf(Variable("a"), Variable("j2")))))
                    ))
            ),
            mapOf("a" to Type("array", listOf(Type("int"))), "m" to Type("int")),
            mapOf(),
            mapOf("mu1" to Mu(
                    "mu1",
                    listOf(TypedVar("nu", Type("array", listOf(Type("int")))), TypedVar("m", Type("int"))),
                    "j1", "j2",
                    listOf(
                            PredicateApplication("<=", listOf(Variable("j1"), Variable("m"))),
                            PredicateApplication(">", listOf(Variable("j1"), Variable("m")))
                    ),
                    listOf(
                            QEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Literal("0", Type("int")))),
                                    listOf(Pair("nu", Type("int")))
                            ),
                            QEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Literal("1", Type("int")))),
                                    listOf(Pair("nu", Type("int")))
                            )
                    ),
                    listOf(),
                    listOf(),
                    listOf()
            )),
            mapOf("len" to UninterpretedFunctionType(listOf(Type("array", listOf(Type("int")))), Type("int")),
                    "mu1" to UninterpretedFunctionType(listOf(Type("array", listOf(Type("int"))), Type("int")), Type("bool")))
    )

    @Test fun checkSolMu() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf("mu1" to
                        MuSolution(listOf(
                                Refinement(mutableSetOf(0), mutableSetOf(0))
                        ), listOf(), mutableSetOf())
                )
        )
        val result = G1.check(solution)
        assertTrue(result is Correct, "G1: solution mu1 a m = [forall i. i < m -> a[i] = 0] is correct")
    }

    @Test fun checkSolMu2() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf("mu1" to
                        MuSolution(
                                listOf(),
                                listOf(
                                        Refinement(mutableSetOf(0, 2), mutableSetOf(0))
                                ),
                                mutableSetOf()
                        )
                )
        )
        val result = G2.check(solution)
        assertTrue(result is Correct, "G2: solution mu1 nu n = [forall j1, j2. 0 <= j1 <= j2 < n  -> nu[j1] <= nu[j2]] is correct")
    }

    @Test fun checkSolMu3() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf(
                        "mu1" to MuSolution(
                                listOf(
                                        Refinement(mutableSetOf(0), mutableSetOf(0)),
                                        Refinement(mutableSetOf(1), mutableSetOf(1))
                                ),
                                listOf(),
                                mutableSetOf()
                        )
                )
        )
        val result = G3.check(solution)
        assertTrue(result is Correct, "G3: solution mu1 nu n = [forall j1. j1 <= m -> nu[j1] = 0] && [forall j1. j1 > m -> nu[j1] = 1]")
    }


}