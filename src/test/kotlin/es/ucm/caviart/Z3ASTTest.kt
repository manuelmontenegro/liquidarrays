package es.ucm.caviart

import com.microsoft.z3.Context
import com.microsoft.z3.Expr
import com.microsoft.z3.IntSort
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
        assertTrue(Type("int").toZ3Sort(ctx) is IntSort)
    }

    @Test fun arrayTypeToAST() {
        assertEquals("(Array Int Int)", Type("array", listOf(Type("int"))).toZ3Sort(ctx).sExpr)
    }

    @Test fun varTypeToAST() {
        assertEquals("a", Type("'a").toZ3Sort(ctx).sExpr)
    }

    @Test fun literalToAST() {
        val ast = Literal("5", Type("int")).toZ3Expr(ctx, mutableMapOf(), mutableMapOf(), mutableMapOf())
        assertTrue(ast.astKind == Z3_ast_kind.Z3_NUMERAL_AST, "5 must be a Z3 literal")
        assertEquals("5", ast.sExpr)
    }

    @Test fun variableToAST() {
        val ast = Variable("x").toZ3Expr(ctx, mutableMapOf("x" to ctx.mkSymbol("x")), mutableMapOf(), mutableMapOf("x" to ctx.mkIntSort()))
        assertTrue(ast.isExpr, "x must be a Z3 AST expression")
        assertTrue((ast as Expr).isConst, "x must be a Z3 variable")
        assertEquals("x", ast.sExpr)
    }

    @Test fun sumToAST() {
        val sum = FunctionApplication("+", listOf(Literal("1", Type("int")), Variable("x")))
        val ast = sum.toZ3Expr(
                ctx,
                symbolMap = mutableMapOf("x" to ctx.mkSymbol("x")),
                declarationMap = mutableMapOf(),
                typeEnv = mutableMapOf("x" to ctx.mkIntSort())
        )
        assertEquals("(+ 1 x)", ast.sExpr)
    }

    @Test fun functionApplicationToAST() {
        val funApp = FunctionApplication("f", listOf(Literal("5", Type("int")), Variable("x")))
        val arraySort = ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort())
        val ast = funApp.toZ3Expr(
                ctx,
                symbolMap = mutableMapOf("x" to ctx.mkSymbol("x")),
                declarationMap = mutableMapOf("f" to ctx.mkFuncDecl("f", arrayOf(ctx.mkIntSort(), arraySort), ctx.mkIntSort())),
                typeEnv = mutableMapOf("x" to arraySort)
        )
        assertTrue(ast.isExpr, "(f 5 x) must be a Z3 AST expression")
        assertTrue((ast as Expr).isApp, "(f 5 x) must be a Z3 application")
        assertEquals("(f 5 x)", ast.sExpr)
    }


}
