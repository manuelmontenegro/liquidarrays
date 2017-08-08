package es.ucm.caviart

import com.microsoft.z3.*
import com.microsoft.z3.enumerations.Z3_ast_kind
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests with Z3AST class
 *
 * Created by manuel on 30/05/17.
 */


class Z3ASTTest {
    val ctx = Context()

    @Test fun intTypeToAST() {
        assertTrue(ConstrType("int").toZ3Sort(ctx) is IntSort)
    }

    @Test fun arrayTypeToAST() {
        assertEquals("(Array Int Int)", ConstrType("array", listOf(ConstrType("int"))).toZ3Sort(ctx).sExpr)
    }

    @Test fun varTypeToAST() {
        assertEquals("a", ConstrType("'a").toZ3Sort(ctx).sExpr)
    }

    @Test fun literalToAST() {
        val ast = Literal("5", ConstrType("int")).toZ3Expr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(ast.astKind == Z3_ast_kind.Z3_NUMERAL_AST, "5 must be a Z3 literal")
        assertEquals("5", ast.sExpr)
    }

    @Test fun variableToAST() {
        val ast = Variable("x").toZ3Expr(ctx, mapOf("x" to ctx.mkSymbol("x")), mapOf(), mapOf("x" to ctx.mkIntSort()))
        assertTrue(ast.isExpr, "x must be a Z3 AST expression")
        assertTrue((ast as Expr).isConst, "x must be a Z3 variable")
        assertEquals("x", ast.sExpr)
    }

    @Test fun sumToAST() {
        val sum = FunctionApplication("+", listOf(Literal("1", ConstrType("int")), Variable("x")))
        val ast = sum.toZ3Expr(
                ctx,
                symbolMap = mapOf("x" to ctx.mkSymbol("x")),
                declarationMap = mapOf(),
                typeEnv = mapOf("x" to ctx.mkIntSort())
        )
        assertEquals("(+ 1 x)", ast.sExpr)
    }

