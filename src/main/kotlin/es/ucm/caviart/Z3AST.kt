package es.ucm.caviart

import com.microsoft.z3.*

/**
 * Functions for converting an AST structure to a Z3's AST
 *
 * Created by manuel on 30/05/17.
 */

val arithmeticOperators = setOf("+", "-", "*", "/")

typealias SymbolMap = MutableMap<String, Symbol>
typealias TypeEnv = MutableMap<String, Sort>
typealias DeclarationMap = MutableMap<String, FuncDecl>



fun Type.toZ3Sort(ctx: Context): Sort = when (this.typeConstructor) {
    "int" -> ctx.mkIntSort()
    "array" -> ctx.mkArraySort(ctx.mkIntSort(), this.arguments[0].toZ3Sort(ctx))
    else -> if (this.arguments.size == 0 && this.typeConstructor.startsWith("\'")) {
        ctx.mkUninterpretedSort(this.typeConstructor.substring(1))
    } else {
        throw UnsupportedTypeException(this)
    }
}

fun Term.toZ3ArithExpr(ctx: Context,
                       symbolMap: SymbolMap,
                       declarationMap: DeclarationMap,
                       typeEnv: TypeEnv): ArithExpr = when(this) {
    is Literal -> when {
        type == Type("int") -> ctx.mkInt(Integer.parseInt(value))
        else -> throw UnsupportedTypeException(type)
    }

    is Variable -> ctx.mkIntConst(symbolMap[name] ?: throw UndefinedVariable(name))

    is FunctionApplication -> {
        val args = arguments.map { it.toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv) }
        when (name) {
            "+" -> ctx.mkAdd(*args.toTypedArray())
            "-" -> ctx.mkSub(*args.toTypedArray())
            "*" -> ctx.mkMul(*args.toTypedArray())
            "/" -> ctx.mkDiv(args[0], args[1])
            else -> throw UnsupportedZ3AST(this)
        }
    }


    else -> throw UnsupportedZ3AST(this)

}

fun Term.toZ3Expr(ctx: Context,
                  symbolMap: SymbolMap,
                  declarationMap: DeclarationMap,
                  typeEnv: TypeEnv): Expr = when (this) {
    is Literal -> when (type) {
        Type("int") -> ctx.mkInt(Integer.parseInt(value))
        else -> throw UnsupportedTypeException(type)
    }

    is Variable -> ctx.mkConst(
            symbolMap[name] ?: throw UndefinedVariable(name),
            typeEnv[name] ?: throw UndefinedVariable(name)
    )

    is FunctionApplication -> {
        if (name in arithmeticOperators) {
            toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
        } else {
            val args = arguments.map { it.toZ3Expr(ctx, symbolMap, declarationMap, typeEnv) }
            ctx.mkApp(
                    declarationMap[name] ?: throw UndefinedFunction(name),
                    *args.toTypedArray()
            )
        }
    }



    else -> throw UnsupportedZ3AST(this)

}

class UnsupportedZ3AST(term: ASTElem) : RuntimeException("Cannot convert to Z3: $term")
class UnsupportedTypeException(t: Type) : RuntimeException("Unsupported type $t")
class UndefinedVariable(name: String) : RuntimeException("Undefined variable $name")
class UndefinedFunction(name: String) : RuntimeException("Undefined function $name")