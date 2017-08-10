package es.ucm.caviart

import org.junit.Test
import kotlin.test.assertEquals

class SubstitutionTest {
    @Test fun substitute1() {
        val p = parseAssertion(getSExps("(@ f x)")[0])
        val subst = mapOf("x" to "y")
        assertEquals("(@ f y)", p.applySubstitution(subst).toSExp())
    }

    @Test fun substitute2() {
        val p = parseAssertion(getSExps("(@ g (the int x))")[0])
        val subst = mapOf("x" to "y")
        assertEquals("(@ g (the int x))", p.applySubstitution(subst).toSExp())
    }

    @Test fun substitute3() {
        val p = parseAssertion(getSExps("(and (@ < x y) (@ <= z x))")[0])
        val subst = mapOf("x" to "y")
        assertEquals("(and (@ < y y) (@ <= z y))", p.applySubstitution(subst).toSExp())
    }

    @Test fun substitute4() {
        val p = parseAssertion(getSExps("(forall ((i int) (j bool)) (@ <= i x))")[0])
        val subst = mapOf("x" to "y")
        assertEquals("(forall ((i int) (j bool)) (@ <= i y))", p.applySubstitution(subst).toSExp())
    }

    @Test fun substitute5() {
        val p = parseAssertion(getSExps("(forall ((i int) (j bool)) (@ <= i x))")[0])
        val subst = mapOf("i" to "y")
        assertEquals("(forall ((i int) (j bool)) (@ <= i x))", p.applySubstitution(subst).toSExp())
    }


    @Test fun substitute6() {
        val p = parseAssertion(getSExps("(forall ((i int) (j bool)) (@ <= i x))")[0])
        val subst = mapOf("i" to "y", "x" to "i")
        assertEquals("(forall ((_I_1 int) (j bool)) (@ <= _I_1 i))", p.applySubstitution(subst).toSExp())
    }
}