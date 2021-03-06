package es.ucm.caviart

import es.ucm.caviart.ast.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

class ParserTest {
    private val fillDef = """(define fill ((xs (array int)) (elem int)) ((res (array int)))
    (declare
        (assertion
            (precd true)
            (postcd (forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len res)) (@ = (@ get-array res i) elem)))))))
    (letfun (
        (filln ((n int) (elem int) (xs (array int))) ((res (array int)))
            (let ((l int)) (@ len xs)
              (let ((b bool)) (@ >= n l)
                (case b (
                    ((@@ true) xs)
                    ((@@ false) (let ((xsp (array int))) (@ set-array xs n elem)
                                (let ((n1 int)) (@ + n (the int 1)) (@ filln n1 elem xsp))))))))))
        (@ filln (the int 0) elem xs)))"""

    private val insertDef = """(define insert ((x int) (m int) (a (array int))) ((res (array int)))
  (declare
   (assertion
    (precd (and (@ <= (the int 0) m) (@ < m (@ len a)) (forall ((i int) (j int))
                (-> (@ <= (the int 0) i)
                (-> (@ <= i j)
                (-> (@ < j m)
                    (@ <= (@ get_array a i) (@ get_array a j))))))))
    (postcd (forall ((i int) (j int))
                (-> (@ <= (the int 0) i)
                (-> (@ <= i j)
                (-> (@ <= j m)
                    (@ <= (@ get_array res i) (@ get_array res j)))))))))
  (letfun (
    (f2 ((x int) (m int) (i int) (a (array int))) ((res2 (array int)))
        (let ((b1 bool)) (@ >= i (the int 0))
         (case b1 (
            ((@@ false) (@ f4 x m i a))
            ((@@ true) (let ((e int)) (@ get-array a i)
                         (let ((b2 bool)) (@ < x e)
                         (case b2 (
                            ((@@ true) (let ((e int)) (@ get-array a i)
                                       (let ((i2 int)) (@ + i (the int 1))
                                       (let ((ap (array int))) (@ set-array a i2 e)
                                       (let ((i3 int)) (@ - i (the int 1)) (@ f2 x m i3 ap))))))
                            ((@@ false) (@ f4 x m i a))
                         )))))
          ))
         ))
    (f4 ((x int) (m int) (i int) (a (array int))) ((res4 (array int)))
        (let ((i2 int)) (@ + i (the int 1))
        (let ((ap (array int))) (@ set-array a i2 x) ap)))
  ) (let ((i int)) (@ - m (the int 1)) (@ f2 x m i a))))"""

    private val selsortDef = """(define selsort ((arr (array int))) ((res (array int)))
  (declare
   (assertion
    (precd (@ <= (the int 0) (@ len arr)))
    (postcd (forall ((i int) (j int))
                (-> (@ <= (the int 0) i)
                (-> (@ <= i j)
                (-> (@ < j (@ len res))
                    (@ <= (@ get-array res i) (@ get-array res j)))))))))

  (letfun (
    (f1 ((n int) (arr (array int))) ((res1 (array int)))
        (let ((l int)) (@ len arr)
        (let ((b bool)) (@ < n l)
        (case b (
            ((@@ false) arr)

            ((@@ true)
                (let ((cur int)) (@ + n (the int 1)) (@ f2 n n cur arr)))
        )))))
    (f2 ((n int) (minIdx int) (cur int) (arr (array int))) ((res2 (array int)))
        (let ((l int)) (@ len arr)
        (let ((b bool)) (@ < cur l)
        (case b (
            ((@@ false)
                (let ((min int)) (@ get-array arr minIdx)
                (let ((elemN int)) (@ get-array arr n)
                (let ((arrp (array int))) (@ set-array arr minIdx elemN)
                (let ((arrpp (array int))) (@ set-array arrp n min)
                (let ((n1 int)) (@ + n (the int 1)) (@ f1 n1 arrpp)))))))

            ((@@ true)
                (let ((elemCur int)) (@ get-array arr cur)
                (let ((min int)) (@ get-array arr minIdx)
                (let ((b1 bool)) (@ < elemCur min)
                (let ((cur1 int)) (@ + cur (the int 1))
                (case b1 (
                    ((@@ false) (@ f2 n minIdx cur1 arr))
                    ((@@ true) (@ f2 n cur cur1 arr))
                )))))))

        )))))
  ) (@ f1 (the int 0) arr)))"""


