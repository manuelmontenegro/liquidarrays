package es.ucm.caviart

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QualifyFunctionalTypesTest {
    val example1 = """
(verification-unit "fill"
    :external (true () ((res bool)))
    :external (false () ((res bool)))
)
(define fill ((xs (array 'a)) (elem 'a)) ((res (array 'a)))
    (letfun (
        (filln ((n int) (elem 'a) (xs (array 'a))) ((res (array 'a)))
            (let ((l int)) (@ len xs)
              (let ((b bool)) (@ >= n l)
                (case b (
                    ((@@ true) xs)
                    ((@@ false) (let ((xsp (array 'a))) (@ set-array xs n elem)
                                (let ((n1 int)) (@ + n (the int 1)) (@ filln n1 elem xsp))))))))))
        (@ filln (the int 0) elem xs)))
"""

    val example2 = """
(verification-unit "fill")
(define insert ((x int) (m int) (a (array int))) ((res (array int)))
  (letfun (
    (f2 ((x int) (m int) (i int) (a (array int))) ((res2 (array int))) 0)
    (f4 ((x int) (m int) (i int) (a (array int))) ((res4 (array int))) 0)
  ) 0))
"""

    @Test fun qualify1() {
        val p = qualifyVeriticationUnit(parseVerificationUnit(getSExps(example1)))
        assertEquals(1, p.definitions.size)
        assertEquals(listOf("(xs (qual nu (array 'a) (@ _mu_fill_xs nu)))", "(elem (qual nu 'a (@ _kappa_fill_elem nu xs)))"), p.definitions[0].inputParams.map { it.toSExp() })
        assertEquals(listOf("(res (qual nu (array 'a) (@ _mu_fill_res nu xs elem)))"), p.definitions[0].outputParams.map { it.toSExp() })
        assertTrue(p.definitions[0].body is LetFun)
        val innerLetFun = p.definitions[0].body as LetFun
        assertEquals(1, innerLetFun.defs.size)
        assertEquals(listOf("(n (qual nu int (@ _kappa_filln_n nu xs elem)))",
                "(elem (qual nu 'a (@ _kappa_filln_elem nu xs elem n)))",
                "(xs (qual nu (array 'a) (@ _mu_filln_xs nu xs n elem)))"), innerLetFun.defs[0].inputParams.map { it.toSExp() })
        assertEquals(listOf("(res (qual nu (array 'a) (@ _mu_filln_res nu n elem xs)))"), innerLetFun.defs[0].outputParams.map { it.toSExp() })

    }

    @Test fun qualify2() {
        val p = qualifyVeriticationUnit(parseVerificationUnit(getSExps(example2)))
        assertEquals(1, p.definitions.size)
        assertEquals(
                listOf("(x (qual nu int (@ _kappa_insert_x nu)))", "(m (qual nu int (@ _kappa_insert_m nu x)))", "(a (qual nu (array int) (@ _mu_insert_a nu x m)))"),
                p.definitions[0].inputParams.map { it.toSExp() }
        )
        assertEquals(listOf("(res (qual nu (array int) (@ _mu_insert_res nu x m a)))"), p.definitions[0].outputParams.map { it.toSExp() })
        assertTrue(p.definitions[0].body is LetFun)
        val defs = (p.definitions[0].body as LetFun).defs
        assertEquals(2, defs.size)
        assertEquals(listOf("(x (qual nu int (@ _kappa_f2_x nu x m a)))", "(m (qual nu int (@ _kappa_f2_m nu m a x)))", "(i (qual nu int (@ _kappa_f2_i nu a x m)))", "(a (qual nu (array int) (@ _mu_f2_a nu a x m i)))"),
                defs[0].inputParams.map { it.toSExp() })
        assertEquals(listOf("(res2 (qual nu (array int) (@ _mu_f2_res2 nu x m i a)))"),
                defs[0].outputParams.map { it.toSExp() })
        assertEquals(listOf("(x (qual nu int (@ _kappa_f4_x nu x m a)))", "(m (qual nu int (@ _kappa_f4_m nu m a x)))", "(i (qual nu int (@ _kappa_f4_i nu a x m)))", "(a (qual nu (array int) (@ _mu_f4_a nu a x m i)))"),
                defs[1].inputParams.map { it.toSExp() })
        assertEquals(listOf("(res4 (qual nu (array int) (@ _mu_f4_res4 nu x m i a)))"),
                defs[1].outputParams.map { it.toSExp() })
        assertEquals(8, p.kappaDeclarations.size)
        assertEquals(6, p.muDeclarations.size)
    }

    @Test fun qualify3() {
        val p = qualifyVeriticationUnit(parseVerificationUnit(getSExps(example2)).copy(
                muDeclarations = setOf(MuDeclaration("_mu_insert_res", HMTypedVar("nu", ConstrType("array", listOf(ConstrType("int")))),
                        listOf(HMTypedVar("x", ConstrType("int"))), null, null, null, null, null
                ))
        ))

        assertEquals(1, p.definitions.size)
        assertEquals(
                listOf("(x (qual nu int (@ _kappa_insert_x nu)))", "(m (qual nu int (@ _kappa_insert_m nu x)))", "(a (qual nu (array int) (@ _mu_insert_a nu x m)))"),
                p.definitions[0].inputParams.map { it.toSExp() }
        )
        assertEquals(listOf("(res (qual nu (array int) (@ _mu_insert_res nu x)))"), p.definitions[0].outputParams.map { it.toSExp() })
        assertTrue(p.definitions[0].body is LetFun)
        val defs = (p.definitions[0].body as LetFun).defs
        assertEquals(2, defs.size)
        assertEquals(listOf("(x (qual nu int (@ _kappa_f2_x nu x m a)))", "(m (qual nu int (@ _kappa_f2_m nu m a x)))", "(i (qual nu int (@ _kappa_f2_i nu a x m)))", "(a (qual nu (array int) (@ _mu_f2_a nu a x m i)))"),
                defs[0].inputParams.map { it.toSExp() })
        assertEquals(listOf("(res2 (qual nu (array int) (@ _mu_f2_res2 nu x m i a)))"),
                defs[0].outputParams.map { it.toSExp() })
        assertEquals(listOf("(x (qual nu int (@ _kappa_f4_x nu x m a)))", "(m (qual nu int (@ _kappa_f4_m nu m a x)))", "(i (qual nu int (@ _kappa_f4_i nu a x m)))", "(a (qual nu (array int) (@ _mu_f4_a nu a x m i)))"),
                defs[1].inputParams.map { it.toSExp() })
        assertEquals(listOf("(res4 (qual nu (array int) (@ _mu_f4_res4 nu x m i a)))"),
                defs[1].outputParams.map { it.toSExp() })
        assertEquals(8, p.kappaDeclarations.size)
        assertEquals(6, p.muDeclarations.size)
    }

}