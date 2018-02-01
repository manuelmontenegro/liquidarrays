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
@file:Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")

package es.ucm.caviart.ast

/*
 * This module exports the `toSExp` method, which allows one to translate an AST
 * element into its corresponding CLIR representation.
 */



private fun t(token: String) = TokenSExp(0, 0, token)
private fun s(vararg children: SExp) = ParenSExp(0, 0, *children)

private val UNDEFINED = t("undefined!")

/**
 * It obtains the CLIR representation of the given type.
 */
fun Type.toSExp(): SExp = when (this) {

    is ConstrType -> {
        when {
            arguments.isEmpty() -> t(typeConstructor)
            else -> s(t(typeConstructor), *arguments.map { it.toSExp() }.toTypedArray())
        }
    }

    is VarType -> t(variable)

    is QualType ->
        s(t("qual"), t(nu), HMType.toSExp(), qualifier.toSExp())

    else -> UNDEFINED
}

/**
 * It returns the CLIR representation of the given HM-typed variable
 * as a pair `(var type)`
 */
fun HMTypedVar.toSExp(): SExp = s(t(varName), HMType.toSExp())

/**
 * It returns the CLIR representation of the given typed variable
 * as a pair `(var type)`. The `type` might contain qualifiers.
 */
fun TypedVar.toSExp(): SExp = s(t(varName), type.toSExp())

/**
 * It returns the CLIR representation of the given function definition.
 */
fun FunctionDefinition.toSExp(): SExp =
        s(t(name), s(*inputParams.map { it.toSExp() }.toTypedArray()), s(*outputParams.map { it.toSExp() }.toTypedArray()),
                s(t("declare"), s(t("assertion"), s(t("precd"), precondition.toSExp()),
                        s(t("postcd"), postcondition.toSExp()))), body.toSExp())


/**
 * It returns the CLIR representation of the given pattern
 */
fun Pattern.toSExp(): SExp = when (this) {
    is LiteralPattern -> t(literal)
    is ConstructorPattern -> s(t("@@"), t(constructorName), *constructorArgs.map { toSExp() }.toTypedArray())
    else -> UNDEFINED
}

/**
 * It returns the CLIR representation of the given case branch
 */
fun CaseBranch.toSExp(): SExp = s(pattern.toSExp(), expression.toSExp())

/**
 * It returns the CLIR representation of the given assertion
 */
fun Assertion.toSExp(): SExp = when (this) {
    is True -> t("true")
    is False -> t("false")
    is PredicateApplication -> s(t("@"), t(name), *arguments.map { it.toSExp() }.toTypedArray())
    is BooleanVariable -> t(name)
    is Not -> s(t("not"), assertion.toSExp())
    is And -> s(t("and"), *conjuncts.map { it.toSExp() }.toTypedArray())
    is Or -> s(t("or"), *disjuncts.map { it.toSExp() }.toTypedArray())
    is Implication -> s(t("->"), *operands.map { it.toSExp() }.toTypedArray())
    is Iff -> s(t("<->"), *operands.map { it.toSExp() }.toTypedArray())
    is ForAll ->
        s(t("forall"), s(*boundVars.map { it.toSExp() }.toTypedArray()), assertion.toSExp())
    is Exists ->
        s(t("exists"), s(*boundVars.map { it.toSExp() }.toTypedArray()), assertion.toSExp())
    else -> UNDEFINED
}


/**
 * It returns the CLIR representation of the given expression
 */
