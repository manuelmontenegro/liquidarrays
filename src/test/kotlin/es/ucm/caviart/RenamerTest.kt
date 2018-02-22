package es.ucm.caviart

import es.ucm.caviart.ast.*
import org.junit.Test
import kotlin.test.assertEquals

class RenamerTest {
    @Test fun checkFreshVar1() {
        assertEquals("x", freshName("x", setOf()))
    }

    @Test fun checkFreshVar2() {
        assertEquals("x", freshName("x", setOf("y")))
    }

    @Test fun checkFreshVar3() {
        assertEquals("x'", freshName("x", setOf("x")))
    }

    @Test fun checkFreshVar4() {
        assertEquals("x''", freshName("x", setOf("x", "x'")))
    }

    @Test fun checkFreshVar5() {
        assertEquals("x", freshName("x", setOf("x'")))
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
        assertEquals("(let ((z int)) v (let ((x' int)) w x'))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp3() {
        val exp = "(let ((x int)) x (let ((x int)) y x))".toTerm()
        assertEquals("(let ((x' int)) v (let ((x'' int)) w x''))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp4() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd true) (postcd true))) (@ + x z))) (@ f x)))".toTerm()
        assertEquals("(let ((x' int)) v (letfun ((f1 ((x'' int)) ((z int)) (declare (assertion (precd true) (postcd true))) (@ + x'' z))) (@ f x')))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp5() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd (@ = x (the int 0))) (postcd true))) (@ + x z))) (@ f x)))".toTerm()
        assertEquals("(let ((x' int)) v (letfun ((f1 ((x'' int)) ((z int)) (declare (assertion (precd (@ = x'' (the int 0))) (postcd true))) (@ + x'' z))) (@ f x')))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp6() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd (@ = x z)) (postcd true))) (@ + x z))) (@ f x)))".toTerm()
        assertEquals("(let ((x' int)) v (letfun ((f1 ((x'' int)) ((z int)) (declare (assertion (precd (@ = x'' z)) (postcd true))) (@ + x'' z))) (@ f x')))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp7() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd (@ = x z)) (postcd (@ = x'' z)))) (@ + x z))) (@ f x)))".toTerm()
        assertEquals("(let ((x' int)) v (letfun ((f1 ((x'' int)) ((z int)) (declare (assertion (precd (@ = x'' z)) (postcd (@ = x'' z)))) (@ + x'' z))) (@ f x')))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y")).toSExp().toString())
    }

    @Test fun renameExp8() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd (@ = x z)) (postcd (@ = x'' z)))) (let ((x int)) (the int 9) (@ + x z)))) (@ f x)))".toTerm()
        assertEquals("(let ((x' int)) v (letfun ((f1 ((x'' int)) ((z' int)) (declare (assertion (precd (@ = x'' z)) (postcd (@ = x'' z')))) (let ((x''' int)) (the int 9) (@ + x''' z)))) (@ f x')))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y", "z")).toSExp().toString())
    }


    @Test fun renameExp9() {
        val exp = "(let ((x int)) x (letfun ((f1 ((x int)) ((z int)) (declare (assertion (precd (@ = x z)) (postcd (@ = x'' z)))) (let ((x int)) (the int 9) (@ + x z)))) (@ f x)))".toTerm()
        assertEquals("(let ((x' int)) v (letfun ((f1 ((x'' int)) ((z' int)) (declare (assertion (precd (@ = x'' z)) (postcd (@ = x'' z')))) (let ((x''' int)) (the int 9) (@ + x''' z)))) (@ f x')))", exp.applyRenaming(mapOf("y" to "w", "x" to "v"), setOf("x", "y", "z")).toSExp().toString())
    }

    @Test fun renameExp10() {
        val exp = "(case x (((@@ C y z) y)))".toTerm()
        assertEquals("(case t (((@@ C y z) y)))", exp.applyRenaming(mapOf("x" to "t"), setOf("x")).toSExp().toString())
    }

    @Test fun renameExp11() {
        val exp = "(case x (((@@ C x z) (let ((x int)) x x))))".toTerm()
        assertEquals("(case t (((@@ C x' z) (let ((x'' int)) x' x''))))", exp.applyRenaming(mapOf("x" to "t"), setOf("x")).toSExp().toString())
    }

    @Test fun renameDef1() {
        val def = "(f ((x int)) ((y (qual nu int (@ < nu x)))) (declare (assertion (precd true) (postcd true))) (@ + x y))".toDef()
        assertEquals("(f ((x int)) ((y (qual nu int (@ < nu x)))) (declare (assertion (precd true) (postcd true))) (@ + x y))", def.applyRenaming(mapOf(), setOf()).toSExp().toString())
    }

    @Test fun renameDef2() {
        val def = "(f ((x int)) ((y (qual nu int (@ < nu x)))) (declare (assertion (precd true) (postcd true))) (@ + x y))".toDef()
        assertEquals("(f ((x' int)) ((y (qual nu int (@ < nu x')))) (declare (assertion (precd true) (postcd true))) (@ + x' y))", def.applyRenaming(mapOf(), setOf("x")).toSExp().toString())
    }

    @Test fun renameDef3() {
        val def = "(f ((x int)) ((y (qual x' int (@ < x' x)))) (declare (assertion (precd true) (postcd true))) (@ + x y))".toDef()
        assertEquals("(f ((x' int)) ((y (qual x'' int (@ < x'' x')))) (declare (assertion (precd true) (postcd true))) (@ + x' y))", def.applyRenaming(mapOf(), setOf("x")).toSExp().toString())
    }

}