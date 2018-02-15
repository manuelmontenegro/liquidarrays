package es.ucm.caviart

import es.ucm.caviart.ast.*
import es.ucm.caviart.iterativeweakening.fromKappaDeclaration
import org.junit.Test
import kotlin.test.assertEquals


class KappaDeclarationToKappaTest {
    private val kappa1Declaration = KappaDeclaration("kappa1",
            HMTypedVar("nu", intType),
            listOf(),
            setOf(PredicateApplication("<", listOf(Variable("nu"), Literal("0", intType))))
    )

    private val kappa2Declaration = KappaDeclaration("kappa2",
            HMTypedVar("nu", intType),
            listOf(HMTypedVar("*", intType)),
            setOf(
                    PredicateApplication(">=", listOf(Variable("nu"), Literal("0", intType))),
                    PredicateApplication("<", listOf(Variable("nu"), Variable("*")))
            )
    )

    @Test fun kappa1() {
        val kappa = fromKappaDeclaration(kappa1Declaration)
        assertEquals("kappa1", kappa.name)
        assertEquals(1, kappa.arguments.size)
        assertEquals(HMTypedVar("nu", intType), kappa.arguments[0])
        assertEquals(1, kappa.qStar.size)
        assertEquals("(@ < nu (the int 0))", kappa.qStar[0].toSExp().toString())
    }

    @Test fun kappa2() {
        val kappa = fromKappaDeclaration(kappa2Declaration)
        assertEquals("kappa2", kappa.name)
        assertEquals(2, kappa.arguments.size)
        assertEquals(HMTypedVar("nu", intType), kappa.arguments[0])
        assertEquals(HMTypedVar("*", intType), kappa.arguments[1])
        assertEquals(2, kappa.qStar.size)
        assertEquals("(@ >= nu (the int 0))", kappa.qStar[0].toSExp().toString())
        assertEquals("(@ < nu *)", kappa.qStar[1].toSExp().toString())
    }
}