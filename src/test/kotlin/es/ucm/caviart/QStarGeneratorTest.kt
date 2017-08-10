package es.ucm.caviart

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class QStarGeneratorTest {

    @Before fun beforeTest() {
        FreshNameGenerator.resetGenerator()
    }

    @Test fun matchParams1() {
        val params = listOf(HMTypedVar("x1", ConstrType("int")), HMTypedVar("x2", VarType("'a")))
        val result = matchParameters(emptyMap(), emptyMap(), emptyList(), params)

        assertEquals(setOf(mapOf()), result)
    }

    @Test fun matchParams2() {
        val params = listOf(HMTypedVar("x1", ConstrType("int")), HMTypedVar("x2", VarType("'a")))
        val result = matchParameters(emptyMap(), emptyMap(), listOf(HMTypedVar("*", ConstrType("int"))), params)

        assertEquals(setOf(mapOf("*" to "x1")), result)
    }

    @Test fun matchParams3() {
        val params = listOf(HMTypedVar("x1", ConstrType("int")), HMTypedVar("x2", VarType("'a")))
        val result = matchParameters(emptyMap(), emptyMap(), listOf(HMTypedVar("*", ConstrType("int")), HMTypedVar("**", VarType("'b"))), params)

        assertEquals(setOf(mapOf("*" to "x1", "**" to "x1"), mapOf("*" to "x1", "**" to "x2")), result)
    }

    @Test fun matchParams4() {
        val params = listOf(HMTypedVar("x1", ConstrType("array", listOf(ConstrType("int")))), HMTypedVar("x2", ConstrType("int")))
        val result = matchParameters(emptyMap(), emptyMap(), listOf(HMTypedVar("*", ConstrType("array", listOf(VarType("'a")))), HMTypedVar("**", VarType("'a"))), params)

        assertEquals(setOf(mapOf("*" to "x1", "**" to "x2")), result)
    }

    @Test fun matchParams5() {
        val params = listOf(HMTypedVar("x1", ConstrType("array", listOf(ConstrType("int")))), HMTypedVar("x2", ConstrType("bool")))
        val result = matchParameters(emptyMap(), emptyMap(), listOf(HMTypedVar("*", ConstrType("array", listOf(VarType("'a")))), HMTypedVar("**", VarType("'a"))), params)

        assertEquals(emptySet(), result)
    }

    @Test fun matchParams6() {
        val params = listOf(HMTypedVar("x1", ConstrType("array", listOf(VarType("'b")))), HMTypedVar("x2", VarType("'b")), HMTypedVar("x3", VarType("'b")))
        val result = matchParameters(emptyMap(), emptyMap(), listOf(HMTypedVar("*", ConstrType("array", listOf(VarType("'a")))), HMTypedVar("**", VarType("'a"))), params)

        assertEquals(setOf(mapOf("*" to "x1", "**" to "x2"), mapOf("*" to "x1", "**" to "x3")), result)
    }

    @Test fun instantiateQualifier1() {
        val qual1 = GenericQualifier(HMTypedVar("nu", ConstrType("int")),
                listOf(HMTypedVar("*", ConstrType("int"))),
                parseAssertion(getSExps("(@ <= (the int 0) *)")[0])
        )

        val set = instantiateGenericQualifier(HMTypedVar("mi_nu", ConstrType("bool")),
                listOf(HMTypedVar("x", ConstrType("int")), HMTypedVar("y", ConstrType("int"))),
                qual1
        )

        assertEquals(emptySet(), set)
    }

    @Test fun instantiateQualifier2() {
        val qual1 = GenericQualifier(HMTypedVar("nu", ConstrType("int")),
                listOf(HMTypedVar("*", ConstrType("int"))),
                parseAssertion(getSExps("(@ <= (the int 0) *)")[0])
        )

        val set = instantiateGenericQualifier(HMTypedVar("mi_nu", ConstrType("int")),
                listOf(HMTypedVar("x", ConstrType("int")), HMTypedVar("y", ConstrType("int"))),
                qual1
        )

        assertEquals(setOf("(@ <= (the int 0) x)", "(@ <= (the int 0) y)"), set.map { it.toSExp() }.toSet())
    }

    @Test fun instantiateQualifier3() {
        val qual1 = GenericQualifier(HMTypedVar("nu", ConstrType("array", listOf(ConstrType("int")))),
                listOf(HMTypedVar("*", ConstrType("int")), HMTypedVar("**", ConstrType("int"))),
                parseAssertion(getSExps("(@ <= (@ get-array nu *) **)")[0])
        )

        val set = instantiateGenericQualifier(HMTypedVar("mi_nu", ConstrType("array", listOf(ConstrType("int")))),
                listOf(HMTypedVar("x", ConstrType("int")), HMTypedVar("y", ConstrType("int"))),
                qual1
        )

        assertEquals(setOf("(@ <= (@ get-array mi_nu x) x)", "(@ <= (@ get-array mi_nu x) y)", "(@ <= (@ get-array mi_nu y) x)", "(@ <= (@ get-array mi_nu y) y)"), set.map { it.toSExp() }.toSet())
    }

    @Test fun instantiateQualifier4() {
        val qual1 = GenericQualifier(HMTypedVar("nu", ConstrType("array", listOf(ConstrType("int")))),
                listOf(HMTypedVar("*", ConstrType("int")), HMTypedVar("**", ConstrType("int"))),
                parseAssertion(getSExps("(@ <= (@ get-array nu *) **)")[0])
        )

        val set = instantiateGenericQualifier(HMTypedVar("mi_nu", ConstrType("array", listOf(ConstrType("int")))),
                listOf(HMTypedVar("x", ConstrType("int")), HMTypedVar("y", ConstrType("bool"))),
                qual1
        )

        assertEquals(setOf("(@ <= (@ get-array mi_nu x) x)"), set.map { it.toSExp() }.toSet())
    }

    @Test fun instantiateQualifier5() {
        val qual1 = GenericQualifier(HMTypedVar("nu", ConstrType("int")),
                listOf(HMTypedVar("*", ConstrType("array", listOf(ConstrType("int")))), HMTypedVar("**", ConstrType("int"))),
                parseAssertion(getSExps("(@ <= (@ get-array * **) nu)")[0])
        )

        val set = instantiateGenericQualifier(HMTypedVar("mi_nu", ConstrType("int")),
                listOf(HMTypedVar("x", ConstrType("int")),
                        HMTypedVar("y", ConstrType("array", listOf(ConstrType("int")))),
                        HMTypedVar("z", ConstrType("array", listOf(ConstrType("int"))))
                ),
                qual1
        )

        assertEquals(setOf("(@ <= (@ get-array y x) mi_nu)", "(@ <= (@ get-array z x) mi_nu)"), set.map { it.toSExp() }.toSet())
    }


    @Test fun instantiateQualifier7() {
        val qual1 = GenericQualifier(HMTypedVar("nu", ConstrType("int")),
                listOf(HMTypedVar("*", ConstrType("array", listOf(ConstrType("int")))), HMTypedVar("**", ConstrType("int"))),
                parseAssertion(getSExps("(@ <= (@ get-array * **) nu)")[0])
        )

        val set = instantiateGenericQualifier(HMTypedVar("mi_nu", ConstrType("int")),
                listOf(HMTypedVar("x", ConstrType("int")),
                        HMTypedVar("y", ConstrType("array", listOf(ConstrType("int")))),
                        HMTypedVar("z", ConstrType("array", listOf(ConstrType("int")))),
                        HMTypedVar("w", ConstrType("int"))
                ),
                qual1
        )

        assertEquals(setOf("(@ <= (@ get-array y x) mi_nu)", "(@ <= (@ get-array z x) mi_nu)",
                "(@ <= (@ get-array y w) mi_nu)", "(@ <= (@ get-array z w) mi_nu)"), set.map { it.toSExp() }.toSet())
    }


    @Test fun instantiateSingleQualifier1() {
        val qual1 = GenericSingleQualifier(HMTypedVar("nu", ConstrType("int")), "i",
                listOf(HMTypedVar("*", ConstrType("int"))),
                parseAssertion(getSExps("(@ <= i *)")[0])
        )

        val set = instantiateGenericSingleQualifier(HMTypedVar("mi_nu", ConstrType("bool")),
                listOf(HMTypedVar("x", ConstrType("int")), HMTypedVar("y", ConstrType("int"))),
                qual1
        )

        assertEquals(emptySet(), set)
    }

    @Test fun instantiateSingleQualifier2() {
        val qual1 = GenericSingleQualifier(HMTypedVar("nu", ConstrType("int")), "i",
                listOf(HMTypedVar("*", ConstrType("int"))),
                parseAssertion(getSExps("(@ f nu i *)")[0])
        )

        val set = instantiateGenericSingleQualifier(HMTypedVar("mi_nu", ConstrType("int")),
                listOf(HMTypedVar("x", ConstrType("int")), HMTypedVar("y", ConstrType("int"))),
                qual1
        )

        assertEquals(setOf("i" to "(@ f mi_nu i x)", "i" to "(@ f mi_nu i y)"), set.map { it.boundVar to it.assertion.toSExp() }.toSet())
    }

    @Test fun instantiateSingleQualifier3() {
        val qual1 = GenericSingleQualifier(HMTypedVar("nu", ConstrType("int")), "i",
                listOf(HMTypedVar("*", ConstrType("int"))),
                parseAssertion(getSExps("(@ f nu i *)")[0])
        )

        val set = instantiateGenericSingleQualifier(HMTypedVar("mi_nu", ConstrType("int")),
                listOf(HMTypedVar("i", ConstrType("int")), HMTypedVar("y", ConstrType("int"))),
                qual1
        )

        assertEquals(setOf("_J_1" to "(@ f mi_nu _J_1 i)", "i" to "(@ f mi_nu i y)"), set.map { it.boundVar to it.assertion.toSExp() }.toSet())
    }

    @Test fun instantiateDoubleQualifier1() {
        val qual1 = GenericDoubleQualifier(HMTypedVar("nu", ConstrType("int")), "i", "j",
                listOf(HMTypedVar("*", ConstrType("int"))),
                parseAssertion(getSExps("(@ f nu i * j)")[0])
        )

        val set = instantiateGenericDoubleQualifier(
                HMTypedVar("mi_nu", ConstrType("int")),
                listOf(HMTypedVar("i", ConstrType("int")),
                        HMTypedVar("y", ConstrType("int")),
                        HMTypedVar("j", ConstrType("int"))),
                qual1
        )

        assertEquals(setOf(("i" to "j") to "(@ f mi_nu i y j)", ("_J_1" to "j") to "(@ f mi_nu _J_1 i j)", ("i" to "_J_2") to "(@ f mi_nu i j _J_2)"), set.map { (it.boundVar1 to it.boundVar2) to it.assertion.toSExp() }.toSet())
    }

    @Test fun instantiateDoubleQualifier2() {
        val qual1 = GenericDoubleQualifier(HMTypedVar("nu", ConstrType("int")), "i", "j",
                listOf(HMTypedVar("*", ConstrType("int")), HMTypedVar("**", ConstrType("int"))),
                parseAssertion(getSExps("(@ f nu i * ** j)")[0])
        )

        val set = instantiateGenericDoubleQualifier(
                HMTypedVar("mi_nu", ConstrType("int")),
                listOf(HMTypedVar("i", ConstrType("int")),
                        HMTypedVar("j", ConstrType("int"))),
                qual1
        )

        assertEquals(setOf(("_J_1" to "j") to "(@ f mi_nu _J_1 i i j)",
                ("_J_2" to "_J_3") to "(@ f mi_nu _J_2 i j _J_3)",
                ("i" to "_J_6") to "(@ f mi_nu i j j _J_6)",
                ("_J_4" to "_J_5") to "(@ f mi_nu _J_4 j i _J_5)"), set.map { (it.boundVar1 to it.boundVar2) to it.assertion.toSExp() }.toSet())
    }
}