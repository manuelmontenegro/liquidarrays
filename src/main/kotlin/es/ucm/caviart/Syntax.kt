package es.ucm.caviart

import kotlin.reflect.KProperty

/**
 * Abstract Syntax Definition
 *
 * Created by manuel on 30/05/17.
 */

/**
 * An instance of a subclass of ASTElem represents an element of the Abstract Syntax Tree,
 * which can be an expression, a type, a typed variable, or an assertion.
 *
 * Each instance contains a dynamically extensible set of [properties]. Properties can be defined
 * by extension attributes by means of the [ASTDelegate] class.
 *
 * @author Manuel Montenegro
 * @param properties A map with the dynamically attached decorations of the AST element.
 */
abstract class ASTElem(val properties: MutableMap<String, Any?> = mutableMapOf())

data class Type(val typeConstructor: String,
                val arguments: List<Type> = listOf()) : ASTElem()

data class UninterpretedFunctionType(val argumentTypes: List<Type>, val resultType: Type) : ASTElem()


abstract class Atomic : BindingExpression()

data class Literal(val value: String,
                   val type: Type) : Atomic()

data class Variable(val name: String) : Atomic()

abstract class BindingExpression : Term()

data class FunctionApplication(val name: String,
                               val arguments: List<BindingExpression> = listOf()) : BindingExpression()

data class Tuple(val arguments: List<Atomic>) : BindingExpression()

data class ConstructorApplication(val name: String,
                                  val arguments: List<BindingExpression> = listOf()) : BindingExpression()



abstract class Term : ASTElem()

data class Let(val bindings: List<TypedVar>,
               val bindingExpression: BindingExpression,
               val mainExpression: Term) : Term() {
    constructor(variable: String, type: Type, bindingExpression: BindingExpression, mainExpression: Term)
            : this(listOf(TypedVar(variable, type)), bindingExpression, mainExpression)
}

data class LetFun(val defs: List<FunctionDefinition>,
                  val mainExpression: Term) : Term()

data class Case(val discriminant: Atomic,
                val branches: List<CaseBranch>,
                val defaultBranch: CaseBranch? = null) : Term()


data class CaseBranch(val constructorName: String,
                          val constructorVars: List<TypedVar>,
                          val expression: Term) : ASTElem()

data class TypedVar(val varName: String, val type: Type) : ASTElem()


data class FunctionDefinition(val name: String,
                              val inputParams: List<TypedVar>,
                              val outputParams: List<TypedVar>,
                              val body: Term,
                              val precondition: Assertion = False(),
                              val postcondition: Assertion = True()) : ASTElem()

abstract class Assertion : ASTElem()

class True : Assertion()

class False : Assertion()

data class PredicateApplication(val name: String,
                                val arguments: List<BindingExpression> = listOf()) : Assertion()

data class Not(val assertion: Assertion) : Assertion()

data class And(val conjuncts: List<Assertion>) : Assertion() {
    constructor(vararg conjuncts: Assertion) : this(conjuncts.asList())
}

data class Or(val disjuncts: List<Assertion>) : Assertion() {
    constructor(vararg disjuncts: Assertion) : this(disjuncts.asList())
}

data class Implication(val operands: List<Assertion>) : Assertion() {
    constructor(antecedent: Assertion, consequent: Assertion) : this(listOf(antecedent, consequent))
}

data class Iff(val operands: List<Assertion>) : Assertion() {
    constructor(lhs: Assertion, rhs: Assertion)
            : this(listOf(lhs, rhs))
}

data class ForAll(val boundVars: List<TypedVar>,
                  val assertion: Assertion) : Assertion() {
    constructor(varName: String, type: Type, assertion: Assertion)
            : this(listOf(TypedVar(varName, type)), assertion)
}

data class Exists(val boundVars: List<TypedVar>,
                  val assertion: Assertion) : Assertion() {
    constructor(varName: String, type: Type, assertion: Assertion)
            : this(listOf(TypedVar(varName, type)), assertion)
}

data class LetAssertion(val bindings: List<TypedVar>,
                        val boundTerm: Term,
                        val mainAssertion: Assertion) : Assertion() {
    constructor(varName: String, type: Type, boundTerm: Term, mainAssertion: Assertion)
            : this(listOf(TypedVar(varName, type)), boundTerm, mainAssertion)
}

data class CaseAssertion(val discriminant: Atomic,
                         val branches: List<CaseAssertionBranch>,
                         val defaultBranch: CaseAssertionBranch?) : Assertion()


data class CaseAssertionBranch(val constructorName: String,
                      val constructorVars: List<TypedVar>,
                      val assertion: Assertion) : ASTElem()


class ASTDelegate {
    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any?> getValue(thisRef: ASTElem, property: KProperty<*>): T =
            thisRef.properties.getOrElse(property.name, { throw PropertyNotFoundException(property.name) }) as T

    operator fun <T> setValue(thisRef: ASTElem, property: KProperty<*>, value: T) {
        thisRef.properties[property.name] = value
    }
}

class PropertyNotFoundException(val property: String) : RuntimeException("Property not found: $property")










