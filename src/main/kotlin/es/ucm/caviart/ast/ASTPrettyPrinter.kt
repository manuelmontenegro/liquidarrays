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
package es.ucm.caviart.ast

/*
 * This module exports the `toSExp` method, which allows one to translate an AST
 * element into its corresponding CLIR representation.
 */

private const val UNDEFINED = "undefined!"


/**
 * It obtains the CLIR representation of the given type.
 */
fun Type.toSExp(): String = when (this) {

    is ConstrType -> {
        when {
            arguments.isEmpty() -> typeConstructor
            else -> "($typeConstructor${arguments.joinToString("") { " " + it.toSExp() }})"
        }
    }

    is VarType -> variable

    is QualType ->
            "(qual $nu ${HMType.toSExp()} ${qualifier.toSExp()})"

    else -> UNDEFINED
}

/**
 * It returns the CLIR representation of the given HM-typed variable
 * as a pair `(var type)`
 */
fun HMTypedVar.toSExp(): String  = "($varName ${HMType.toSExp()})"

/**
 * It returns the CLIR representation of the given typed variable
 * as a pair `(var type)`. The `type` might contain qualifiers.
 */
fun TypedVar.toSExp(): String  = "($varName ${type.toSExp()})"

/**
 * It returns the CLIR representation of the given function definition.
 */
fun FunctionDefinition.toSExp(): String =
        "($name (${inputParams.joinToString(" ") { it.toSExp() }}) (${outputParams.joinToString(" ") { it.toSExp() }}) " +
                "(declare (assertion (precd ${precondition.toSExp()}) (postcd ${postcondition.toSExp()}))) ${body.toSExp()})"


/**
 * It returns the CLIR representation of the given pattern
 */
fun Pattern.toSExp(): String = when(this) {
    is LiteralPattern -> literal
    is ConstructorPattern -> "(@@ $constructorName${constructorArgs.joinToString(" ")})"
    else -> UNDEFINED
}

/**
 * It returns the CLIR representation of the given case branch
 */
fun CaseBranch.toSExp(): String = "(${pattern.toSExp()} ${expression.toSExp()})"

/**
 * It returns the CLIR representation of the given assertion
 */
fun Assertion.toSExp(): String = when(this) {
    is True -> "true"
    is False -> "false"
    is PredicateApplication -> "(@ $name${arguments.joinToString("") { " " + it.toSExp() }})"
    is BooleanVariable -> name
    is Not -> "(not ${assertion.toSExp()})"
    is And -> "(and${conjuncts.joinToString("") { " " + it.toSExp() }})"
    is Or -> "(or${disjuncts.joinToString("") { " " + it.toSExp() }})"
    is Implication -> "(->${operands.joinToString("") { " " + it.toSExp() }})"
    is Iff -> "(<->${operands.joinToString("") { " " + it.toSExp() }})"
    is ForAll -> "(forall (${boundVars.joinToString(" ") { it.toSExp() }}) ${assertion.toSExp()})"
    is Exists -> "(exists (${boundVars.joinToString(" ") { it.toSExp() }}) ${assertion.toSExp()})"
    else -> UNDEFINED
}


/**
 * It returns the CLIR representation of the given expression
 */
fun Term.toSExp(): Any = when (this) {
    is Literal ->
        "(the ${type.toSExp()} $value)"

    is Variable ->
        name

    is FunctionApplication ->
        "(@ $name${arguments.joinToString("") { " " + it.toSExp() }})"

    is ConstructorApplication ->
        "(@@ $name${arguments.joinToString("") { " " + it.toSExp() }})"

    is Tuple ->
        "(tuple${arguments.joinToString("") { " " + it.toSExp() }})"

    is Let ->
            "(let (${bindings.joinToString(" ") {it.toSExp()}}) ${bindingExpression.toSExp()} ${mainExpression.toSExp()})"

    is LetFun ->
            "(letfun (${defs.joinToString(" ") {it.toSExp()}}) ${mainExpression.toSExp()})"

    is Case ->
            "(case ${discriminant.toSExp()} (${branches.joinToString(" ") { it.toSExp() }})${defaultBranch?.toSExp() ?: ""})"

    else -> UNDEFINED
}

