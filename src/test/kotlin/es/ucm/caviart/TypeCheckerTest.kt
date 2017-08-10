package es.ucm.caviart

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.fail

class TypeCheckerTest {
    val sourceCode = """
    (verification-unit fill)
    (define fill ((xs (array 'a)) (elem 'a)) ((res (array 'a)))
    (declare
        (assertion
            (precd true)
            (postcd (forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len res)) (@ = (@ get-array res i) elem)))))))
        (letfun (
            (filln ((n int) (elem 'a) (xs (array 'a))) ((res (array 'a)))
                (let ((l int)) (@ len xs)
                  (let ((b bool)) (@ >= n l)
                    (case b (
                        (true xs)
                        (false (let ((xsp (array 'a))) (@ set-array xs n elem)
                                    (let ((n1 int)) (@ + n (the int 1)) (@ filln n1 elem xsp))))))))))
            (@ filln (the int 0) elem xs)))

    (define insert ((x int) (m int) (a (array int))) ((res (array int)))
      (declare
       (assertion
        (precd (and (@ <= (the int 0) m) (@ < m (@ len a)) (forall ((i int) (j int))
                    (-> (@ <= (the int 0) i)
                    (-> (@ <= i j)
                    (-> (@ < j m)
                        (@ <= (@ get-array a i) (@ get-array a j))))))))
        (postcd (forall ((i int) (j int))
                    (-> (@ <= (the int 0) i)
                    (-> (@ <= i j)
                    (-> (@ <= j m)
                        (@ <= (@ get-array res i) (@ get-array res j)))))))))
      (letfun (
        (f2 ((x int) (m int) (i int) (a (array int))) ((res2 (array int)))
            (let ((b1 bool)) (@ >= i (the int 0))
             (case b1 (
                (false (@ f4 x m i a))
                (true (let ((e int)) (@ get-array a i)
                             (let ((b2 bool)) (@ < x e)
                             (case b2 (
                                (true (let ((e int)) (@ get-array a i)
                                           (let ((i2 int)) (@ + i (the int 1))
                                           (let ((ap (array int))) (@ set-array a i2 e)
                                           (let ((i3 int)) (@ - i (the int 1)) (@ f2 x m i3 ap))))))
                                (false (@ f4 x m i a))
                             )))))
              ))
             ))
        (f4 ((x int) (m int) (i int) (a (array int))) ((res4 (array int)))
            (let ((i2 int)) (@ + i (the int 1))
            (let ((ap (array int))) (@ set-array a i2 x) ap)))
      ) (let ((i int)) (@ - m (the int 1)) (@ f2 x m i a))))

    (define selsort ((arr (array int))) ((res (array int)))
          (declare
           (assertion
            (precd (@ <= (the int 0) (@ len arr)))
            (postcd (forall ((i int) (j int))
                        (-> (@ <= (the int 0) i)
                        (-> (@ <= i j)
                        (-> (@ < j (@ len res))
                            (@ <= (@ get-array res i) (@ get-array res j)))))))))

          (letfun (
            (sel_f1 ((n int) (arr (array int))) ((res1 (array int)))
                (let ((l int)) (@ len arr)
                (let ((b bool)) (@ < n l)
                (case b (
                    (false arr)

                    (true
                        (let ((cur int)) (@ + n (the int 1)) (@ sel_f2 n n cur arr)))
                )))))
            (sel_f2 ((n int) (minIdx int) (cur int) (arr (array int))) ((res2 (array int)))
                (let ((l int)) (@ len arr)
                (let ((b bool)) (@ < cur l)
                (case b (
                    (false
                        (let ((min int)) (@ get-array arr minIdx)
                        (let ((elemN int)) (@ get-array arr n)
                        (let ((arrp (array int))) (@ set-array arr minIdx elemN)
                        (let ((arrpp (array int))) (@ set-array arrp n min)
                        (let ((n1 int)) (@ + n (the int 1)) (@ sel_f1 n1 arrpp)))))))

                    (true
                        (let ((elemCur int)) (@ get-array arr cur)
                        (let ((min int)) (@ get-array arr minIdx)
                        (let ((b1 bool)) (@ < elemCur min)
                        (let ((cur1 int)) (@ + cur (the int 1))
                        (case b1 (
                            (false (@ sel_f2 n minIdx cur1 arr))
                            (true (@ sel_f2 n cur cur1 arr))
                        )))))))

                )))))
          ) (@ sel_f1 (the int 0) arr)))

    (define wrong1 ((n (qual nu int (@ < x (the int 0))))) ((res int)) (the int 0))
"""

    val program = parseVerificationUnit(getSExps(sourceCode))

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

    @Test fun checkFill() {
        checkFunctionDefinition(program.definitions[0], initialEnvironment, emptyMap())
    }

    @Test fun checkInsert() {
        checkFunctionDefinition(program.definitions[1], initialEnvironment, emptyMap())
    }

    @Test fun checkSelSort() {
        checkFunctionDefinition(program.definitions[2], initialEnvironment, emptyMap())
    }

    @Test fun checkOutOfScope() {
        try {
            checkFunctionDefinition(program.definitions[3], initialEnvironment, emptyMap())
            fail("x must be reported as undefined")
        } catch(e: UndefinedVariableException) {
            assertEquals("x", e.variable)
        } catch(e: Exception) {
            fail("Unexpected excepction")
        }
    }
}