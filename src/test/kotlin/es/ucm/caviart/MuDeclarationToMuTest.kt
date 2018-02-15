package es.ucm.caviart

import es.ucm.caviart.ast.*
import es.ucm.caviart.iterativeweakening.fromMuDeclaration
import es.ucm.caviart.utils.FreshNameGenerator
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MuDeclarationToMuTest {
    private val mu1 = MuDeclaration(
            "mu1",
            HMTypedVar("nu", arrayType(intType)),
            listOf(),
            setOf(
                    SingleQualifier("i1", "(@ >= i1 (the int 0))".toAssertion()),
                    SingleQualifier("i2", "(@ >= i2 (the int 1))".toAssertion())
            ),
            setOf(
                    SingleQualifier("i1", "(@ = (@ get-array nu i1) (the int 0))".toAssertion()),
                    SingleQualifier("i2", "(@ = (@ get-array nu i2) (the int 0))".toAssertion())
            ),
            setOf(),
            setOf(),
            setOf()
    )

    private val mu2 = MuDeclaration(
            "mu2",
            HMTypedVar("nu", arrayType(intType)),
            listOf(HMTypedVar("i", intType), HMTypedVar("a", arrayType(intType))),
            setOf(
                    SingleQualifier("i1", "(@ >= i1 i)".toAssertion())
            ),
            setOf(
                    SingleQualifier("i1", "(@ = (@ get-array nu i1) (the int 0))".toAssertion())
            ),
            setOf(),
            setOf(),
            setOf()
    )


    private val mu3 = MuDeclaration(
            "mu3",
            HMTypedVar("nu", arrayType(intType)),
            listOf(HMTypedVar("i", intType), HMTypedVar("a", arrayType(intType))),
            setOf(
                    SingleQualifier("i1", "(@ >= i1 i)".toAssertion())
            ),
            setOf(
                    SingleQualifier("i1", "(@ = (@ get-array nu i1) (the int 0))".toAssertion())
            ),
            setOf(
                    DoubleQualifier("i1", "j1", "(@ >= i1 j1)".toAssertion()),
                    DoubleQualifier("i1", "j1", "(@ < i1 (the int 0))".toAssertion())
            ),
            setOf(
                    DoubleQualifier("i1", "j1", "(@ < (@ get-array nu i1) (@ get-array a j1))".toAssertion())
            ),
            setOf()
    )

    private val mu4 = MuDeclaration(
            "mu4",
            HMTypedVar("nu", arrayType(intType)),
            listOf(HMTypedVar("a", arrayType(intType))),
            setOf(),
            setOf(),
            setOf(),
            setOf(),
            setOf("(@ =[] nu a)".toAssertion(), "(@ = (@ len nu) (@ len a))".toAssertion())
    )


    @Before fun initFreshNameGenerator() {
        FreshNameGenerator.resetGenerator()
    }


    @Test fun muSingle1() {
        val mu = fromMuDeclaration(mu1)
        assertEquals("mu1", mu.name)
        assertEquals(1, mu.arguments.size)
        assertEquals(HMTypedVar("nu", arrayType(intType)), mu.arguments[0])
        assertEquals("i", mu.boundVar1)
        assertEquals("j", mu.boundVar2)
        assertEquals(2, mu.qI.size)
        assertEquals("(@ >= i (the int 0))", mu.qI[0].toSExp().toString())
        assertEquals("(@ >= i (the int 1))", mu.qI[1].toSExp().toString())
        assertEquals(2, mu.qE.size)
        assertEquals("(@ = (@ get-array nu i) (the int 0))", mu.qE[0].qualifier.toSExp().toString())
        assertEquals("(@ = (@ get-array nu i) (the int 0))", mu.qE[1].qualifier.toSExp().toString())
        assertEquals(listOf("nu" to intType), mu.qE[0].arrayNames)
        assertEquals(listOf("nu" to intType), mu.qE[1].arrayNames)
        assert(mu.qII.isEmpty())
        assert(mu.qEE.isEmpty())
        assert(mu.qLen.isEmpty())
    }

    @Test fun muSingle2() {
        val mu = fromMuDeclaration(mu1.copy(qESet = setOf(SingleQualifier("i", "(@ < i (the int 0))".toAssertion()))))
        assertEquals("mu1", mu.name)
        assertEquals(1, mu.arguments.size)
        assertEquals(HMTypedVar("nu", arrayType(intType)), mu.arguments[0])
        assertEquals("i", mu.boundVar1)
        assertEquals("j", mu.boundVar2)
        assertEquals(2, mu.qI.size)
        assertEquals("(@ >= i (the int 0))", mu.qI[0].toSExp().toString())
        assertEquals("(@ >= i (the int 1))", mu.qI[1].toSExp().toString())
        assertEquals(1, mu.qE.size)
        assertEquals("(@ < i (the int 0))", mu.qE[0].qualifier.toSExp().toString())
        assertEquals(listOf(), mu.qE[0].arrayNames)
        assert(mu.qII.isEmpty())
        assert(mu.qEE.isEmpty())
        assert(mu.qLen.isEmpty())
    }

    @Test fun muSingle3() {
        val mu = fromMuDeclaration(mu2)
        assertEquals("mu2", mu.name)
        assertEquals(3, mu.arguments.size)
        assertEquals(HMTypedVar("nu", arrayType(intType)), mu.arguments[0])
        assertEquals(HMTypedVar("i", intType), mu.arguments[1])
        assertEquals(HMTypedVar("a", arrayType(intType)), mu.arguments[2])
        assertEquals("_I_1", mu.boundVar1)
        assertEquals("j", mu.boundVar2)
        assertEquals(1, mu.qI.size)
        assertEquals("(@ >= _I_1 i)", mu.qI[0].toSExp().toString())
        assertEquals(1, mu.qE.size)
        assertEquals("(@ = (@ get-array nu _I_1) (the int 0))", mu.qE[0].qualifier.toSExp().toString())
        assertEquals(listOf("nu" to intType), mu.qE[0].arrayNames)
        assert(mu.qII.isEmpty())
        assert(mu.qEE.isEmpty())
        assert(mu.qLen.isEmpty())
    }

    @Test fun muDouble1() {
        val mu = fromMuDeclaration(mu3)
        assertEquals("mu3", mu.name)
        assertEquals(3, mu.arguments.size)
        assertEquals(HMTypedVar("nu", arrayType(intType)), mu.arguments[0])
        assertEquals(HMTypedVar("i", intType), mu.arguments[1])
        assertEquals(HMTypedVar("a", arrayType(intType)), mu.arguments[2])
        assertEquals("_I_1", mu.boundVar1)
        assertEquals("j", mu.boundVar2)
        assertEquals(2, mu.qII.size)
        assertEquals("(@ >= _I_1 j)", mu.qII[0].toSExp().toString())
        assertEquals("(@ < _I_1 (the int 0))", mu.qII[1].toSExp().toString())
        assertEquals(1, mu.qEE.size)
        assertEquals("(@ < (@ get-array nu _I_1) (@ get-array a j))", mu.qEE[0].qualifier.toSExp().toString())
        assertEquals(listOf("nu" to intType), mu.qEE[0].arrayNames1)
        assertEquals(listOf("a" to intType), mu.qEE[0].arrayNames2)
        assert(mu.qLen.isEmpty())
    }

    @Test fun muDouble2() {
        val mu = fromMuDeclaration(mu3.copy(parameters = listOf(HMTypedVar("i", intType), HMTypedVar("j", intType), HMTypedVar("a", arrayType(intType)))))
        assertEquals("mu3", mu.name)
        assertEquals(4, mu.arguments.size)
        assertEquals(HMTypedVar("nu", arrayType(intType)), mu.arguments[0])
        assertEquals(HMTypedVar("i", intType), mu.arguments[1])
        assertEquals(HMTypedVar("j", intType), mu.arguments[2])
        assertEquals("_I_1", mu.boundVar1)
        assertEquals("_J_1", mu.boundVar2)
        assertEquals(2, mu.qII.size)
        assertEquals("(@ >= _I_1 _J_1)", mu.qII[0].toSExp().toString())
        assertEquals("(@ < _I_1 (the int 0))", mu.qII[1].toSExp().toString())
        assertEquals(1, mu.qEE.size)
        assertEquals("(@ < (@ get-array nu _I_1) (@ get-array a _J_1))", mu.qEE[0].qualifier.toSExp().toString())
        assertEquals(listOf("nu" to intType), mu.qEE[0].arrayNames1)
        assertEquals(listOf("a" to intType), mu.qEE[0].arrayNames2)
        assert(mu.qLen.isEmpty())
    }

    @Test fun muDouble3() {
        val mu = fromMuDeclaration(mu4)
        assertEquals("mu4", mu.name)
        assertEquals(2, mu.arguments.size)
        assertEquals(HMTypedVar("nu", arrayType(intType)), mu.arguments[0])
        assertEquals(HMTypedVar("a", arrayType(intType)), mu.arguments[1])
        assertEquals("i", mu.boundVar1)
        assertEquals("j", mu.boundVar2)
        assert(mu.qI.isEmpty())
        assert(mu.qE.isEmpty())
        assert(mu.qII.isEmpty())
        assert(mu.qEE.isEmpty())
        assertEquals(2, mu.qLen.size)
    }

}
