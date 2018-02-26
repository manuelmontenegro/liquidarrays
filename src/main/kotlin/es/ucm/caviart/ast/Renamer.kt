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

/**
 * This module provides function for renaming the variables in CLIR programs
 * so as to avoid name clashes
 */

import es.ucm.caviart.utils.mapAccumLeft

/**
 * Given a variable name and a set of variables in scope, it
 * returns a fresh variable (i.e. not appearing in `taken`)
 *
 * @param prefix Name to take as a base for the fresh variable
 * @param taken Set of variables in scope
 *
 * @return A fresh variable distinct from `prefix` and not occurring in `taken`.
 */
fun freshName(prefix: String, taken: Set<String>): String {
    var currentNumber = 1
    while ("${prefix}_$currentNumber" in taken) {
        currentNumber += 1
    }
    return "${prefix}_$currentNumber"
}

typealias Renaming = Map<String, String>


/**
 * It renames a given atomic expression according to the given renaming
 *
 * @param renaming Renaming to apply
 *
 * @return Atomic expression that results from applying the renaming
 */
fun Atomic.applyRenaming(renaming: Renaming): Atomic = when (this) {
    is Literal -> this
    is Variable -> Variable(renaming[this.name] ?: this.name).addPropertiesFrom(this)
    else -> throw RuntimeException("Renaming: unknown atomic expression")
}

/**
 * It renames a given binding expression according to the given renaming
 *
 * @param renaming Renaming to apply
 *
 * @return Binding expression that results from applying the renaming
 */
fun BindingExpression.applyRenaming(renaming: Renaming): BindingExpression = when (this) {
    is Atomic -> this.applyRenaming(renaming)
    is FunctionApplication -> this.copy(arguments = this.arguments.map { it.applyRenaming(renaming) }).addPropertiesFrom(this)
    is Tuple -> this.copy(arguments = this.arguments.map { it.applyRenaming(renaming) }).addPropertiesFrom(this)
    is ConstructorApplication -> this.copy(arguments = this.arguments.map { it.applyRenaming(renaming) }).addPropertiesFrom(this)
    else -> throw RuntimeException("Renaming: unknown binding expression")
}

/**
 * It renames a given expression according to a given renaming, assuming that
 * the variables passed as second parameter are in scope
 *
 * @param renaming Renaming to apply
 * @param scope List of variables in scope
 *
 * @return Expression that results from applying the renaming and by
 *         substituing all the bound variables in the expression that are already
 *         in higher scopes, to avoid name clashes
 */
fun Term.applyRenaming(renaming: Renaming, scope: Set<String>): Term = when (this) {
    is BindingExpression -> this.applyRenaming(renaming)

    is Let -> {
        val (e, newBindings) = this.bindings.mapAccumLeft(Pair(renaming, scope)) { (ren, sc), bv ->
            if (bv.varName in sc) {
                val fresh = freshName(bv.varName, sc)
                Pair(ren + (bv.varName to fresh), sc + fresh) to bv.copy(varName = fresh).addPropertiesFrom(bv)
            } else {
                Pair(ren, sc + bv.varName) to bv
            }
        }

        val (nextRenaming, nextScope) = e
        this.copy(
                bindings = newBindings,
                bindingExpression = bindingExpression.applyRenaming(renaming),
                mainExpression = mainExpression.applyRenaming(nextRenaming, nextScope)
        ).addPropertiesFrom(this)
    }

    is LetFun -> this.copy(
            defs = defs.map { it.applyRenaming(renaming, scope) },
            mainExpression = mainExpression.applyRenaming(renaming, scope)
    ).addPropertiesFrom(this)

    is Case -> this.copy(
            discriminant = discriminant.applyRenaming(renaming),
            branches = branches.map { it.applyRenaming(renaming, scope) },
            defaultBranch = defaultBranch?.applyRenaming(renaming, scope)
    ).addPropertiesFrom(this)


    else -> throw RuntimeException("Renaming: unknown expression")
}

/**
 * It renames a given case branch according to a given renaming, assuming that
 * the variables passed as second parameter are in scope
 *
 * @param renaming Renaming to apply
 * @param scope List of variables in scope
 *
 * @return Case branch that results from applying the renaming and by
 *         substituing all the bound variables in the expression that are already
 *         in higher scopes, to avoid name clashes
 */
fun CaseBranch.applyRenaming(renaming: Renaming, scope: Set<String>): CaseBranch {

    val (e, newPattern) =
            if (this.pattern is ConstructorPattern) {
                val (ep, newArgs) = this.pattern.constructorArgs.mapAccumLeft(Pair(renaming, scope)) { (ren, sc), v ->
                    if (v in sc) {
                        val fresh = freshName(v, sc)
                        Pair(ren + (v to fresh), sc + fresh) to fresh
                    } else Pair(ren, sc + v) to v
                }
                Pair(ep, this.pattern.copy(constructorArgs = newArgs).addPropertiesFrom(this.pattern))
            } else Pair(renaming, scope) to this.pattern

    val (nextRenaming, nextScope) = e


    return this.copy(pattern = newPattern, expression = expression.applyRenaming(nextRenaming, nextScope)).addPropertiesFrom(this)
}

/**
 * It renames a given function definition according to a given renaming, assuming that
 * the variables passed as second parameter are in scope
 *
 * @param renaming Renaming to apply
 * @param scope List of variables in scope
 *
 * @return Function definition that results from applying the renaming and by
 *         substituing all the bound variables in the expression that are already
 *         in higher scopes, to avoid name clashes
 */
