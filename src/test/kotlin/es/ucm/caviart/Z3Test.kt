package es.ucm.caviart

import com.microsoft.z3.*
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Class for testing that Z3 works
 *
 * Created by manuel on 30/05/17.
 */


class Z3Test {
    @Test fun checkVersion() {
        assertEquals(4, Version.getMajor())
        assertEquals(6, Version.getMinor())
    }

    @Test fun assertTrue() {
        val c = Context()
        val s = c.mkSolver()
        s.add(c.mkTrue())
        assertEquals(Status.SATISFIABLE, s.check())
    }

    @Test fun assertFalse() {
        val c = Context()
        val s = c.mkSolver()
        s.add(c.mkFalse())
        assertEquals(Status.UNSATISFIABLE, s.check())
    }

    @Test fun severalContexts() {
        val cs = Array(10) { Context() }
        val solvers = mutableListOf<Solver>()
        for ((i, c) in cs.withIndex()) {
            val s = c.mkSolver()
            when {
                i % 2 == 0 -> s.add(c.mkTrue())
                else       -> s.add(c.mkFalse())
            }
            solvers.add(s)
        }

        for ((i, s) in solvers.withIndex()) {
            when {
                i % 2 == 0 -> assertEquals(Status.SATISFIABLE, s.check())
                else       -> assertEquals(Status.UNSATISFIABLE, s.check())
            }
        }

    }

    @Test fun differentSymbolsTest() {
        val ctx = Context()
        val sym1 = ctx.mkSymbol("i")
        val sym2 = ctx.mkSymbol("i")
        val s = ctx.mkSolver()
        val formula = ctx.mkNot(ctx.mkEq(ctx.mkConst(sym1, ctx.mkIntSort()), ctx.mkConst(sym2, ctx.mkIntSort())))
        s.add(formula)
        assertEquals(Status.UNSATISFIABLE, s.check())
    }

    @Test fun symbolsAndQuantifiers() {
        val ctx = Context()
        val sym1 = ctx.mkSymbol("i")
        // (= i 0)
        val formula1 = ctx.mkEq(ctx.mkConst(sym1, ctx.mkIntSort()), ctx.mkInt(0))
        // (forall ((i Int)) (=> (= i 1) false))
        val formula2 = ctx.mkForall(arrayOf(ctx.mkConst(sym1, ctx.mkIntSort())),
                ctx.mkImplies(ctx.mkEq(ctx.mkConst(sym1, ctx.mkIntSort()), ctx.mkInt(1)), ctx.mkFalse()), 0, null, null, null, null)
        val s = ctx.mkSolver()
        s.add(formula1)
        s.add(formula2)
        assertEquals(Status.UNSATISFIABLE, s.check())
    }

    @Test fun symbolsAndQuantifiers2() {
        val ctx = Context()
        val sym1 = ctx.mkSymbol("i")
        val sym2 = ctx.mkSymbol("j")
        // (= i 0)
        val formula1 = ctx.mkEq(ctx.mkConst(sym1, ctx.mkIntSort()), ctx.mkInt(0))
        // (forall ((j Int)) (=> (= i 1) false))
        val formula2 = ctx.mkForall(arrayOf(ctx.mkConst(sym2, ctx.mkIntSort())),
                ctx.mkImplies(ctx.mkEq(ctx.mkConst(sym1, ctx.mkIntSort()), ctx.mkInt(1)), ctx.mkFalse()), 0, null, null, null, null)
        val s = ctx.mkSolver()
        s.add(formula1)
        s.add(formula2)
        assertEquals(Status.SATISFIABLE, s.check())
    }

    @Test fun severalFunDeclsWithSameName() {
        val ctx = Context()
        val decl1 = ctx.mkFuncDecl("len", ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort()), ctx.mkIntSort())
        val decl2 = ctx.mkFuncDecl("len", ctx.mkArraySort(ctx.mkIntSort(), ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort())), ctx.mkIntSort())
        val decl3 = ctx.mkFuncDecl("len", ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort()), ctx.mkIntSort())
        val s = ctx.mkSolver()

        // F1: len_1(a : array int) != 0
        val formula1 = ctx.mkNot(ctx.mkEq(ctx.mkApp(decl1, ctx.mkConst(ctx.mkSymbol("a"), ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort()))), ctx.mkInt(0)))
        // F2: len_2(a : array (array int)) != 0
        val formula2 = ctx.mkNot(ctx.mkEq(ctx.mkApp(decl2, ctx.mkConst(ctx.mkSymbol("b"), ctx.mkArraySort(ctx.mkIntSort(), ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort())))), ctx.mkInt(0)))
        // F3: len_3(a : array int) != 0
        val formula3 = ctx.mkNot(ctx.mkEq(ctx.mkApp(decl3, ctx.mkConst(ctx.mkSymbol("c"), ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort()))), ctx.mkInt(0)))
        // F4: forall x: array int. len_1(x) = 0
        val formula4 = ctx.mkForall(arrayOf(ctx.mkArrayConst(ctx.mkSymbol("x"), ctx.mkIntSort(), ctx.mkIntSort())),
                ctx.mkEq(ctx.mkApp(decl1, ctx.mkArrayConst("x", ctx.mkIntSort(), ctx.mkIntSort())), ctx.mkInt(0)), 0, null, null, null, null)


        s.push()
        s.add(formula1)
        s.add(formula4)
        val status1 = s.check()
        s.pop()
        assertEquals(Status.UNSATISFIABLE, status1, "F1 and F4 must be unsatisfiable")

        s.push()
        s.add(formula2)
        s.add(formula4)
        val status2 = s.check()
        s.pop()
        assertEquals(Status.SATISFIABLE, status2, "F2 and F4 must be satisfiable")

        s.push()
        s.add(formula3)
        s.add(formula4)
        val status3 = s.check()
        s.pop()
        assertEquals(Status.UNSATISFIABLE, status3, "F3 and F4 must be unsatisfiable")

    }

    @Test fun booleans() {
        val ctx = Context()

        val symb = ctx.mkBoolConst("b")
        val comparison = ctx.mkLe(ctx.mkInt(0), ctx.mkInt(1))

        val s = ctx.mkSolver()
        s.add(ctx.mkEq(symb, comparison))
        s.add(ctx.mkNot(ctx.mkEq(symb, ctx.mkTrue())))
        val status = s.check()
        assertEquals(Status.UNSATISFIABLE, status)
    }

}
