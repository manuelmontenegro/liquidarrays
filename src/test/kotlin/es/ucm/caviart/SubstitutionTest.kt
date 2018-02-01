package es.ucm.caviart

import es.ucm.caviart.ast.Variable
import es.ucm.caviart.ast.getSExps
import es.ucm.caviart.ast.parseAssertion
import es.ucm.caviart.ast.toSExp
import es.ucm.caviart.qstar.applySubstitution
import org.junit.Test
import kotlin.test.assertEquals

class SubstitutionTest {
    @Test fun substitute1() {
        val p = parseAssertion(getSExps("(@ f x)")[0])
        val subst = mapOf("x" to Variable("y"))
        assertEquals("(@ f y)", p.applySubstitution(subst).toSExp().toString())
    }

    @Test fun substitute2() {
        val p = parseAssertion(getSExps("(@ g (the int x))")[0])
        val subst = mapOf("x" to Variable("y"))
        assertEquals("(@ g (the int x))", p.applySubstitution(subst).toSExp().toString())
    }

    @Test fun substitute3() {
        val p = parseAssertion(getSExps("(and (@ < x y) (@ <= z x))")[0])
        val subst = mapOf("x" to Variable("y"))
        assertEquals("(and (@ < y y) (@ <= z y))", p.applySubstitution(subst).toSExp().toString())
    }

    @Test fun substitute4() {
        val p = parseAssertion(getSExps("(forall ((i int) (j bool)) (@ <= i x))")[0])
        val subst = mapOf("x" to Variable("y"))
        assertEquals("(forall ((i int) (j bool)) (@ <= i y))", p.applySubstitution(subst).toSExp().toString())
    }

    @Test fun substitute5() {
        val p = parseAssertion(getSExps("(forall ((i int) (j bool)) (@ <= i x))")[0])
        val subst = mapOf("i" to Variable("y"))
        assertEquals("(forall ((i int) (j bool)) (@ <= i x))", p.applySubstitution(subst).toSExp().toString())
    }


    @Test fun substitute6() {
        val p = parseAssertion(getSExps("(forall ((i int) (j bool)) (@ <= i x))")[0])
        val subst = mapOf("i" to Variable("y"), "x" to Variable("i"))
        assertEquals("(forall ((_I_1 int) (j bool)) (@ <= _I_1 i))", p.applySubstitution(subst).toSExp().toString())
    }
}