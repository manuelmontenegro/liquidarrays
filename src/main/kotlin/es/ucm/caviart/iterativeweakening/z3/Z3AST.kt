/*
    Copyright (c) 2018 Manuel Montenegro

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
*/
package es.ucm.caviart.iterativeweakening.z3

/**
 * This file defines the translation of CLIR assertions into Z3 Assertions.
 */

import com.microsoft.z3.*
import es.ucm.caviart.ast.*
import es.ucm.caviart.utils.LiquidException

/**
 * Functions for converting an AST structure to a Z3's AST
 *
 * Created by manuel on 30/05/17.
 */

private val arithmeticOperators = setOf("+", "-", "*", "/")


        /**
         * Each variable is represented by a symbol in Z3. We maintain a map
         * of such symbols
         */
typealias SymbolMap = Map<String, Symbol>

        /**
         * A mapping from variables to Z3 sorts
         */
typealias TypeEnv = Map<String, Sort>

        /**
         * A mapping from function symbols to Z3 function declarations
         */
typealias DeclarationMap = Map<String, FuncDecl<out Sort>>


/**
 * It translates a CLIR type into a Z3 sort
 *
 * @param ctx Z3 context under which the sort will be created
 * @return A Z3 representation of the `this` type.
 */
fun Type.toZ3Sort(ctx: Context): Sort = when (this) {
    is ConstrType -> when (this.typeConstructor) {
        "int" -> ctx.mkIntSort()
        "bool" -> ctx.mkBoolSort()
        "array" -> ctx.mkArraySort(ctx.mkIntSort(), this.arguments[0].toZ3Sort(ctx))
        else -> throw UnsupportedTypeException(this)
    }
    is QualType -> this.HMType.toZ3Sort(ctx)
    else -> throw UnsupportedTypeException(this)
}

/**
 * It translates an expression of numeric type into an Arithmetic expression
 */
fun Term.toZ3ArithExpr(ctx: Context,
                       symbolMap: SymbolMap,
                       declarationMap: DeclarationMap,
                       typeEnv: TypeEnv): ArithExpr<out ArithSort> = when (this) {
    is Literal -> when {
        type is ConstrType && type.typeConstructor == "int" -> ctx.mkInt(Integer.parseInt(value))
        else -> throw UnsupportedTypeException(type)
    }

    is Variable -> ctx.mkIntConst(symbolMap[name] ?: throw UndefinedVariable(name))

    is FunctionApplication -> {
        when (name) {
            "+" -> {
                val args = arguments.map { it.toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv) }
                ctx.mkAdd(*args.toTypedArray())
            }
            "-" -> {
                val args = arguments.map { it.toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv) }
                ctx.mkSub(*args.toTypedArray())
            }
            "*" -> {
                val args = arguments.map { it.toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv) }
                ctx.mkMul(*args.toTypedArray())
            }
            "/" -> {
                val args = arguments.map { it.toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv) }
                ctx.mkDiv(args[0], args[1])
            }
            "get-array" -> ctx.mkSelect(
                    arguments[0].toZ3ArrayExpr(ctx, symbolMap, declarationMap, typeEnv),
                    arguments[1].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv) as ArithExpr<IntSort>
            ) as ArithExpr<out ArithSort>
            else -> {
                val args = arguments.map {
                    it.toZ3Expr(ctx, symbolMap, declarationMap, typeEnv)
                }
                ctx.mkApp(declarationMap[name], *args.toTypedArray()) as ArithExpr
            }
        }
    }

    else -> throw UnsupportedZ3ArithExpr(this)
}


/**
 * It translates an expression of array type into a Z3 ArrayExpr
 */
