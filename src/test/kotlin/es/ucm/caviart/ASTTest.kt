package es.ucm.caviart

import org.junit.Test
import java.util.function.Predicate
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Functions for testing the AST decorations
 *
 * Created by manuel on 30/05/17.
 */

var ASTElem.testProp: Int by ASTDelegate()

var ASTElem.secondaryProp: String by ASTDelegate()

class ASTTest {
    @Test fun addAndRemoveProperty() {
        val l = Literal("5", ConstrType("int"))
        l.testProp = 3
        assertEquals(3, l.testProp)
    }

    @Test fun initiallyEmpty() {
        val l = Literal("5", ConstrType("int"))
        assertFailsWith(PropertyNotFoundException::class) {
            val x = l.testProp
            println(x)
        }
    }

    @Test fun severalProperties() {
        val v = Variable("x")
        v.testProp = 3
        v.secondaryProp = "Pepe"
        assertEquals(3, v.testProp)
        assertEquals("Pepe", v.secondaryProp)
    }

    @Test fun getVariables1() {
        val a = And(True(), False(), BooleanVariable("x"), BooleanEquality(BooleanVariable("z"), True()))
        assertEquals(setOf("x", "z"), a.getVariables())
    }

    @Test fun getVariables2() {
        val a = PredicateApplication("fu", listOf(Literal("5", ConstrType("int")), Variable("x"), Variable("z")))
        assertEquals(setOf("x", "z"), a.getVariables())
    }

    @Test fun getVariables3() {
        val a = ForAll(listOf(HMTypedVar("i", ConstrType("int")), HMTypedVar("j", ConstrType("int"))),
            PredicateApplication("p", listOf(Variable("x"), Variable("z"), Variable("i"), Variable("j"))))
        assertEquals(setOf("x", "z"), a.getVariables())
    }
}