fun FunctionDefinition.applyRenaming(renaming: Renaming, scope: Set<String>): FunctionDefinition {

    val (e, newInputParams) = this.inputParams.mapAccumLeft(Pair(renaming, scope)) { (ren, sc), tv ->
        if (tv.varName in sc) {
            val fresh = freshName(tv.varName, sc)
            Pair(ren + (tv.varName to fresh), sc + fresh) to tv.copy(varName = fresh, type = tv.type.applyRenaming(ren, sc)).addPropertiesFrom(tv)
        } else Pair(ren, sc + tv.varName) to tv.copy(type = tv.type.applyRenaming(ren, sc)).addPropertiesFrom(tv)
    }

    val (nextRenaming, nextScope) = e

    val newPrecondition = this.precondition.applyRenaming(nextRenaming, nextScope)
    val newBody = this.body.applyRenaming(nextRenaming, nextScope)

    val (e2, newOutputParams) = this.outputParams.mapAccumLeft(Pair(nextRenaming, nextScope)) { (ren, sc), tv ->
        if (tv.varName in sc) {
            val fresh = freshName(tv.varName, sc)
            Pair(ren + (tv.varName to fresh), sc + fresh) to tv.copy(varName = fresh, type = tv.type.applyRenaming(ren, sc)).addPropertiesFrom(tv)
        } else Pair(ren, sc + tv.varName) to tv.copy(type = tv.type.applyRenaming(ren, sc)).addPropertiesFrom(tv)
    }

    val (nextRenaming2, nextScope2) = e2

    val newPostcondition = this.postcondition.applyRenaming(nextRenaming2, nextScope2)

    return this.copy(
            inputParams = newInputParams,
            precondition = newPrecondition,
            outputParams = newOutputParams,
            postcondition = newPostcondition,
            body = newBody
    ).addPropertiesFrom(this)
}

/**
 * It renames the qualifier of a type according to a given renaming, assuming that
 * the variables passed as second parameter are in scope
 *
 * @param renaming Renaming to apply
 * @param scope List of variables in scope
 *
 * @return Resulting type with the renamed qualifier
 */
fun Type.applyRenaming(renaming: Renaming, scope: Set<String>): Type = when(this) {
    is QualType -> if (this.nu in scope) {
        val fresh = freshName(this.nu, scope)
        this.copy(nu = fresh, qualifier = qualifier.applyRenaming(renaming + (nu to fresh), scope + fresh)).addPropertiesFrom(this)
    } else {
        this.copy(qualifier = qualifier.applyRenaming(renaming, scope + this.nu)).addPropertiesFrom(this)
    }

    else -> this
}

/**
 * It renames an assertion according to a given renaming, assuming that
 * the variables passed as second parameter are in scope
 *
 * @param renaming Renaming to apply
 * @param scope List of variables in scope
 *
 * @return Resulting assertion
 */
fun Assertion.applyRenaming(renaming: Renaming, scope: Set<String>): Assertion = when(this) {
    is BooleanVariable -> BooleanVariable(renaming[name] ?: name).addPropertiesFrom(this)

    is PredicateApplication -> this.copy(
            arguments = this.arguments.map { it.applyRenaming(renaming) }
    ).addPropertiesFrom(this)

    is Not -> this.copy(assertion.applyRenaming(renaming, scope)).addPropertiesFrom(this)

    is And -> this.copy(conjuncts = conjuncts.map { it.applyRenaming(renaming, scope) }).addPropertiesFrom(this)

    is Or -> this.copy(disjuncts = disjuncts.map { it.applyRenaming(renaming, scope) }).addPropertiesFrom(this)

    is Implication -> this.copy(operands = operands.map { it.applyRenaming(renaming, scope) }).addPropertiesFrom(this)

    is Iff -> this.copy(operands = operands.map { it.applyRenaming(renaming, scope) }).addPropertiesFrom(this)

    is ForAll -> {
        val (e, newVars) = this.boundVars.mapAccumLeft(Pair(renaming, scope)) { (ren, sc), tv ->
            if (tv.varName in sc) {
                val fresh = freshName(tv.varName, sc)
                Pair(ren + (tv.varName to fresh), sc + fresh) to tv.copy(varName = fresh).addPropertiesFrom(tv)
            } else Pair(ren, sc + tv.varName) to tv
        }

        val (nextRenaming, nextScope) = e

        this.copy(boundVars = newVars, assertion = assertion.applyRenaming(nextRenaming, nextScope))
    }

    is Exists -> {
        val (e, newVars) = this.boundVars.mapAccumLeft(Pair(renaming, scope)) { (ren, sc), tv ->
            if (tv.varName in sc) {
                val fresh = freshName(tv.varName, sc)
                Pair(ren + (tv.varName to fresh), sc + fresh) to tv.copy(varName = fresh).addPropertiesFrom(tv)
            } else Pair(ren, sc + tv.varName) to tv
        }

        val (nextRenaming, nextScope) = e

        this.copy(boundVars = newVars, assertion = assertion.applyRenaming(nextRenaming, nextScope))
    }

    else -> this
}


/**
 * It renames a verification unit by distinguishing all the bound variables in
 * nested scopes, in order to avoid name clashes
 *
 * @return Updated verification unit
 */

fun VerificationUnit.applyRenaming() =
        this.copy(definitions = this.definitions.map { it.applyRenaming(mapOf(), setOf()) }).addPropertiesFrom(this)
