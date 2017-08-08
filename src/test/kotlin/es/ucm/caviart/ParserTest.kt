package es.ucm.caviart

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ParserTest {
    val fillDef = """(define fill ((xs (array 'a)) (elem 'a)) ((res (array 'a)))
    (declare
        (assertion
            (precd true)
            (postcd (forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len res)) (@ = (@ get-array res i) elem)))))))
    (letfun (
        (filln ((n int) (elem 'a) (xs (array 'a))) ((res (array 'a)))
            (let ((l int)) (@ len xs)
              (let ((b bool)) (@ >= n l)
                (case b (
                    ((@@ true) xs)
                    ((@@ false) (let ((xsp (array 'a))) (@ set-array xs n elem)
                                (let ((n1 int)) (@ + n (the int 1)) (@ filln n1 elem xsp))))))))))
        (@ filln (the int 0) elem xs)))"""

    val insertDef = """(define insert ((x int) (m int) (a (array int))) ((res (array int)))
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

    val selsortDef = """(define selsort ((arr (array 'a))) ((res (array 'a)))
  (declare
   (assertion
    (precd (@ <= (the int 0) (@ len arr)))
    (postcd (forall ((i int) (j int))
                (-> (@ <= (the int 0) i)
                (-> (@ <= i j)
                (-> (@ < j (@ len res))
                    (@ <= (@ get-array res i) (@ get-array res j)))))))))

  (letfun (
    (f1 ((n int) (arr (array 'a))) ((res1 (array 'a)))
        (let ((l int)) (@ len arr)
        (let ((b bool)) (@ < n l)
        (case b (
            ((@@ false) arr)

            ((@@ true)
                (let ((cur int)) (@ + n (the int 1)) (@ f2 n n cur arr)))
        )))))
    (f2 ((n int) (minIdx int) (cur int) (arr (array 'a))) ((res2 (array 'a)))
        (let ((l int)) (@ len arr)
        (let ((b bool)) (@ < cur l)
        (case b (
            ((@@ false)
                (let ((min 'a)) (@ get-array arr minIdx)
                (let ((elemN 'a)) (@ get-array arr n)
                (let ((arrp (array 'a))) (@ set-array arr minIdx elemN)
                (let ((arrpp (array 'a))) (@ set-array arrp n min)
                (let ((n1 int)) (@ + n (the int 1)) (@ f1 n1 arrpp)))))))

            ((@@ true)
                (let ((elemCur 'a)) (@ get-array arr cur)
                (let ((min 'a)) (@ get-array arr minIdx)
                (let ((b1 bool)) (@ < elemCur min)
                (let ((cur1 int)) (@ + cur (the int 1))
                (case b1 (
                    ((@@ false) (@ f2 n minIdx cur1 arr))
                    ((@@ true) (@ f2 n cur cur1 arr))
                )))))))

        )))))
  ) (@ f1 (the int 0) arr)))"""

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
        val p = parseType(getSExps("'a")[0])
        assertTrue(p is VarType)
        assertEquals("'a", (p as VarType).variable)
    }

    @Test fun arrayType() {
        val p = parseType(getSExps("(array 'a)")[0])
        assertTrue(p is ConstrType)
        assertEquals(1, (p as ConstrType).arguments.size)
        assertEquals("array", p.typeConstructor)
        val param = p.arguments[0]
        assertTrue(param is VarType)
        assertEquals("'a", (param as VarType).variable)
    }

    @Test fun mapType() {
        val p = parseType(getSExps("(map int 'a)")[0])
        assertTrue(p is ConstrType)
        assertEquals(2, (p as ConstrType).arguments.size)
        assertEquals("map", p.typeConstructor)
        val param1 = p.arguments[0]
        val param2 = p.arguments[1]
        assertTrue(param1 is ConstrType)
        assertEquals(0, (param1 as ConstrType).arguments.size)
        assertEquals("int", param1.typeConstructor)
        assertTrue(param2 is VarType)
        assertEquals("'a", (param2 as VarType).variable)
    }

    @Test fun intTypePPrint() {
        val p = parseType(getSExps("int")[0])
        assertEquals("int", p.toSExp())
    }

    @Test fun boolTypePPrint() {
        val p = parseType(getSExps("bool")[0])
        assertEquals("bool", p.toSExp())
    }

    @Test fun varTypePPrint() {
        val p = parseType(getSExps("'a")[0])
        assertEquals("'a", p.toSExp())
    }

    @Test fun arrayTypePPrint() {
        val p = parseType(getSExps("(array 'a)")[0])
        assertEquals("(array 'a)", p.toSExp())
    }

    @Test fun mapTypePPrint() {
        val p = parseType(getSExps("(map int 'a)")[0])
        assertEquals("(map int 'a)", p.toSExp())
    }

    @Test fun complexType() {
        val p = parseType(getSExps("(map int (array (array 'a)))")[0])
        assertEquals("(map int (array (array 'a)))", p.toSExp())
    }

    @Test fun fillDefinition() {
        val p = parseTopLevelFunctionDefinition(getSExps(fillDef)[0])
        val expected = "(fill ((xs (array 'a)) (elem 'a)) ((res (array 'a))) (declare (assertion (precd true) (postcd (forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len res)) (@ = (@ get-array res i) elem))))))) (letfun ((filln ((n int) (elem 'a) (xs (array 'a))) ((res (array 'a))) (declare (assertion (precd false) (postcd true))) (let ((l int)) (@ len xs) (let ((b bool)) (@ >= n l) (case b (((@@ true) xs) ((@@ false) (let ((xsp (array 'a))) (@ set-array xs n elem) (let ((n1 int)) (@ + n (the int 1)) (@ filln n1 elem xsp)))))))))) (@ filln (the int 0) elem xs)))"
        assertEquals(expected, p.toSExp())
    }

    @Test fun insertDefinition() {
        val p = parseTopLevelFunctionDefinition(getSExps(insertDef)[0])
        val expected = "(insert ((x int) (m int) (a (array int))) ((res (array int))) (declare (assertion (precd (and (@ <= (the int 0) m) (@ < m (@ len a)) (forall ((i int) (j int)) (-> (@ <= (the int 0) i) (-> (@ <= i j) (-> (@ < j m) (@ <= (@ get_array a i) (@ get_array a j)))))))) (postcd (forall ((i int) (j int)) (-> (@ <= (the int 0) i) (-> (@ <= i j) (-> (@ <= j m) (@ <= (@ get_array res i) (@ get_array res j))))))))) (letfun ((f2 ((x int) (m int) (i int) (a (array int))) ((res2 (array int))) (declare (assertion (precd false) (postcd true))) (let ((b1 bool)) (@ >= i (the int 0)) (case b1 (((@@ false) (@ f4 x m i a)) ((@@ true) (let ((e int)) (@ get-array a i) (let ((b2 bool)) (@ < x e) (case b2 (((@@ true) (let ((e int)) (@ get-array a i) (let ((i2 int)) (@ + i (the int 1)) (let ((ap (array int))) (@ set-array a i2 e) (let ((i3 int)) (@ - i (the int 1)) (@ f2 x m i3 ap)))))) ((@@ false) (@ f4 x m i a))))))))))) (f4 ((x int) (m int) (i int) (a (array int))) ((res4 (array int))) (declare (assertion (precd false) (postcd true))) (let ((i2 int)) (@ + i (the int 1)) (let ((ap (array int))) (@ set-array a i2 x) ap)))) (let ((i int)) (@ - m (the int 1)) (@ f2 x m i a))))"
        assertEquals(expected, p.toSExp())
    }

    @Test fun selsortDefinition() {
        val p = parseTopLevelFunctionDefinition(getSExps(selsortDef)[0])
        val expected = "(selsort ((arr (array 'a))) ((res (array 'a))) (declare (assertion (precd (@ <= (the int 0) (@ len arr))) (postcd (forall ((i int) (j int)) (-> (@ <= (the int 0) i) (-> (@ <= i j) (-> (@ < j (@ len res)) (@ <= (@ get-array res i) (@ get-array res j))))))))) (letfun ((f1 ((n int) (arr (array 'a))) ((res1 (array 'a))) (declare (assertion (precd false) (postcd true))) (let ((l int)) (@ len arr) (let ((b bool)) (@ < n l) (case b (((@@ false) arr) ((@@ true) (let ((cur int)) (@ + n (the int 1)) (@ f2 n n cur arr)))))))) (f2 ((n int) (minIdx int) (cur int) (arr (array 'a))) ((res2 (array 'a))) (declare (assertion (precd false) (postcd true))) (let ((l int)) (@ len arr) (let ((b bool)) (@ < cur l) (case b (((@@ false) (let ((min 'a)) (@ get-array arr minIdx) (let ((elemN 'a)) (@ get-array arr n) (let ((arrp (array 'a))) (@ set-array arr minIdx elemN) (let ((arrpp (array 'a))) (@ set-array arrp n min) (let ((n1 int)) (@ + n (the int 1)) (@ f1 n1 arrpp))))))) ((@@ true) (let ((elemCur 'a)) (@ get-array arr cur) (let ((min 'a)) (@ get-array arr minIdx) (let ((b1 bool)) (@ < elemCur min) (let ((cur1 int)) (@ + cur (the int 1)) (case b1 (((@@ false) (@ f2 n minIdx cur1 arr)) ((@@ true) (@ f2 n cur cur1 arr))))))))))))))) (@ f1 (the int 0) arr)))"
        assertEquals(expected, p.toSExp())
    }


}