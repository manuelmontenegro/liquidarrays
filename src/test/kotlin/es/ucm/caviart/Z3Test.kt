package es.ucm.caviart

import com.microsoft.z3.Context
import com.microsoft.z3.Solver
import com.microsoft.z3.Status
import com.microsoft.z3.Version
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
}
