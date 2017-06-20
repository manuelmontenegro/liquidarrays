package es.ucm.caviart

import org.junit.Test
import java.util.function.Predicate

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
                                    QEStarElement(PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Literal("0", Type("int")))), listOf("nu")),
                                    QEStarElement(PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Variable("m"))), listOf("nu"))
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
                                    listOf("nu"),
                                    listOf("nu")
                            ),
                            QEEStarElement(
                                    PredicateApplication("=", listOf(FunctionApplication("get-array", listOf(Variable("nu"), Variable("j1"))), Literal("0", Type("int")))),
                                    listOf("nu"),
                                    listOf()
                            )),
                    listOf()
            )),
            mapOf("mu1" to UninterpretedFunctionType(listOf(Type("array", listOf(Type("int"))), Type("int")), Type("bool")))
    )

    @Test fun checkSolMu() {
        val solution = Solution(mutableMapOf(),
                mutableMapOf("mu1" to
                        MuSolution(listOf(
                                Refinement(mutableSetOf(0), mutableSetOf(0))
                        ), listOf(), mutableSetOf())
                )
        )
        G1.check(solution);

        // TODO: Complete
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
        G2.check(solution);

        // TODO: Complete
    }


}