    private val header1 = """(verification-unit "fill"
		   :sources (((:lang :handmade-clir)
			      (:module :self)))
		   :uses (:ir)
		   :documentation "A function that fills the elements of an array with a given value"
       :external (set-array ((xs (array int))
                             (i (qual nu int (and (@ <= (the int 0) nu) (@ < nu (@ len xs)))))
                             (nv int))
                            ((res (qual nu (array int) (@ = nu (@ set-array xs i nv))))))
       :external (get-array ((xs (array int))
                             (i (qual nu int (and (@ <= (the int 0) nu) (@ < nu (@ len xs))))))
                            ((res (qual nu int (@ = nu (@ get-array xs i))))))
       )"""

    private val header2 = """(verification-unit "fill"
		   :sources (((:lang :handmade-clir)
			      (:module :self)))
		   :uses (:ir)
		   :documentation "A function that fills the elements of an array with a given value"
       :external (set-array ((xs (array int))
                             (i (qual nu int (and (@ <= (the int 0) nu) (@ < nu (@ len xs)))))
                             (nv int))
                            ((res (qual nu (array int) (@ = nu (@ set-array xs i nv))))))
                (get-array ((xs (array int))
                             (i (qual nu int (and (@ <= (the int 0) nu) (@ < nu (@ len xs))))))
                            ((res (qual nu int (@ = nu (@ get-array xs i))))))
       )"""

    private val header3 = """(verification-unit "fill"
       :qset (Q (nu int () (@ <= (the int 0) nu)) (nu int ((x int)) (@ <= x nu)) (nu int ((x int)) (@ <= nu x)) (nu char () (@ is-upper nu)))
       :qset (QLen (nu (array int) ((x int)) (and (@ <= (the int 0) x) (@ < x (@ len nu)))) (nu (array int) ((x int) (y int)) (and (@ <= x y) (@ >= x (@ len nu))))))
    """

    private val header4 = """(verification-unit "fill"
       :qset (QI j1 (nu (array int) () (@ <= (the int 0) j1)) (nu (array int) ((x int)) (@ <= j1 x)))
             (QE j1 (nu (array int) ((x int)) (@ = (@ get-array nu j1) x))))
    """

    private val header5 = """(verification-unit bu
       :qset (QII j1 j2 (nu (array int) () (@ <= j1 j2)) (nu (array int) () (@ < j2 (@ len nu))))
       :qset (QEE j1 j2 (nu (array int) () (@ <= (@ get-array nu j1) (@ get-array nu j2)))))
    """

    private val header6 = """(verification-unit bu
       :kappa (kappa1 ((nu int) (x int) (y int)))
              (kappa2 ((nu int) (x int) (y int)) (Q (@ <= nu x) (@ <= y nu))))
    """

    private val header7 = """(verification-unit bu
       :mu (mu1 ((nu int) (a (array int)) (x int)))
       :mu (mu2 ((nu int) (a (array int)) (x int)) (QI i (@ < i x) (@ < nu i)) (QEE i j (@ < (@ get-array a i) (@ get-array a j)))))
    """

    @Test fun intType() {
        val p = parseType(getSExps("int")[0])
        assertTrue(p is ConstrType)
        assertEquals(0, (p as ConstrType).arguments.size)
        assertEquals("int", p.typeConstructor)
    }

    @Test fun boolType() {
        val p = parseType(getSExps("bool")[0])
        assertTrue(p is ConstrType)
        assertEquals(0, (p as ConstrType).arguments.size)
        assertEquals("bool", p.typeConstructor)
    }

    @Test fun varType() {
        try {
            parseType(getSExps("'a")[0])
            fail("Exception expected")
        } catch(e: UnsupportedTypeVariables) {

        }
    }

    @Test fun arrayType() {
        val p = parseType(getSExps("(array int)")[0])
        assertTrue(p is ConstrType)
        assertEquals(1, (p as ConstrType).arguments.size)
        assertEquals("array", p.typeConstructor)
        val param = p.arguments[0]
        assertTrue(param is ConstrType && param.typeConstructor == "int" && param.arguments.isEmpty())
    }

    @Test fun mapType() {
        val p = parseType(getSExps("(map int int)")[0])
        assertTrue(p is ConstrType)
        assertEquals(2, (p as ConstrType).arguments.size)
        assertEquals("map", p.typeConstructor)
        val param1 = p.arguments[0]
        val param2 = p.arguments[1]
        assertTrue(param1 is ConstrType)
        assertEquals(0, (param1 as ConstrType).arguments.size)
        assertEquals("int", param1.typeConstructor)
        assertTrue(param2 is ConstrType && param2.typeConstructor == "int" && param2.arguments.isEmpty())
    }

