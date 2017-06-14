package es.ucm.caviart

import com.microsoft.z3.*

/**
 * Functions for converting an AST structure to a Z3's AST
 *
 * Created by manuel on 30/05/17.
 */

private val arithmeticOperators = setOf("+", "-", "*", "/")


typealias SymbolMap = Map<String, Symbol>
typealias TypeEnv = Map<String, Sort>
typealias DeclarationMap = Map<String, FuncDecl>



fun Type.toZ3Sort(ctx: Context): Sort = when (this.typeConstructor) {
    "int" -> ctx.mkIntSort()
    "bool" -> ctx.mkBoolSort()
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
                       typeEnv: TypeEnv): ArithExpr = when (this) {
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
            "get-array" -> ctx.mkSelect(
                    arguments[0].toZ3ArrayExpr(ctx, symbolMap, declarationMap, typeEnv),
                    args[1]
            ) as ArithExpr
            else -> throw UnsupportedZ3AST(this)
        }
    }

    else -> throw UnsupportedZ3ArithExpr(this)
}

fun Term.toZ3ArrayExpr(ctx: Context,
                       symbolMap: SymbolMap,
                       declarationMap: DeclarationMap,
                       typeEnv: TypeEnv): ArrayExpr = when (this) {
    is Variable -> {
        val varSort = typeEnv[name] ?: throw UndefinedVariable(name)
        when (varSort) {
            is ArraySort ->
                ctx.mkArrayConst(symbolMap[name], ctx.mkIntSort(), varSort.range)
            else -> throw Z3TypeMismatch(varSort)
        }
    }
    else -> throw UnsupportedZ3ArrayExpr(this)
}

fun Term.toZ3Expr(ctx: Context,
                  symbolMap: SymbolMap,
                  declarationMap: DeclarationMap,
                  typeEnv: TypeEnv): Expr = when (this) {

    is Literal -> when (type) {
        Type("int") -> toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
        Type("bool") -> when (value) {
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
        if (name in arithmeticOperators) {
            toZ3ArithExpr(ctx, symbolMap, declarationMap, typeEnv)
        } else if (name == "set-array") {
            ctx.mkStore(
                    arguments[0].toZ3ArrayExpr(ctx, symbolMap, declarationMap, typeEnv),
                    arguments[1].toZ3Expr(ctx, symbolMap, declarationMap, typeEnv),
                    arguments[2].toZ3Expr(ctx, symbolMap, declarationMap, typeEnv)
            )
        } else if (name == "get-array") {
            ctx.mkSelect(
                    arguments[0].toZ3ArrayExpr(ctx, symbolMap, declarationMap, typeEnv),
                    arguments[1].toZ3Expr(ctx, symbolMap, declarationMap, typeEnv)
            )
        } else {
            val args = arguments.map { it.toZ3Expr(ctx, symbolMap, declarationMap, typeEnv) }
            ctx.mkApp(
                    declarationMap[name] ?: throw UndefinedFunction(name),
                    *args.toTypedArray()
            )
        }
    }

    is Tuple -> TODO("Tuples will be added later")

    is ConstructorApplication ->
        FunctionApplication(name, arguments).toZ3Expr(ctx, symbolMap, declarationMap, typeEnv)

    else -> throw UnsupportedZ3AST(this)
}


private fun buildQuantifier(ctx: Context,
                            symbolMap: SymbolMap,
                            declarationMap: DeclarationMap,
                            typeEnv: TypeEnv,
                            boundVars: List<TypedVar>,
                            body: Assertion,
                            universal: Boolean): Quantifier {

    val sorts = boundVars.map { it.type.toZ3Sort(ctx) }
    val varNames = boundVars.map { it.varName }
    val symbols = varNames.map { ctx.mkSymbol(it) }

    val newSymbols = (varNames zip symbols).toMap()
    val newEnvBindings = (varNames zip sorts).toMap()

    val assrtZ3 = body.toZ3BoolExpr(ctx, symbolMap + newSymbols, declarationMap, typeEnv + newEnvBindings)


    return ctx.mkQuantifier(universal, (symbols zip sorts).map { (sym, sort) -> ctx.mkConst(sym, sort) }.toTypedArray(), assrtZ3, 0, null, null, null, null)
}

fun Assertion.toZ3BoolExpr(ctx: Context,
                           symbolMap: SymbolMap,
                           declarationMap: DeclarationMap,
                           typeEnv: TypeEnv): BoolExpr = when (this) {
    is True ->
        ctx.mkTrue()

    is False ->
        ctx.mkFalse()

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
                        arguments[0].toZ3Expr(ctx, symbolMap, declarationMap, typeEnv),
                        arguments[1].toZ3Expr(ctx, symbolMap, declarationMap, typeEnv)
                )
            "!=" ->
                ctx.mkNot(ctx.mkEq(
                        arguments[0].toZ3Expr(ctx, symbolMap, declarationMap, typeEnv),
                        arguments[1].toZ3Expr(ctx, symbolMap, declarationMap, typeEnv)
                ))
            else ->
                ctx.mkApp(
                        declarationMap[name] ?: throw UndefinedFunction(name),
                        *arguments.map { it.toZ3Expr(ctx, symbolMap, declarationMap, typeEnv) }.toTypedArray()
                ) as BoolExpr
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

    is LetAssertion -> TODO("Let assertions not supported yet")

    is CaseAssertion -> TODO("Case assertions not supported yet")

    else -> throw UnsupportedZ3AST(this)
}


fun UninterpretedFunctionType.toFuncDecl(name: String, ctx: Context) =
    ctx.mkFuncDecl(name, argumentTypes.map { it.toZ3Sort(ctx) }.toTypedArray(), resultType.toZ3Sort(ctx))


class UnsupportedZ3AST(term: ASTElem) : RuntimeException("Cannot convert to Z3: $term")
class UnsupportedZ3ArithExpr(term: ASTElem) : RuntimeException("Cannot use as arithmetic expression: $term")
class UnsupportedZ3ArrayExpr(term: ASTElem) : RuntimeException("Cannot use as array expression: $term")
class UnsupportedTypeException(t: Type) : RuntimeException("Unsupported type $t")
class UndefinedVariable(name: String) : RuntimeException("Undefined variable $name")
class UndefinedFunction(name: String) : RuntimeException("Undefined function $name")
class Z3TypeMismatch(sort: Sort) : RuntimeException("Type mismatch ${sort.sExpr}")
