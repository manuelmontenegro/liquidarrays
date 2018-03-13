package es.ucm.caviart

import es.ucm.caviart.ast.*
import org.junit.Test
import kotlin.test.assertEquals

class RenamerTest {
    @Test fun checkFreshVar1() {
        assertEquals("x_1", freshName("x", setOf()))
    }

    @Test fun checkFreshVar2() {
        assertEquals("x_1", freshName("x", setOf("y")))
    }

    @Test fun checkFreshVar3() {
        assertEquals("x_1", freshName("x", setOf("x")))
    }

    @Test fun checkFreshVar4() {
        assertEquals("x_2", freshName("x", setOf("x", "x_1")))
    }

    @Test fun checkFreshVar5() {
        assertEquals("x_2", freshName("x", setOf("x_1")))
    }

    @Test fun renameAtomic1() {
        val exp = "(the int 0)".toTerm()
        assertEquals("(the int 0)", exp.applyRenaming(mapOf(), setOf()).toSExp().toString())
    }

    @Test fun renameAtomic2() {
        val exp = "x".toTerm()
        assertEquals("x", exp.applyRenaming(mapOf(), setOf()).toSExp().toString())
    }

    @Test fun renameAtomic3() {
        val exp = "x".toTerm()
        assertEquals("y", exp.applyRenaming(mapOf("x" to "y"), setOf()).toSExp().toString())
    }


    @Test fun renameBinding1() {
        val exp = "(@ f x x)".toTerm()
        assertEquals("(@ f y y)", exp.applyRenaming(mapOf("x" to "y", "f" to "f2"), setOf("f", "x")).toSExp().toString())
    }

    @Test fun renameBinding2() {
        val exp = "(@ f x v)".toTerm()
        assertEquals("(@ f y w)", exp.applyRenaming(mapOf("x" to "y", "v" to "w", "f" to "f2"), setOf("f", "v", "x")).toSExp().toString())
    }

    @Test fun renameBinding3() {
        val exp = "(@ f x x)".toTerm()
        assertEquals("(@ f x x)", exp.applyRenaming(mapOf("v" to "w", "f" to "f2"), setOf("f", "v")).toSExp().toString())
    }

    @Test fun renameBinding4() {
        val exp = "(@@ f x x)".toTerm()
        assertEquals("(@@ f y y)", exp.applyRenaming(mapOf("x" to "y", "f" to "f2"), setOf("f", "x")).toSExp().toString())
    }

    @Test fun renameBinding5() {
        val exp = "(@@ f x v)".toTerm()
        assertEquals("(@@ f y w)", exp.applyRenaming(mapOf("x" to "y", "v" to "w", "f" to "f2"), setOf("f", "x", "v")).toSExp().toString())
    }

    @Test fun renameBinding6() {
        val exp = "(@@ f x x)".toTerm()
        assertEquals("(@@ f x x)", exp.applyRenaming(mapOf("v" to "w", "f" to "f2"), setOf("f", "v")).toSExp().toString())
    }

    @Test fun renameBinding7() {
        val exp = "(tuple x x)".toTerm()
        assertEquals("(tuple y y)", exp.applyRenaming(mapOf("x" to "y", "f" to "f2"), setOf("f", "x")).toSExp().toString())
    }

    @Test fun renameBinding8() {
        val exp = "(tuple x v)".toTerm()
        assertEquals("(tuple y w)", exp.applyRenaming(mapOf("x" to "y", "v" to "w", "f" to "f2"), setOf("f", "v", "x")).toSExp().toString())
    }

    @Test fun renameBinding9() {
        val exp = "(tuple x x)".toTerm()
        assertEquals("(tuple x x)", exp.applyRenaming(mapOf("v" to "w", "f" to "f2"), setOf("f", "v")).toSExp().toString())
    }

    @Test fun renameExp1() {
        val exp = "(let ((x int)) y x)".toTerm()
        assertEquals("(let ((x int)) w x)", exp.applyRenaming(mapOf("y" to "w"), setOf("y")).toSExp().toString())
    }

