package es.ucm.caviart

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class QStarGeneratorTest {

    val header = """(verification-unit dummy
       :qset (Q (nu1 int ((* int)) (@ <= nu1 *)) (nu2 int ((* int) (** int)) (@ <= nu (@ + * **))))
       :qset (QI i (nu (array int) () (@ >= nu 0)) (nu (array int) () (@ >= nu 3)) (nu (array int) ((* int)) (@ <= * nu)))
       :qset (QE i (nu (array int) () (@ >= (@ get-array nu i) 0)) (nu (array int) ((** (array int)) (* int)) (@ >= (@ get-array ** i) *)))
       :qset (QII i j (nu (array int) () (@ >= j 0)) (nu (array int) () (@ <= i j)) (nu (array int) ((* int)) (@ <= * j)))
       :qset (QEE i j (nu (array int) ((* (array int))) (@ <= (@ get-array nu i) (@ get-array * j))))
       :kappa (kappa1 ((nu int) (x int) (y int)))
              (kappa2 ((nu int) (x int) (y bool)) (Q (@ <= nu x) (@ = y true)))

       :mu (mu1 ((NU (array int)) (a (array int)) (x int) (y int) (z bool)))
    )"""

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

    @Test fun generateKappas1() {
        val p = parseVerificationUnit(getSExps(header))
        val kappas = p.kappaDeclarations.map {
            generateKappa(it, p.qset)
        }
        assertEquals(2, kappas.size)
        assertEquals("kappa1", kappas[0].name)
        assertEquals("kappa2", kappas[1].name)
        assertEquals("(nu int) (x int) (y int)", kappas[0].arguments.map { it.toSExp() }.joinToString(" "))
        assertEquals("(nu int) (x int) (y bool)", kappas[1].arguments.map { it.toSExp() }.joinToString(" "))
        assertEquals(setOf("(@ <= nu x)", "(@ <= nu y)", "(@ <= nu (@ + x x))", "(@ <= nu (@ + x y))", "(@ <= nu (@ + y x))", "(@ <= nu (@ + y y))"),
                kappas[0].qStar.map { it.toSExp() }.toSet())
        assertEquals(setOf("(@ <= nu x)", "(@ = y true)"), kappas[1].qStar.map { it.toSExp() }.toSet())
    }

    @Test fun generateMus1() {
        val p = parseVerificationUnit(getSExps(header))
        val mus = p.muDeclarations.map {
            generateMu(it, p.qISet, p.qESet, p.qIISet, p.qEESet, p.qLenSet)
        }

        val mu = mus[0]
        assertEquals("mu1", mu.name)
        assertEquals("(NU (array int)) (a (array int)) (x int) (y int) (z bool)", mu.arguments.map { it.toSExp() }.joinToString(" "))
        assertEquals("_J_1", mu.boundVar1)
        assertEquals("_J_2", mu.boundVar2)
        assertEquals("(@ >= NU 0) (@ >= NU 3) (@ <= x NU) (@ <= y NU)", mu.qI.map { it.toSExp() }.joinToString(" "))
        assertEquals("(@ >= (@ get-array NU _J_1) 0)[NU / int] (@ >= (@ get-array a _J_1) x)[a / int] (@ >= (@ get-array a _J_1) y)[a / int]",
                mu.qE.map { "${it.qualifier.toSExp()}[${it.arrayNames.map { (v, t) -> "$v / ${t.toSExp()}" }.joinToString(" | ")}]" }.joinToString(" "))
        assertEquals("(@ >= _J_2 0) (@ <= _J_1 _J_2) (@ <= x _J_2) (@ <= y _J_2)", mu.qII.map { it.toSExp() }.joinToString(" "))
        assertEquals("(@ <= (@ get-array NU _J_1) (@ get-array a _J_2))[NU / int][a / int]",
                mu.qEE.map { "${it.qualifier.toSExp()}[${it.arrayNames1.map { (v, t) -> "$v / ${t.toSExp()}" }.joinToString(" | ")}][${it.arrayNames2.map { (v, t) -> "$v / ${t.toSExp()}" }.joinToString(" | ")}]" }.joinToString(" "))
    }
}