fun Term.toZ3ArrayExpr(ctx: Context,
                       symbolMap: SymbolMap,
                       declarationMap: DeclarationMap,
                       typeEnv: TypeEnv): ArrayExpr<IntSort, out Sort> = when (this) {
    is Variable -> {
        val varSort = typeEnv[name] ?: throw UndefinedVariable(name)
        when (varSort) {
            is ArraySort<*, *> ->
                ctx.mkArrayConst(symbolMap[name], ctx.mkIntSort(), varSort.range)
            else -> throw Z3TypeMismatch(varSort)
        }
    }
    is FunctionApplication -> {
        when (name) {
            "set-array" -> ctx.mkStore(
                    arguments[0].toZ3ArrayExpr(ctx, symbolMap, declarationMap, typeEnv) as ArrayExpr<IntSort, Sort>,
                    arguments[1].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv) as ArithExpr<IntSort>,
                    arguments[2].toZ3Expr(ctx, symbolMap, declarationMap, typeEnv) as Expr<Sort>
            )
            else -> throw UnsupportedZ3AST(this)
        }
    }
    else -> throw UnsupportedZ3ArrayExpr(this)
}


/**
 * It translates an expression of array type into a Z3 Expr.
 */
fun Term.toZ3Expr(ctx: Context,
                  symbolMap: SymbolMap,
                  declarationMap: DeclarationMap,
                  typeEnv: TypeEnv): Expr<out Sort> = when (this) {

    is Literal -> when (type) {
        ConstrType("int") -> toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
        ConstrType("bool") -> when (value) {
            "true" -> ctx.mkTrue()
            "false" -> ctx.mkFalse()
            else -> throw UnsupportedZ3AST(this)
        }
        else -> throw UnsupportedZ3AST(this)
    }

    is Variable -> ctx.mkConst(
            symbolMap[name] ?: throw UndefinedVariable(name),
            typeEnv[name] ?: throw UndefinedVariable(name)
    )

    is FunctionApplication -> {
        if (name in arithmeticOperators || name == "get-array") {
            toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
        } else if (name == "set-array") {
            toZ3ArrayExpr(ctx, symbolMap, declarationMap, typeEnv)
        } else {
            val args = arguments.map { it.toZ3Expr(ctx, symbolMap, declarationMap, typeEnv) }
            ctx.mkApp(
                    declarationMap[name] ?: throw UndefinedFunction(name),
                    *args.toTypedArray()
            )
        }
    }

    is Tuple -> throw UnsupportedZ3AST(this)

    is ConstructorApplication -> {
        val args = arguments.map { it.toZ3Expr(ctx, symbolMap, declarationMap, typeEnv) }
        ctx.mkApp(
                declarationMap[name] ?: throw UndefinedFunction(name),
                *args.toTypedArray()
        )
    }

    else -> throw UnsupportedZ3AST(this)
}


/**
 * It builds a quantification formula from an assertion and a list
 * of bound variables.
 */
private fun buildQuantifier(ctx: Context,
                            symbolMap: SymbolMap,
                            declarationMap: DeclarationMap,
                            typeEnv: TypeEnv,
                            boundVars: List<HMTypedVar>,
                            body: Assertion,
                            universal: Boolean): Quantifier {

    val sorts = boundVars.map { it.HMType.toZ3Sort(ctx) }
    val varNames = boundVars.map { it.varName }
    val symbols = varNames.map { ctx.mkSymbol(it) }

    val newSymbols = (varNames zip symbols).toMap()
    val newEnvBindings = (varNames zip sorts).toMap()

    val assrtZ3 = body.toZ3BoolExpr(ctx, symbolMap + newSymbols, declarationMap, typeEnv + newEnvBindings)


    return ctx.mkQuantifier(universal, (symbols zip sorts).map { (sym, sort) -> ctx.mkConst(sym, sort) }.toTypedArray(), assrtZ3, 0, null, null, null, null)
}

/**
 * It translates a CLIR assertion into a Z3 boolean expression
 *
 * @param ctx Context in which the Z3 expression will be created
 * @param symbolMap Map that associates each variable in scope with its Z3 symbol
 * @param declarationMap Map that associates each function symbol with its Z3 declaration
 * @param typeEnv Map that associates each variable in scope with its Z3 sort
 */