    @Test fun renameExp2() {
        val exp = "(let ((z int)) x (let ((x int)) y x))".toTerm()
        assertEquals("(let ((z int)) v (let ((x_1 int)) w x_1))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp3() {
        val exp = "(let ((x int)) x (let ((x int)) y x))".toTerm()
        assertEquals("(let ((x_1 int)) v (let ((x_2 int)) w x_2))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp4() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd true) (postcd true))) (@ + x z))) (@ f x)))".toTerm()
        assertEquals("(let ((x_1 int)) v (letfun ((f1 ((x_2 int)) ((z int)) (declare (assertion (precd true) (postcd true))) (@ + x_2 z))) (@ f x_1)))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp5() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd (@ = x (the int 0))) (postcd true))) (@ + x z))) (@ f x)))".toTerm()
        assertEquals("(let ((x_1 int)) v (letfun ((f1 ((x_2 int)) ((z int)) (declare (assertion (precd (@ = x_2 (the int 0))) (postcd true))) (@ + x_2 z))) (@ f x_1)))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp6() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd (@ = x z)) (postcd true))) (@ + x z))) (@ f x)))".toTerm()
        assertEquals("(let ((x_1 int)) v (letfun ((f1 ((x_2 int)) ((z int)) (declare (assertion (precd (@ = x_2 z)) (postcd true))) (@ + x_2 z))) (@ f x_1)))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp7() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd (@ = x z)) (postcd (@ = x_2 z)))) (@ + x z))) (@ f x)))".toTerm()
        assertEquals("(let ((x_1 int)) v (letfun ((f1 ((x_2 int)) ((z int)) (declare (assertion (precd (@ = x_2 z)) (postcd (@ = x_2 z)))) (@ + x_2 z))) (@ f x_1)))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp8() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd (@ = x z)) (postcd (@ = x_2 z)))) (let ((x int)) (the int 9) (@ + x z)))) (@ f x)))".toTerm()
        assertEquals("(let ((x_1 int)) v (letfun ((f1 ((x_2 int)) ((z_1 int)) (declare (assertion (precd (@ = x_2 z)) (postcd (@ = x_2 z_1)))) (let ((x_3 int)) (the int 9) (@ + x_3 z)))) (@ f x_1)))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y", "z")).toSExp().toString())
    }


    @Test fun renameExp9() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd (@ = x z)) (postcd (@ = x_2 z)))) (let ((x int)) (the int 9) (@ + x z)))) (@ f x)))".toTerm()
        assertEquals("(let ((x_1 int)) v (letfun ((f1 ((x_2 int)) ((z_1 int)) (declare (assertion (precd (@ = x_2 z)) (postcd (@ = x_2 z_1)))) (let ((x_3 int)) (the int 9) (@ + x_3 z)))) (@ f x_1)))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y", "z")).toSExp().toString())
    }

    @Test fun renameExp10() {
        val exp = "(case x (((@@ C y z) y)))".toTerm()
        assertEquals("(case t (((@@ C y z) y)))", exp.applyRenaming(mapOf("x" to "t"), setOf("x")).toSExp().toString())
    }

    @Test fun renameExp11() {
        val exp = "(case x (((@@ C x z) (let ((x int)) x x))))".toTerm()
        assertEquals("(case t (((@@ C x_1 z) (let ((x_2 int)) x_1 x_2))))", exp.applyRenaming(mapOf("x" to "t"), setOf("x")).toSExp().toString())
    }

    @Test fun renameDef1() {
        val def = "(f ((x int)) ((y (qual nu int (@ < nu x)))) (declare (assertion (precd true) (postcd true))) (@ + x y))".toDef()
        assertEquals("(f ((x int)) ((y (qual nu int (@ < nu x)))) (declare (assertion (precd true) (postcd true))) (@ + x y))", def.applyRenaming(mapOf(), setOf()).toSExp().toString())
    }

    @Test fun renameDef2() {
        val def = "(f ((x int)) ((y (qual nu int (@ < nu x)))) (declare (assertion (precd true) (postcd true))) (@ + x y))".toDef()
        assertEquals("(f ((x_1 int)) ((y (qual nu int (@ < nu x_1)))) (declare (assertion (precd true) (postcd true))) (@ + x_1 y))", def.applyRenaming(mapOf(), setOf("x")).toSExp().toString())
    }

    @Test fun renameDef3() {
        val def = "(f ((x int)) ((y (qual x_1 int (@ < x_1 x)))) (declare (assertion (precd true) (postcd true))) (@ + x y))".toDef()
        assertEquals("(f ((x_1 int)) ((y (qual x_1_1 int (@ < x_1_1 x_1)))) (declare (assertion (precd true) (postcd true))) (@ + x_1 y))", def.applyRenaming(mapOf(), setOf("x")).toSExp().toString())
    }

}