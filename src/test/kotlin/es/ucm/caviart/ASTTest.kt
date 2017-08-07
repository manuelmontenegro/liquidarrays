package es.ucm.caviart

import org.junit.Test
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
        val l = Literal("5", HMType("int"))
        l.testProp = 3
        assertEquals(3, l.testProp)
    }

    @Test fun initiallyEmpty() {
        val l = Literal("5", HMType("int"))
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
}
