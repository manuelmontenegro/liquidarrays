package es.ucm.caviart

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TypeCheckerTest {
    @Test fun instance1() {
        assertEquals(mapOf(), findInstanceOf(ConstrType("int"), ConstrType("int")))
    }

    @Test fun instance2() {
        assertNull(findInstanceOf(ConstrType("bool"), ConstrType("int")))
    }

    @Test fun instance3() {
        assertEquals(mapOf("'a" to ConstrType("int")), findInstanceOf(VarType("'a"), ConstrType("int")))
    }

    @Test fun instance4() {
        assertEquals(mapOf("'a" to VarType("'b")),
                findInstanceOf(
                        ConstrType("map", listOf(VarType("'a"), ConstrType("int"))),
                        ConstrType("map", listOf(VarType("'b"), ConstrType("int")))
                ))
    }

    @Test fun instance5() {
        assertEquals(mapOf("'a" to ConstrType("array", listOf(ConstrType("int")))),
                findInstanceOf(
                        ConstrType("map", listOf(VarType("'a"), ConstrType("int"))),
                        ConstrType("map", listOf(ConstrType("array", listOf(ConstrType("int"))), ConstrType("int")))
                ))
    }

    @Test fun instance6() {
        assertEquals(mapOf("'a" to ConstrType("array", listOf(ConstrType("bool")))),
                findInstanceOf(
                        ConstrType("map", listOf(VarType("'a"), ConstrType("int"))),
                        ConstrType("map", listOf(ConstrType("array", listOf(ConstrType("bool"))), ConstrType("int")))
                ))
    }

    @Test fun instance7() {
        assertEquals(mapOf("'a" to ConstrType("int")),
                findInstanceOf(
                        ConstrType("map", listOf(VarType("'a"), VarType("'a"))),
                        ConstrType("map", listOf(ConstrType("int"), ConstrType("int")))
                ))
    }

    @Test fun instance8() {
        assertNull(findInstanceOf(
                        ConstrType("map", listOf(VarType("'a"), VarType("'a"))),
                        ConstrType("map", listOf(ConstrType("int"), ConstrType("bool")))
                ))
    }

    @Test fun instantiate1() {
        assertEquals(ConstrType("int"), instantiate(ConstrType("int"), mapOf("'a" to ConstrType("bool"))))
    }

    @Test fun instantiate2() {
        assertEquals(ConstrType("bool"), instantiate(VarType("'a"), mapOf("'a" to ConstrType("bool"))))
    }

    @Test fun instantiate3() {
        assertEquals(VarType("'b"), instantiate(VarType("'b"), mapOf("'a" to ConstrType("bool"))))
    }

    @Test fun instantiate4() {
        assertEquals(ConstrType("map", listOf(ConstrType("int"), ConstrType("int"), ConstrType("bool"))),
                instantiate(ConstrType("map", listOf(VarType("'a"), VarType("'a"), VarType("'b"))),
                        mapOf("'a" to ConstrType("int"), "'b" to ConstrType("bool"))))
    }

    @Test fun instantiate5() {
        assertEquals(ConstrType("map", listOf(ConstrType("int"), VarType("'c"), ConstrType("bool"))),
                instantiate(ConstrType("map", listOf(VarType("'a"), VarType("'c"), VarType("'b"))),
                        mapOf("'a" to ConstrType("int"), "'b" to ConstrType("bool"))))
    }
}