    @Test fun intTypePPrint() {
        val p = parseType(getSExps("int")[0])
        assertEquals("int", p.toSExp().toString())
    }

    @Test fun boolTypePPrint() {
        val p = parseType(getSExps("bool")[0])
        assertEquals("bool", p.toSExp().toString())
    }

    @Test fun varTypePPrint() {
        val p = parseType(getSExps("int")[0])
        assertEquals("int", p.toSExp().toString())
    }

    @Test fun arrayTypePPrint() {
        val p = parseType(getSExps("(array int)")[0])
        assertEquals("(array int)", p.toSExp().toString())
    }

    @Test fun mapTypePPrint() {
        val p = parseType(getSExps("(map int int)")[0])
        assertEquals("(map int int)", p.toSExp().toString())
    }

    @Test fun complexType() {
        val p = parseType(getSExps("(map int (array (array int)))")[0])
        assertEquals("(map int (array (array int)))", p.toSExp().toString())
    }

    @Test fun fillDefinition() {
        val p = parseTopLevelFunctionDefinition(getSExps(fillDef)[0])
        val expected = "(fill ((xs (array int)) (elem int)) ((res (array int))) (declare (assertion (precd true) (postcd (forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len res)) (@ = (@ get-array res i) elem))))))) (letfun ((filln ((n int) (elem int) (xs (array int))) ((res (array int))) (declare (assertion (precd true) (postcd true))) (let ((l int)) (@ len xs) (let ((b bool)) (@ >= n l) (case b (((@@ true) xs) ((@@ false) (let ((xsp (array int))) (@ set-array xs n elem) (let ((n1 int)) (@ + n (the int 1)) (@ filln n1 elem xsp)))))))))) (@ filln (the int 0) elem xs)))"
        assertEquals(expected, p.toSExp().toString())
    }

    @Test fun insertDefinition() {
        val p = parseTopLevelFunctionDefinition(getSExps(insertDef)[0])
        val expected = "(insert ((x int) (m int) (a (array int))) ((res (array int))) (declare (assertion (precd (and (@ <= (the int 0) m) (@ < m (@ len a)) (forall ((i int) (j int)) (-> (@ <= (the int 0) i) (-> (@ <= i j) (-> (@ < j m) (@ <= (@ get_array a i) (@ get_array a j)))))))) (postcd (forall ((i int) (j int)) (-> (@ <= (the int 0) i) (-> (@ <= i j) (-> (@ <= j m) (@ <= (@ get_array res i) (@ get_array res j))))))))) (letfun ((f2 ((x int) (m int) (i int) (a (array int))) ((res2 (array int))) (declare (assertion (precd true) (postcd true))) (let ((b1 bool)) (@ >= i (the int 0)) (case b1 (((@@ false) (@ f4 x m i a)) ((@@ true) (let ((e int)) (@ get-array a i) (let ((b2 bool)) (@ < x e) (case b2 (((@@ true) (let ((e int)) (@ get-array a i) (let ((i2 int)) (@ + i (the int 1)) (let ((ap (array int))) (@ set-array a i2 e) (let ((i3 int)) (@ - i (the int 1)) (@ f2 x m i3 ap)))))) ((@@ false) (@ f4 x m i a))))))))))) (f4 ((x int) (m int) (i int) (a (array int))) ((res4 (array int))) (declare (assertion (precd true) (postcd true))) (let ((i2 int)) (@ + i (the int 1)) (let ((ap (array int))) (@ set-array a i2 x) ap)))) (let ((i int)) (@ - m (the int 1)) (@ f2 x m i a))))"
        assertEquals(expected, p.toSExp().toString())
    }