fun Assertion.toZ3BoolExpr(ctx: Context,
                           symbolMap: SymbolMap,
                           declarationMap: DeclarationMap,
                           typeEnv: TypeEnv): BoolExpr = when (this) {
    is True ->
        ctx.mkTrue()

    is False ->
        ctx.mkFalse()

    is BooleanVariable ->
        ctx.mkBoolConst(name)

    is PredicateApplication ->
        when (name) {
            "<" ->
                ctx.mkLt(
                        arguments[0].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv),
                        arguments[1].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
                )
            "<=" ->
                ctx.mkLe(
                        arguments[0].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv),
                        arguments[1].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
                )
            ">" ->
                ctx.mkGt(
                        arguments[0].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv),
                        arguments[1].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
                )
            ">=" ->
                ctx.mkGe(
                        arguments[0].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv),
                        arguments[1].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
                )
            "=" ->
                ctx.mkEq(
                        arguments[0].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv),
                        arguments[1].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
                )
            "=[]" ->
                ctx.mkEq(
                        arguments[0].toZ3ArrayExpr(ctx, symbolMap, declarationMap, typeEnv),
                        arguments[1].toZ3ArrayExpr(ctx, symbolMap, declarationMap, typeEnv)
                )
            "!=" ->
                ctx.mkNot(ctx.mkEq(
                        arguments[0].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv),
                        arguments[1].toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
                ))
            else -> {
                ctx.mkApp(
                        declarationMap[name] ?: throw UndefinedFunction(name),
                        *arguments.map {
                            it.toZ3Expr(ctx, symbolMap, declarationMap, typeEnv)
                        }.toTypedArray()
                ) as BoolExpr
            }

        }

    is Not ->
        ctx.mkNot(assertion.toZ3BoolExpr(ctx, symbolMap, declarationMap, typeEnv))

    is And ->
        ctx.mkAnd(*conjuncts.map { it.toZ3BoolExpr(ctx, symbolMap, declarationMap, typeEnv) }.toTypedArray())

    is Or ->
        ctx.mkOr(*disjuncts.map { it.toZ3BoolExpr(ctx, symbolMap, declarationMap, typeEnv) }.toTypedArray())

    is Implication ->
        operands.map { it.toZ3BoolExpr(ctx, symbolMap, declarationMap, typeEnv) }
                .reduceRight { assrt, acc -> ctx.mkImplies(assrt, acc) }

    is Iff ->
        operands.map { it.toZ3BoolExpr(ctx, symbolMap, declarationMap, typeEnv) }
                .reduce { ac, assrt -> ctx.mkIff(ac, assrt) }

    is ForAll -> buildQuantifier(ctx, symbolMap, declarationMap, typeEnv, boundVars, assertion, true)

    is Exists -> buildQuantifier(ctx, symbolMap, declarationMap, typeEnv, boundVars, assertion, false)


    else -> throw UnsupportedZ3AST(this)
}

/*
fun getTypeBindingExpression(ctx:Context, typeEnv: TypeEnv, declarationMap: DeclarationMap, bindingExpression: BindingExpression): Sort = when(bindingExpression) {
    is Literal -> bindingExpression.type.toZ3Sort(ctx)
    is Variable -> typeEnv[bindingExpression.name] ?: throw UndefinedVariable(bindingExpression.name)
    is FunctionApplication -> {
        val funcDecl = declarationMap[bindingExpression.name] ?: throw UndefinedFunction(bindingExpression.name)
        funcDecl.range
    }
    is ConstructorApplication -> {
        val constrDecl = declarationMap[bindingExpression.name] ?: throw UndefinedFunction(bindingExpression.name)
        constrDecl.range
    }
    else -> throw UnsupportedZ3AST(bindingExpression)
}
*/

fun UninterpretedFunctionType.toFuncDecl(name: String, ctx: Context): FuncDecl<out Sort> =
        ctx.mkFuncDecl(name, argumentTypes.map { it.toZ3Sort(ctx) }.toTypedArray(), resultType.toZ3Sort(ctx))


class UnsupportedZ3AST(term: ASTElem) : LiquidException("Cannot convert to Z3: $term")
class UnsupportedZ3ArithExpr(term: ASTElem) : LiquidException("Cannot use as arithmetic expression: $term")
class UnsupportedZ3ArrayExpr(term: ASTElem) : LiquidException("Cannot use as array expression: $term")
class UnsupportedTypeException(t: Type) : LiquidException("Unsupported type $t")
class UndefinedVariable(name: String) : LiquidException("Undefined variable $name")
class UndefinedFunction(name: String) : LiquidException("Undefined function $name")
class Z3TypeMismatch(sort: Sort) : LiquidException("HMType mismatch ${sort.sExpr}")
