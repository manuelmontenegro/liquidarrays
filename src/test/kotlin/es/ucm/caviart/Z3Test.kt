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
        assertEquals(5, Version.getMinor())
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
}