    @Test fun selsortDefinition() {
        val p = parseTopLevelFunctionDefinition(getSExps(selsortDef)[0])
        val expected = "(selsort ((arr (array int))) ((res (array int))) (declare (assertion (precd (@ <= (the int 0) (@ len arr))) (postcd (forall ((i int) (j int)) (-> (@ <= (the int 0) i) (-> (@ <= i j) (-> (@ < j (@ len res)) (@ <= (@ get-array res i) (@ get-array res j))))))))) (letfun ((f1 ((n int) (arr (array int))) ((res1 (array int))) (declare (assertion (precd true) (postcd true))) (let ((l int)) (@ len arr) (let ((b bool)) (@ < n l) (case b (((@@ false) arr) ((@@ true) (let ((cur int)) (@ + n (the int 1)) (@ f2 n n cur arr)))))))) (f2 ((n int) (minIdx int) (cur int) (arr (array int))) ((res2 (array int))) (declare (assertion (precd true) (postcd true))) (let ((l int)) (@ len arr) (let ((b bool)) (@ < cur l) (case b (((@@ false) (let ((min int)) (@ get-array arr minIdx) (let ((elemN int)) (@ get-array arr n) (let ((arrp (array int))) (@ set-array arr minIdx elemN) (let ((arrpp (array int))) (@ set-array arrp n min) (let ((n1 int)) (@ + n (the int 1)) (@ f1 n1 arrpp))))))) ((@@ true) (let ((elemCur int)) (@ get-array arr cur) (let ((min int)) (@ get-array arr minIdx) (let ((b1 bool)) (@ < elemCur min) (let ((cur1 int)) (@ + cur (the int 1)) (case b1 (((@@ false) (@ f2 n minIdx cur1 arr)) ((@@ true) (@ f2 n cur cur1 arr))))))))))))))) (@ f1 (the int 0) arr)))"
        assertEquals(expected, p.toSExp().toString())
    }

    @Test fun externals1() {
        val p = parseVerificationUnit(getSExps(header1))
        assertEquals("fill", p.name)
        assertEquals(2, p.external.size)
        val setArray = p.external["set-array"]!!
        val getArray = p.external["get-array"]!!
        assertEquals("(xs (array int))", setArray.input[0].toSExp().toString())
        assertEquals("(i (qual nu int (and (@ <= (the int 0) nu) (@ < nu (@ len xs)))))", setArray.input[1].toSExp().toString())
        assertEquals("(nv int)", setArray.input[2].toSExp().toString())
        assertEquals("(res (qual nu (array int) (@ = nu (@ set-array xs i nv))))", setArray.output[0].toSExp().toString())

        assertEquals("(xs (array int))", getArray.input[0].toSExp().toString())
        assertEquals("(i (qual nu int (and (@ <= (the int 0) nu) (@ < nu (@ len xs)))))", getArray.input[1].toSExp().toString())
        assertEquals("(res (qual nu int (@ = nu (@ get-array xs i))))", getArray.output[0].toSExp().toString())
    }

    @Test fun externals2() {
        val p = parseVerificationUnit(getSExps(header2))
        assertEquals("fill", p.name)
        assertEquals(2, p.external.size)
        val setArray = p.external["set-array"]!!
        val getArray = p.external["get-array"]!!
        assertEquals("(xs (array int))", setArray.input[0].toSExp().toString())
        assertEquals("(i (qual nu int (and (@ <= (the int 0) nu) (@ < nu (@ len xs)))))", setArray.input[1].toSExp().toString())
        assertEquals("(nv int)", setArray.input[2].toSExp().toString())
        assertEquals("(res (qual nu (array int) (@ = nu (@ set-array xs i nv))))", setArray.output[0].toSExp().toString())

        assertEquals("(xs (array int))", getArray.input[0].toSExp().toString())
        assertEquals("(i (qual nu int (and (@ <= (the int 0) nu) (@ < nu (@ len xs)))))", getArray.input[1].toSExp().toString())
        assertEquals("(res (qual nu int (@ = nu (@ get-array xs i))))", getArray.output[0].toSExp().toString())
    }

    @Test fun qsets1() {
        val p = parseVerificationUnit(getSExps(header3))
        assertEquals(4, p.qSet.size)
        assertTrue(p.qSet.all { it.nu.varName == "nu" })
        assertEquals(setOf("(@ <= (the int 0) nu)", "(@ <= x nu)", "(@ <= nu x)", "(@ is-upper nu)"), p.qSet.map { it.assertion.toSExp().toString() }.toSet())
    }

    @Test fun qsets2() {
        val p = parseVerificationUnit(getSExps(header3))
        assertEquals(2, p.qLenSet.size)
        assertTrue(p.qLenSet.all { it.nu.varName == "nu" })
        assertEquals(setOf("(and (@ <= (the int 0) x) (@ < x (@ len nu)))", "(and (@ <= x y) (@ >= x (@ len nu)))"), p.qLenSet.map { it.assertion.toSExp().toString() }.toSet())
    }

    @Test fun qsets3() {
        val p = parseVerificationUnit(getSExps(header4))
        assertEquals(2, p.qISet.size)
        assertTrue(p.qISet.all { it.nu.varName == "nu" })
        assertTrue(p.qISet.all { it.boundVar == "j1" })
        assertEquals(setOf("(@ <= (the int 0) j1)", "(@ <= j1 x)"), p.qISet.map { it.assertion.toSExp().toString() }.toSet())
    }

