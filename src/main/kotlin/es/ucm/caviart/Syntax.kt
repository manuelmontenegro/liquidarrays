package es.ucm.caviart

import com.microsoft.z3.Expr
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

data class ConstrType(val typeConstructor: String,
                  val arguments: List<HMType> = listOf()) : HMType()

data class VarType(val variable: String) : HMType()

abstract class HMType : Type()

data class QualType(val nu: String, val HMType: HMType, val qualifier: Assertion) : Type()

abstract class Type : ASTElem()


data class UninterpretedFunctionType(val argumentTypes: List<HMType>, val resultType: HMType) : ASTElem()


abstract class Atomic : BindingExpression()

data class Literal(val value: String,
                   val type: HMType) : Atomic()

data class Variable(val name: String) : Atomic()

abstract class BindingExpression : Term()

data class FunctionApplication(val name: String,
                               val arguments: List<BindingExpression> = listOf()) : BindingExpression()

data class Tuple(val arguments: List<Atomic>) : BindingExpression()

data class ConstructorApplication(val name: String,
                                  val arguments: List<BindingExpression> = listOf()) : BindingExpression()


abstract class Term : ASTElem()

data class Let(val bindings: List<HMTypedVar>,
               val bindingExpression: BindingExpression,
               val mainExpression: Term) : Term() {
    constructor(variable: String, type: HMType, bindingExpression: BindingExpression, mainExpression: Term)
            : this(listOf(HMTypedVar(variable, type)), bindingExpression, mainExpression)
}

data class LetFun(val defs: List<FunctionDefinition>,
                  val mainExpression: Term) : Term()

data class Case(val discriminant: Atomic,
                val branches: List<CaseBranch>,
                val defaultBranch: Term? = null) : Term()


data class CaseBranch(val pattern: Pattern,
                      val expression: Term) : ASTElem() {
    constructor(constructorName: String, constructorVars: List<String>, expression: Term)
            : this(ConstructorPattern(constructorName, constructorVars), expression)
}

abstract class Pattern : ASTElem()

data class LiteralPattern(val literal: String) : Pattern()
data class ConstructorPattern(val constructorName: String, val constructorArgs: List<String>) : Pattern()


data class TypedVar(val varName: String, val type: Type) : ASTElem()
data class HMTypedVar(val varName: String, val HMType: HMType) : ASTElem()


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

data class ForAll(val boundVars: List<HMTypedVar>,
                  val assertion: Assertion) : Assertion() {
    constructor(varName: String, type: HMType, assertion: Assertion)
            : this(listOf(HMTypedVar(varName, type)), assertion)
}

data class Exists(val boundVars: List<HMTypedVar>,
                  val assertion: Assertion) : Assertion() {
    constructor(varName: String, type: HMType, assertion: Assertion)
            : this(listOf(HMTypedVar(varName, type)), assertion)
}

/*
data class LetAssertion(val bindings: List<TypedVar>,
                        val boundTerm: Term,
                        val mainAssertion: Assertion) : Assertion() {
    constructor(varName: String, type: HMType, boundTerm: Term, mainAssertion: Assertion)
            : this(listOf(TypedVar(varName, type)), boundTerm, mainAssertion)
}

data class CaseAssertion(val discriminant: Atomic,
                         val branches: List<CaseAssertionBranch>,
                         val defaultBranch: CaseAssertionBranch?) : Assertion()


data class CaseAssertionBranch(val constructorName: String,
                               val constructorVars: List<TypedVar>,
                               val assertion: Assertion) : ASTElem()
*/

data class FunctionalType(val input: List<TypedVar>, val output: List<TypedVar>)

data class GenericQualifier(val nu: HMTypedVar, val markers: HMTypedVar, val assertion: Assertion)
data class GenericSingleQualifier(val nu: HMTypedVar, val boundVar: String, val markers: HMTypedVar, val assertion: Assertion)
data class GenericDoubleQualifier(val nu: HMTypedVar, val boundVar1: String, val boundVar2: String, val markers: HMTypedVar, val assertion: Assertion)


data class SingleQualifier(val boundVar: String, val assertion: Assertion)
data class DoubleQualifier(val boundVar1: String, val boundVar2: String, val assertion: Assertion)

data class KappaDeclaration(val name: String, val nuVar: HMTypedVar, val parameters: List<HMTypedVar>, val qSet: Set<Assertion>)
data class MuDeclaration(val name: String, val nuVar: HMTypedVar, val parameters: List<HMTypedVar>,
                         val qISet: Set<SingleQualifier>, val qESet: Set<SingleQualifier>,
                         val qIISet: Set<DoubleQualifier>, val qEESet: Set<DoubleQualifier>)

data class VerificationUnit(val name: String,
                            val external: Map<String, FunctionalType>,
                            val qset: Set<GenericQualifier>,
                            val qISet: Set<GenericSingleQualifier>,
                            val qESet: Set<GenericSingleQualifier>,
                            val qIISet: Set<GenericDoubleQualifier>,
                            val qEESet: Set<GenericDoubleQualifier>,
                            val qLenSet: Set<GenericQualifier>,
                            val kappaDeclarations: Set<KappaDeclaration>,
                            val muDeclaration: Set<MuDeclaration>,
                            val definitions: List<FunctionDefinition>)


class ASTDelegate {
    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any?> getValue(thisRef: ASTElem, property: KProperty<*>): T =
            thisRef.properties.getOrElse(property.name, { throw PropertyNotFoundException(property.name) }) as T

    operator fun <T> setValue(thisRef: ASTElem, property: KProperty<*>, value: T) {
        thisRef.properties[property.name] = value
    }
}

class PropertyNotFoundException(val property: String) : RuntimeException("Property not found: $property")