fun Term.toSExp(): SExp = when (this) {
    is Literal ->
        s(t("the"), type.toSExp(), t(value))

    is Variable ->
        t(name)

    is FunctionApplication ->
        s(t("@"), t(name), *arguments.map { it.toSExp() }.toTypedArray())

    is ConstructorApplication ->
        s(t("@@"), t(name), *arguments.map { it.toSExp() }.toTypedArray())

    is Tuple ->
        s(t("tuple"), *arguments.map { it.toSExp() }.toTypedArray())

    is Let ->
        s(t("let"), s(*bindings.map { it.toSExp() }.toTypedArray()), bindingExpression.toSExp(), mainExpression.toSExp())

    is LetFun ->
        s(t("letfun"), s(*defs.map { it.toSExp() }.toTypedArray()), mainExpression.toSExp())

    is Case ->
        if (defaultBranch != null) {
            s(t("case"), discriminant.toSExp(), s(*branches.map { it.toSExp() }.toTypedArray()), defaultBranch.toSExp())
        } else {
            s(t("case"), discriminant.toSExp(), s(*branches.map { it.toSExp() }.toTypedArray()))
        }

    else -> UNDEFINED
}

/**
 * It returns the CLIR representation of the given kappa declaration
 */
fun KappaDeclaration.toSExp(): SExp = when {
    this.qSet != null ->
        s(t(this.name),
                s(this.nuVar.toSExp(), *this.parameters.map { it.toSExp() }.toTypedArray()),
                s(t("Q"), *this.qSet.map { it.toSExp() }.toTypedArray()))
    else ->
        s(t(this.name),
                s(this.nuVar.toSExp(), *this.parameters.map { it.toSExp() }.toTypedArray()))
}

/**
 * It returns the CLIR representation of the given mu declaration
 */
fun MuDeclaration.toSExp(): SExp {
    val qSets = mutableListOf<SExp>()

    fun getSingle(name: String, qSet: Set<SingleQualifier>): SExp {
        val boundVars = qSet.map { it.boundVar }.toSet()
        if (boundVars.size > 1)
            throw InvalidQualifierSet(this.line, this.column, boundVars)

        return if (qSet.isEmpty()) {
            s(t(name), t(if (boundVars.isEmpty()) "i" else boundVars.first()))
        } else {
            s(t(name), t(if (boundVars.isEmpty()) "i" else boundVars.first()), *(qSet.map { it.assertion.toSExp() }.toTypedArray()))
        }
    }

    fun getDouble(name: String, qSet: Set<DoubleQualifier>): SExp {
        val boundVars1 = qSet.map { it.boundVar1 }.toSet()
        if (boundVars1.size > 1)
            throw InvalidQualifierSet(this.line, this.column, boundVars1)

        val boundVars2 = qSet.map { it.boundVar2 }.toSet()
        if (boundVars2.size > 1)
            throw InvalidQualifierSet(this.line, this.column, boundVars2)

        return if (qSet.isEmpty()) {
            s(t(name), t(if (boundVars1.isEmpty()) "i" else boundVars1.first()), t(if (boundVars2.isEmpty()) "j" else boundVars2.first()))
        } else {
            s(t(name), t(if (boundVars1.isEmpty()) "i" else boundVars1.first()), t(if (boundVars2.isEmpty()) "j" else boundVars2.first()), *(qSet.map { it.assertion.toSExp() }.toTypedArray()))
        }

    }

    if (this.qISet != null) {
        qSets.add(getSingle("QI", this.qISet))
    }

    if (this.qESet != null) {
        qSets.add(getSingle("QE", this.qESet))
    }

    if (this.qIISet != null) {
        qSets.add(getDouble("QII", this.qIISet))
    }

    if (this.qEESet != null) {
        qSets.add(getDouble("QEE", this.qEESet))
    }

    if (this.qLenSet != null) {
        qSets.add(s(t("QLen"), *this.qLenSet.map { it.toSExp() }.toTypedArray()))
    }

    return s(t(this.name), s(this.nuVar.toSExp(), *this.parameters.map { it.toSExp() }.toTypedArray()), *qSets.toTypedArray())
}

/**
 * It returns the CLIR representation of a verification unit
 */