    @Test fun qsets4() {
        val p = parseVerificationUnit(getSExps(header4))
        assertEquals(1, p.qESet.size)
        assertTrue(p.qESet.all { it.nu.varName == "nu" })
        assertTrue(p.qESet.all { it.boundVar == "j1" })
        assertEquals(setOf("(@ = (@ get-array nu j1) x)"), p.qESet.map { it.assertion.toSExp().toString() }.toSet())
    }

    @Test fun qsets5() {
        val p = parseVerificationUnit(getSExps(header5))
        assertEquals(2, p.qIISet.size)
        assertTrue(p.qIISet.all { it.nu.varName == "nu" })
        assertTrue(p.qIISet.all { it.boundVar1 == "j1" })
        assertTrue(p.qIISet.all { it.boundVar2 == "j2" })
        assertEquals(setOf("(@ <= j1 j2)", "(@ < j2 (@ len nu))"), p.qIISet.map { it.assertion.toSExp().toString() }.toSet())
    }

    @Test fun qsets6() {
        val p = parseVerificationUnit(getSExps(header5))
        assertEquals(1, p.qEESet.size)
        assertTrue(p.qEESet.all { it.nu.varName == "nu" })
        assertTrue(p.qEESet.all { it.boundVar1 == "j1" })
        assertTrue(p.qEESet.all { it.boundVar2 == "j2" })
        assertEquals(setOf("(@ <= (@ get-array nu j1) (@ get-array nu j2))"), p.qEESet.map { it.assertion.toSExp().toString() }.toSet())
    }

    @Test fun kappaDef1() {
        val p = parseVerificationUnit(getSExps(header6))
        assertEquals(2, p.kappaDeclarations.size)
        assertEquals(setOf("(nu int)"), p.kappaDeclarations.map { it.nuVar.toSExp().toString() }.toSet())
        assertEquals(setOf("(x int)", "(y int)"), p.kappaDeclarations.flatMap { it.parameters.map { it.toSExp().toString() } }.toSet())
        assertNull(p.kappaDeclarations.find { it.name == "kappa1" }!!.qSet)
        assertEquals(setOf("(@ <= nu x)", "(@ <= y nu)"), p.kappaDeclarations.find { it.name == "kappa2" }!!.qSet!!.map { it.toSExp().toString() }.toSet())
    }

    @Test fun muDef1() {
        val p = parseVerificationUnit(getSExps(header7))
        assertEquals(2, p.muDeclarations.size)
        assertEquals(setOf("(nu int)"), p.muDeclarations.map { it.nuVar.toSExp().toString() }.toSet())
        assertEquals(setOf("(a (array int))", "(x int)"), p.muDeclarations.flatMap { it.parameters.map { it.toSExp().toString() } }.toSet())
        assertNull(p.muDeclarations.find { it.name == "mu1" }!!.qISet)
        assertNull(p.muDeclarations.find { it.name == "mu1" }!!.qESet)
        assertNull(p.muDeclarations.find { it.name == "mu1" }!!.qIISet)
        assertNull(p.muDeclarations.find { it.name == "mu1" }!!.qEESet)
        assertNull(p.muDeclarations.find { it.name == "mu1" }!!.qLenSet)
    }

    @Test fun muDef2() {
        val p = parseVerificationUnit(getSExps(header7))
        assertEquals(2, p.muDeclarations.size)
        assertEquals(setOf("(nu int)"), p.muDeclarations.map { it.nuVar.toSExp().toString() }.toSet())
        assertEquals(setOf("(a (array int))", "(x int)"), p.muDeclarations.flatMap { it.parameters.map { it.toSExp().toString() } }.toSet())
        assertNull(p.muDeclarations.find { it.name == "mu2" }!!.qESet)
        assertNull(p.muDeclarations.find { it.name == "mu2" }!!.qIISet)
        assertNull(p.muDeclarations.find { it.name == "mu2" }!!.qLenSet)
        assertEquals(setOf("i"), p.muDeclarations.find {it.name == "mu2"}!!.qISet!!.map { it.boundVar }.toSet())
        assertEquals(setOf("i"), p.muDeclarations.find {it.name == "mu2"}!!.qEESet!!.map { it.boundVar1 }.toSet())
        assertEquals(setOf("j"), p.muDeclarations.find {it.name == "mu2"}!!.qEESet!!.map { it.boundVar2 }.toSet())
        assertEquals(setOf("(@ < (@ get-array a i) (@ get-array a j))"), p.muDeclarations.find {it.name == "mu2"}!!.qEESet!!.map { it.assertion.toSExp().toString() }.toSet())
    }

}