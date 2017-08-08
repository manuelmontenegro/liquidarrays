package es.ucm.caviart

private val UNDEFINED = "undefined!"

fun Type.toSExp(): String = when (this) {

    is ConstrType -> {
        when {
            arguments.size == 0 -> "$typeConstructor"
            else -> "($typeConstructor${arguments.map { " " + it.toSExp() }.joinToString("")})"
        }
    }

    is VarType ->
        "$variable"

    is QualType ->
            "(qual $nu ${HMType.toSExp()} ${qualifier.toSExp()})"

    else -> UNDEFINED
}

fun HMTypedVar.toSExp(): String  = "($varName ${HMType.toSExp()})"
fun TypedVar.toSExp(): String  = "($varName ${type.toSExp()})"

fun FunctionDefinition.toSExp(): String =
        "($name (${inputParams.map { it.toSExp() }.joinToString(" ")}) (${outputParams.map { it.toSExp() }.joinToString(" ")}) " +
                "(declare (assertion (precd ${precondition.toSExp()}) (postcd ${postcondition.toSExp()}))) ${body.toSExp()})"

fun Pattern.toSExp(): String = when(this) {
    is LiteralPattern -> "$literal"
    is ConstructorPattern -> "(@@ $constructorName${constructorArgs.joinToString(" ")})"
    else -> UNDEFINED
}

fun CaseBranch.toSExp(): String = "(${pattern.toSExp()} ${expression.toSExp()})"

fun Assertion.toSExp(): String = when(this) {
    is True -> "true"
    is False -> "false"
    is PredicateApplication -> "(@ $name${arguments.map { " " + it.toSExp() }.joinToString("")})"
    is Not -> "(not ${assertion.toSExp()})"
    is And -> "(and${conjuncts.map { " " + it.toSExp() }.joinToString("")})"
    is Or -> "(or${disjuncts.map { " " + it.toSExp() }.joinToString("")})"
    is Implication -> "(->${operands.map { " " + it.toSExp() }.joinToString("")})"
    is Iff -> "(<->${operands.map { " " + it.toSExp() }.joinToString("")})"
    is ForAll -> "(forall (${boundVars.map { it.toSExp() }.joinToString(" ")}) ${assertion.toSExp()})"
    is Exists -> "(exists (${boundVars.map { it.toSExp() }.joinToString(" ")}) ${assertion.toSExp()})"
    else -> UNDEFINED
}


fun Term.toSExp(): Any = when (this) {
    is Literal ->
        "(the ${type.toSExp()} $value)"

    is Variable ->
        name

    is FunctionApplication ->
        "(@ $name${arguments.map { " " + it.toSExp() }.joinToString("")})"

    is ConstructorApplication ->
        "(@@ $name${arguments.map { " " + it.toSExp() }.joinToString("")})"

    is Tuple ->
        "(tuple${arguments.map { " " + it.toSExp() }.joinToString("")})"

    is Let ->
            "(let (${bindings.map {it.toSExp()}.joinToString(" ")}) ${bindingExpression.toSExp()} ${mainExpression.toSExp()})"

    is LetFun ->
            "(letfun (${defs.map {it.toSExp()}.joinToString(" ")}) ${mainExpression.toSExp()})"

    is Case ->
            "(case ${discriminant.toSExp()} (${branches.map { it.toSExp() }.joinToString(" ")})${if (defaultBranch != null) defaultBranch.toSExp() else ""})"

    else -> UNDEFINED
}