    @Test fun functionApplicationToAST() {
        val funApp = FunctionApplication("f", listOf(Literal("5", ConstrType("int")), Variable("x")))
        val arraySort = ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort())
        val ast = funApp.toZ3Expr(
                ctx,
                symbolMap = mapOf("x" to ctx.mkSymbol("x")),
                declarationMap = mapOf("f" to ctx.mkFuncDecl("f", arrayOf(ctx.mkIntSort(), arraySort), ctx.mkIntSort())),
                typeEnv = mapOf("x" to arraySort)
        )
        assertTrue(ast.isExpr, "(f 5 x) must be a Z3 AST expression")
        assertTrue((ast as Expr).isApp, "(f 5 x) must be a Z3 application")
        assertEquals("(f 5 x)", ast.sExpr)
    }

    @Test fun constructorApplicationToAST() {
        val consApp = ConstructorApplication("c", listOf(Literal("1", ConstrType("int")), Literal("false", ConstrType("bool"))))
        val constrDecl = ctx.mkFuncDecl("c", arrayOf(ctx.mkIntSort(), ctx.mkBoolSort()), ctx.mkUninterpretedSort("C"))
        val ast = consApp.toZ3Expr(ctx,
                symbolMap = mapOf(),
                declarationMap = mapOf("c" to constrDecl),
                typeEnv = mapOf())
        assertTrue(ast.isExpr, "(c 1 false) must be a Z3 AST expression")
        assertEquals("(c 1 false)", ast.sExpr)
    }

    @Test fun trueToAST() {
        val ast = True().toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertEquals("true", ast.sExpr)
    }

    @Test fun falseToAST() {
        val ast = False().toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertEquals("false", ast.sExpr)
    }

    @Test fun lessOrEqualThanAST() {
        val ast = PredicateApplication("<=", listOf(Literal("5", ConstrType("int")), Literal("2", ConstrType("int"))))
        val z3 = ast.toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isLE, "(<= 5 2) is a <= expression")
        assertEquals("(<= 5 2)", z3.sExpr)
    }

    @Test fun lessThanAST() {
        val ast = PredicateApplication("<", listOf(Literal("1", ConstrType("int")), Variable("x")))
        val z3 = ast.toZ3BoolExpr(ctx, mapOf("x" to ctx.mkSymbol("x")), mapOf(), mapOf("x" to ctx.mkIntSort()))
        assertTrue(z3.isLT, "(< 1 x) is a < expression")
        assertEquals("(< 1 x)", z3.sExpr)
    }

    @Test fun greaterThanAST() {
        val ast = PredicateApplication(">", listOf(Variable("x"), Literal("2", ConstrType("int"))))
        val z3 = ast.toZ3BoolExpr(ctx, mapOf("x" to ctx.mkSymbol("x")), mapOf(), mapOf("x" to ctx.mkIntSort()))
        assertTrue(z3.isGT, "(> x 2) is a > expression")
        assertEquals("(> x 2)", z3.sExpr)
    }

    @Test fun greaterOrEqualThanAST() {
        val ast = PredicateApplication(">=", listOf(Literal("5", ConstrType("int")), Literal("2", ConstrType("int"))))
        val z3 = ast.toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isGE, "(>= 5 2) is a >= expression")
        assertEquals("(>= 5 2)", z3.sExpr)
    }

    @Test fun equalToAST() {
        val ast = PredicateApplication("=", listOf(Literal("1", ConstrType("int")), Literal("2", ConstrType("int"))))
        val z3 = ast.toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isEq, "(= 1 2) is a = expression")
        assertEquals("(= 1 2)", z3.sExpr)
    }

    @Test fun notEqualToAST() {
        val ast = PredicateApplication("!=", listOf(Literal("1", ConstrType("int")), Literal("2", ConstrType("int"))))
        val z3 = ast.toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isNot, "(!= 1 2) is a not expression")
        assertEquals("(not (= 1 2))", z3.sExpr)
    }

    @Test fun andAST() {
        val ast1 = PredicateApplication("<", listOf(Literal("1", ConstrType("int")), Literal("2", ConstrType("int"))))
        val ast2 = PredicateApplication("<", listOf(Literal("3", ConstrType("int")), Literal("5", ConstrType("int"))))
        val ast3 = PredicateApplication("<", listOf(Literal("5", ConstrType("int")), Literal("7", ConstrType("int"))))
        val z3 = And(ast1, ast2, ast3).toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isAnd, "(and (< 1 2) (< 3 5) (< 5 7)) is an and expression")
        assertEquals("(and (< 1 2) (< 3 5) (< 5 7))", z3.sExpr)
    }

    @Test fun orAST() {
        val ast1 = PredicateApplication("<", listOf(Literal("1", ConstrType("int")), Literal("2", ConstrType("int"))))
        val ast2 = PredicateApplication("<", listOf(Literal("3", ConstrType("int")), Literal("5", ConstrType("int"))))
        val ast3 = PredicateApplication("<", listOf(Literal("5", ConstrType("int")), Literal("7", ConstrType("int"))))
        val z3 = Or(ast1, ast2, ast3).toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isOr, "(or (< 1 2) (< 3 5) (< 5 7)) is an or expression")
        assertEquals("(or (< 1 2) (< 3 5) (< 5 7))", z3.sExpr)
    }

    @Test fun notAST() {
        val ast1 = PredicateApplication("<", listOf(Literal("1", ConstrType("int")), Literal("2", ConstrType("int"))))
        val z3 = Not(ast1).toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isNot, "(not (< 1 2)) is a not expression")
        assertEquals("(not (< 1 2))", z3.sExpr)
    }

    @Test fun impliesAST() {
        val ast1 = PredicateApplication("<", listOf(Literal("1", ConstrType("int")), Literal("2", ConstrType("int"))))
        val ast2 = PredicateApplication("<", listOf(Literal("3", ConstrType("int")), Literal("5", ConstrType("int"))))
        val ast3 = PredicateApplication("<", listOf(Literal("5", ConstrType("int")), Literal("7", ConstrType("int"))))
        val z3 = Implication(listOf(ast1, ast2, ast3)).toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isImplies, "(-> (< 1 2) (< 3 5) (< 5 7)) is a not expression")
        assertEquals("(=> (< 1 2) (=> (< 3 5) (< 5 7)))", z3.sExpr)
    }

    @Test fun iffAST() {
        val ast1 = PredicateApplication("<", listOf(Literal("1", ConstrType("int")), Literal("2", ConstrType("int"))))
        val ast2 = PredicateApplication("<", listOf(Literal("3", ConstrType("int")), Literal("5", ConstrType("int"))))
        val ast3 = PredicateApplication("<", listOf(Literal("5", ConstrType("int")), Literal("7", ConstrType("int"))))
        val z3 = Iff(listOf(ast1, ast2, ast3)).toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isIff, "(<-> (< 1 2) (< 3 5) (< 5 7)) is a not expression")
        assertEquals("(= (= (< 1 2) (< 3 5)) (< 5 7))", z3.sExpr)
    }

    @Test fun forAllAST() {
        val ast = ForAll("x", ConstrType("int"), PredicateApplication(">=", listOf(Variable("x"), Literal("0", ConstrType("int")))))
        val z3 = ast.toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isQuantifier, "(forall ((x int)) (>= x 0)) is a quantified expression")
        assertEquals("(forall ((x Int)) (>= x 0))", z3.sExpr)
    }

    @Test fun existsAST() {
        val ast = Exists("x", ConstrType("int"), PredicateApplication(">=", listOf(Variable("x"), Literal("0", ConstrType("int")))))
        val z3 = ast.toZ3BoolExpr(ctx, mapOf(), mapOf(), mapOf())
        assertTrue(z3.isQuantifier, "(exists ((x int)) (>= x 0)) is a quantified expression")
        assertEquals("(exists ((x Int)) (>= x 0))", z3.sExpr)
    }

    @Test fun orderingProperty() {
        val symbolMap = mapOf("n" to ctx.mkSymbol("n"), "a" to ctx.mkSymbol("a"))
        val typeEnvironment = mapOf("n" to ctx.mkIntSort(), "a" to ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort()))

        val ord1 = ForAll(listOf(TypedVar("j1", ConstrType("int")), TypedVar("j2", ConstrType("int"))), Implication(
                And(
                        PredicateApplication("<=", listOf(Literal("0", ConstrType("int")), Variable("j1"))),
                        PredicateApplication("<=", listOf(Variable("j1"), Variable("j2"))),
                        PredicateApplication("<", listOf(Variable("j2"), Variable("n")))
                ),
                PredicateApplication("<=", listOf(
                        FunctionApplication("get-array", listOf(Variable("a"), Variable("j1"))),
                        FunctionApplication("get-array", listOf(Variable("a"), Variable("j2")))
                ))
        )).toZ3BoolExpr(ctx, symbolMap, mapOf(), typeEnvironment)

        val ord2 = PredicateApplication("<=", listOf(
                FunctionApplication("get-array", listOf(Variable("a"), FunctionApplication("-", listOf(Variable("n"), Literal("1", ConstrType("int")))))),
                FunctionApplication("get-array", listOf(Variable("a"), Variable("n")))
        )).toZ3BoolExpr(ctx, symbolMap, mapOf(), typeEnvironment)

        val ord3 = Not(ForAll(listOf(TypedVar("j3", ConstrType("int")), TypedVar("j4", ConstrType("int"))), Implication(
                And(
                        PredicateApplication("<=", listOf(Literal("0", ConstrType("int")), Variable("j3"))),
                        PredicateApplication("<=", listOf(Variable("j3"), Variable("j4"))),
                        PredicateApplication("<=", listOf(Variable("j4"), Variable("n")))
                ),
                PredicateApplication("<=", listOf(
                        FunctionApplication("get-array", listOf(Variable("a"), Variable("j3"))),
                        FunctionApplication("get-array", listOf(Variable("a"), Variable("j4")))
                ))
        ))).toZ3BoolExpr(ctx, symbolMap, mapOf(), typeEnvironment)

        println(ord1.sExpr)
        println(ord2.sExpr)
        println(ord3.sExpr)

        val s = ctx.mkSolver()
        s.add(ord1)
        s.add(ord2)
        s.add(ord3)
        assertEquals(Status.UNSATISFIABLE, s.check())
    }


    /*
    @Test fun mkConstructors() {
        val cons1 = ctx.mkConstructor("ni l", "is-nil", null, null, null)
        val cons2 = ctx.mkConstructor("cons", "is-cons", arrayOf("car", "cdr"), kotlin.arrayOfNulls<Sort>(2), intArrayOf(0, 0))
        val dataType = ctx.mkDatatypeSort("List", arrayOf(cons1, cons2))
        println(dataType.sExpr)

        val nil = cons1.ConstructorDecl()
        val cons = cons2.ConstructorDecl()

        val l1 = ctx.mkApp(cons, ctx.mkApp(nil), ctx.mkApp(nil))
        val l2 = ctx.mkApp(cons, ctx.mkApp(cons, ctx.mkApp(nil), ctx.mkApp(nil)), ctx.mkApp(nil))

        val s = ctx.mkSolver()
        val mkNot = ctx.mkNot(ctx.mkEq(l1, l2))
        s.add(mkNot)

        println(mkNot.sExpr)
        println(s.check())

    }
    */


}