fun VerificationUnit.toSExpList(): List<SExp> {
    val externalDefinitions: List<Pair<String, SExp>> = this.external.map { (funName, type) ->
        ":external" to
                s(t(funName),
                        s(*type.input.map { it.toSExp() }.toTypedArray()),
                        s(*type.output.map { it.toSExp() }.toTypedArray())
                )
    }


    /*
     * It returns the directives corresponding to Q and QLen
     */
    fun handleUnquantifiedGenericQualifiedSet(name: String, qSet: Set<GenericQualifier>): List<Pair<String, SExp>> =
            listOf(":qset" to s(t(name),
                    *qSet.map {
                        s(t(it.nu.varName), it.nu.HMType.toSExp(),
                                s(*it.markers.map { it.toSExp() }.toTypedArray()), it.assertion.toSExp())
                    }.toTypedArray()
            ))

    val setQ: List<Pair<String, SExp>> = handleUnquantifiedGenericQualifiedSet("Q", this.qSet)
    val setQLen: List<Pair<String, SExp>> = handleUnquantifiedGenericQualifiedSet("QLen", this.qLenSet)


    /*
     * It returns the directives corresponding to QI and QE
     */
    fun handleSingleGenericQualifiedSet(name: String, qSet: Set<GenericSingleQualifier>): List<Pair<String, SExp>> {
        val boundVars = qSet.map { it.boundVar }.toSet()
        if (boundVars.size > 1) {
            throw InvalidQualifierSet(0, 0, boundVars)
        }
        return listOf(":qset" to s(t(name), t(if (boundVars.isEmpty()) "i" else boundVars.first()),
                *qSet.map {
                    s(t(it.nu.varName), it.nu.HMType.toSExp(), s(*it.markers.map { it.toSExp() }.toTypedArray()), it.assertion.toSExp())
                }.toTypedArray()
        ))
    }


    val setQI = handleSingleGenericQualifiedSet("QI", this.qISet)
    val setQE = handleSingleGenericQualifiedSet("QE", this.qESet)

    /*
     * It returns the directives corresponding to QII and QEE
     */
    fun handleDoubleGenericQualifiedSet(name: String, qSet: Set<GenericDoubleQualifier>): List<Pair<String, SExp>> {
        val boundVars1 = qSet.map { it.boundVar1 }.toSet()
        if (boundVars1.size > 1) {
            throw InvalidQualifierSet(0, 0, boundVars1)
        }
        val boundVars2 = qSet.map { it.boundVar1 }.toSet()
        if (boundVars2.size > 1) {
            throw InvalidQualifierSet(0, 0, boundVars2)
        }
        return listOf(":qset" to s(t(name), t(if (boundVars1.isEmpty()) "i" else boundVars1.first()), t(if (boundVars2.isEmpty()) "j" else boundVars2.first()),
                *qSet.map {
                    s(t(it.nu.varName), it.nu.HMType.toSExp(), s(*it.markers.map { it.toSExp() }.toTypedArray()), it.assertion.toSExp())
                }.toTypedArray()
        ))
    }

    val setQII = handleDoubleGenericQualifiedSet("QII", this.qIISet)
    val setQEE = handleDoubleGenericQualifiedSet("QEE", this.qEESet)


    val kappaDecls = this.kappaDeclarations.map { ":kappa" to it.toSExp() }
    val muDecls = this.muDeclarations.map { ":mu" to it.toSExp() }

    val directives: List<Pair<String, SExp>> = externalDefinitions + setQ + setQI + setQE + setQII + setQEE + setQLen + kappaDecls + muDecls

    val header = s(t("verification-unit"), t(this.name),
            *directives.flatMap { (name, value) -> listOf(t(name), value) }.toTypedArray()
    )

    return listOf(header, *definitions.map {
        s(t("define"), *(it.toSExp() as ParenSExp).children.toTypedArray())
    }.toTypedArray<SExp>())
}

class InvalidQualifierSet(val line: Int, val column: Int, variables: Set<String>)
    : RuntimeException("In L$line, C$column: all the variables in the qualifier must be the same. Found: $variables")

