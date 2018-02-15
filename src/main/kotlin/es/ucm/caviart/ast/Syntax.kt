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

import kotlin.reflect.KProperty


/**
 * An instance of a subclass of ASTElem represents an element of the Abstract Syntax Tree,
 * which can be an expression, a type, a typed variable, or an assertion.
 *
 * Each instance contains a dynamically extensible set of [properties]. Properties can be defined
 * by extension attributes by means of the [ASTDelegate] class.
 *
 * @param properties A map with the dynamically attached decorations of the AST element.
 */
abstract class ASTElem(val properties: MutableMap<String, Any?> = mutableMapOf())


/**
 * It copies the properties of a given AST element into another one.
 *
 * @param other The AST element from which gather the properties
 * @return `this` object
 */
fun <T : ASTElem> T.addPropertiesFrom(other: ASTElem): T {
    this.properties.putAll(other.properties)
    return this
}

/**
 * An instance of [Type] represents both standard Hindley-Milner types as well as
 * refined types.
 */
abstract class Type : ASTElem() {
    /**
     * It contains the underlying Hindley-Milner type, in the case
     * of refinement types.
     */
    abstract val hmType: HMType
}

/**
 * An instance of [HMType] represents a standard Hindley-Milner type.
 */
abstract class HMType : Type()


/**
 * Class for representing applications of type constructors.
 *
 * @property typeConstructor Type constructor being applied
 * @property arguments Hindley-Milner types to which the constructor is applied
 */
data class ConstrType(val typeConstructor: String,
                      val arguments: List<HMType> = listOf()) : HMType() {
    override val hmType: HMType
        get() = this
}

val intType = ConstrType("int")

fun arrayType(t: HMType): HMType = ConstrType("array", listOf(t))



/**
 * Class for representing type variables (not supported yet)
 *
 * @property variable Name of the type variable (e.g. 'a', 'b')
 */
data class VarType(val variable: String) : HMType() {
    override val hmType: HMType
        get() = this
}

/**
 * Class for representing qualified types
 *
 * @property nu Name of the bound value variable (nu)
 * @property HMType Underlying Hindley-Milner Type
 * @property qualifier Refinement, in which the [nu] variable may occur
 */
data class QualType(val nu: String, val HMType: HMType, val qualifier: Assertion) : Type() {
    override val hmType: HMType
        get() = HMType
}


/**
 * Instances of this class represent functional Hindley-Milner types
 *
 * @property argumentTypes A list of the types of function arguments
 * @property resultType The type of the result of the function
 */
data class UninterpretedFunctionType(val argumentTypes: List<HMType>, val resultType: HMType) : ASTElem()

/**
 * Instances of this class represent functional and possibly refined types
 *
 * @property input A list with the types of function arguments, each one with a variable name
 * @property output A list with the types of the results, each one with a variable name
 */
data class FunctionalType(val input: List<TypedVar>, val output: List<TypedVar>) : ASTElem()


/**
 * Base class representing language expressions
 */
abstract class Term : ASTElem()


/**
 * Base class representing atomic expressions (constants and variables)
 */
abstract class Atomic : BindingExpression()

/**
 * Base class representing all the binding expressions (i.e. those that
 * may appear in the bound expression of a `let` (see [Let] for details).
 */
abstract class BindingExpression : Term()


/**
 * Instances of this class represent a typed constant (e.g. number).
 *
 * **CLIR expression**: `(the [type] [value])`
 *
 * @property value Constant value (represented as a string)
 * @property type Hindley-Milner type of the constant
 */
data class Literal(val value: String,
                   val type: HMType) : Atomic()


/**
 * Instances of this class represent a program variable
 *
 * **CLIR expression**: `[name]`
 *
 * @property name Name of the variable
 */
data class Variable(val name: String) : Atomic()


/**
 * Instances of this class represent a funcion application expression.
 *
 * **CLIR expression**: `(@ [name] [arguments]_1 [arguments]_2 ...)`
 *
 * @property name Name of the function being applied
 * @property arguments List of the arguments passed to the function
 */
data class FunctionApplication(val name: String,
                               val arguments: List<Atomic> = listOf()) : BindingExpression()

/**
 * Instances of this class represent a tuple construction
 *
 * **CLIR expression**: `(tuple [arguments]_1 [arguments]_2 ...)`
 *
 * @property arguments List of the tuple components
 */
data class Tuple(val arguments: List<Atomic>) : BindingExpression()


/**
 * Instances of this class represent an application of a constructor symbol
 *
 * **CLIR expression**: `(@@ [name] [arguments]_1 [arguments]_2 ...)`
 *
 * @property name Name of the constructor being applied
 * @property arguments List of the constructor arguments
 */
data class ConstructorApplication(val name: String,
                                  val arguments: List<Atomic> = listOf()) : BindingExpression()


/**
 * Instances of this class represent a `let` expression.
 *
 * **CLIR expression**: `(let ([bindings]_1 [bindings]_2 ...) [bindingExpression] [mainExpression])`
 *
 * @property bindings List of bound variables, each one with its type
 * @property bindingExpression Bound (auxiliary) expression
 * @property mainExpression Main expression
 */
data class Let(val bindings: List<HMTypedVar>,
               val bindingExpression: BindingExpression,
               val mainExpression: Term) : Term()


/**
 * Instances of this class represent a `letfun` expression, for defining a mutually
 * recursive set of function definitions.
 *
 * **CLIR expression**: `(letfun ([defs]_1 [defs]_2 ...) [mainExpression])`
 *
 * @property defs List of auxiliary function definitions
 * @property mainExpression Main expression
 */
data class LetFun(val defs: List<FunctionDefinition>,
                  val mainExpression: Term) : Term()

/**
 * Instances of this class represent `case` expressions.
 *
 * **CLIR expression**: `(case [discriminant] ([branches]_1 [branches]_2, ...))` or `(case [discriminant] ([branches]_1 [branches]_2, ...) [defaultBranch])`
 *
 *
 * @property discriminant Discriminant of the case
 * @property branches List of pairs (pattern, expression), each one representing a case branch.
 * @property defaultBranch Catch-all branch (optional)
 */
data class Case(val discriminant: Atomic,
                val branches: List<CaseBranch>,
                val defaultBranch: Term? = null) : Term()


/**
 * A pair representing a pattern and an expression. Used in case branches.
 *
 * **CLIR expression**: `([pattern] [expression])`
 *
 * @property pattern Pattern of the branch
 * @property expression Expression to be executed, in case the discriminant matches the pattern
 */
data class CaseBranch(val pattern: Pattern,
                      val expression: Term) : ASTElem()

/**
 * Instances of this class represent a case pattern.
 */
abstract class Pattern : ASTElem()

/**
 * Literal patterns, for matching on integers
 *
 * **CLIR expression**: `[literal]`
 *
 * @property literal Literal value
 */
data class LiteralPattern(val literal: String) : Pattern()

/**
 * Pattern that represents a constructor symbol applied to a list of variables
 *
 * **CLIR expression**: `(@@ [constructorName] [constructorArgs]_1 [constructorArgs]_2 ...)`
 *
 *
 * @property constructorName Name of the constructor
 * @property constructorArgs Variables that will be bound to the values to which the constructor is applied
 */
data class ConstructorPattern(val constructorName: String, val constructorArgs: List<String>) : Pattern()

/**
 * Instances of this class represent pairs (value, type).
 *
 * **CLIR expression**: `([varName] [type])`
 *
 * These pairs are used, for instance, for defining function parameters
 */
data class TypedVar(val varName: String, val type: Type) : ASTElem()

/**
 * Instances of this class represent pairs (value, HM-type). For instance, in function parameters
 *
 * **CLIR expression**: `([varName] [HMType])`
 *
 * These are used, for instance, in `let` expressions:
 */
data class HMTypedVar(val varName: String, val HMType: HMType) : ASTElem()

/**
 * Instances of this class represent function definitions
 *
 * @property name Name of the function being defined
 * @property inputParams Names and types of the input parameters
 * @property outputParams Names and types of the results
 * @property body Body of the function definition
 * @property precondition Precondition of the function
 * @property postcondition Postcondition of the function
 */
data class FunctionDefinition(val name: String,
                              val inputParams: List<TypedVar>,
                              val outputParams: List<TypedVar>,
                              val body: Term,
                              val precondition: Assertion = False(),
                              val postcondition: Assertion = True()) : ASTElem()


/**
 * Instances of this class represent logical formulas (i.e. assertions).
 */
abstract class Assertion : ASTElem()

/**
 * Atomic assertion that always holds.
 */
class True : Assertion() {
    override fun toString(): String = "True()"
    override fun equals(other: Any?): Boolean = other is True
    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

/**
 * Atomic assertion that never holds.
 */
class False : Assertion() {
    override fun toString(): String = "False()"
    override fun equals(other: Any?): Boolean = other is False
    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

/**
 * Variable denoting an assertion
 *
 * @property name Name of the variable
 */
data class BooleanVariable(val name: String) : Assertion()

/**
 * Application of a predicate symbol to a list of binding expressions
 *
 * **CLIR expression**: `(@ [name] [arguments]_1 [arguments]_2 ...)
 *
 * @property name Predicate symbol being applied
 * @property arguments Expressions to which the predicate symbol is applied
 */
data class PredicateApplication(val name: String,
                                val arguments: List<BindingExpression> = listOf()) : Assertion()

/**
 * Negation of an assertion.
 *
 * **CLIR expression**: `(not [assertion])
 *
 * @property [assertion] Assertion being negated
 */
data class Not(val assertion: Assertion) : Assertion()

/**
 * Logical conjunction
 *
 * **CLIR expression**: `(and [conjuncts]_1 [conjuncts]_2 ...)
 *
 * @property [conjuncts] List of child assertions
 */
data class And(val conjuncts: List<Assertion>) : Assertion() {
    /**
     * Alternative constructor for constructing conjunctions
     *
     * @param [conjuncts] List of child assertions
     */
    constructor(vararg conjuncts: Assertion) : this(conjuncts.asList())
}

/**
 * Logical disjunction
 *
 * **CLIR expression**: `(or [disjuncts]_1 [disjuncts]_2 ...)
 *
 * @property [disjuncts] List of child assertions
 */
data class Or(val disjuncts: List<Assertion>) : Assertion() {
    /**
     * Alternative constructor for disjunctions
     *
     * @param [disjuncts] List of child assertions
     */
    constructor(vararg disjuncts: Assertion) : this(disjuncts.asList())
}


/**
 * Logical implication. The `->` operator is assumed to be right-associative
 *
 * **CLIR expression**: `(-> [operands]_1 [operands]_2 ...)
 *
 * @property [operands] List of child assertions
 */
data class Implication(val operands: List<Assertion>) : Assertion() {
    /**
     * Constructor for simple assertions of the form `(-> [antecedent] [consequent])`
     *
     * @param antecedent Left-hand side of the implication
     * @param consequent Right-hand side of the implication
     */
    constructor(antecedent: Assertion, consequent: Assertion) : this(listOf(antecedent, consequent))
}

/**
 * Logical equivalence.
 *
 * **CLIR expression**: `(<-> [operands]_1 [operands]_2 ...)
 *
 * @property [operands] List of child assertions
 */
data class Iff(val operands: List<Assertion>) : Assertion() {
    constructor (lhs: Assertion, rhs: Assertion) : this(listOf(lhs, rhs))
}

/**
 * Universal quantification
 *
 * **CLIR expression**: `(forall ([boundVars]_1 [boundVars]_2 ...) [assertion])
 *
 * @param boundVars List of quantified variables
 * @param assertion Main assertion, which can contain the bound variables
 */
data class ForAll(val boundVars: List<HMTypedVar>,
                  val assertion: Assertion) : Assertion()

/**
 * Existencial quantification
 *
 * **CLIR expression**: `(exists ([boundVars]_1 [boundVars]_2 ...) [assertion])
 *
 * @param boundVars List of quantified variables
 * @param assertion Main assertion, which can contain the bound variables
 */
data class Exists(val boundVars: List<HMTypedVar>,
                  val assertion: Assertion) : Assertion()

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

/**
 * A qualifier from the `Q` or `QLen` set. It may contain placeholders ([markers]) and references to a context [nu] variable.
 *
 * @property nu Context variable, representing the value being refined
 * @property markers List of wildcards occurring in the refinement, each one with its type
 * @property assertion The refinement itself
 */
data class GenericQualifier(val nu: HMTypedVar, val markers: List<HMTypedVar>, val assertion: Assertion) : ASTElem()


/**
 * A qualifier from the `QI` or `QE` set. It may contain placeholders ([markers]) and references to a context [nu] variable, and
 * references to the variable [boundVar] representing the index of the array.
 *
 * @property nu Context variable, representing the value being refined
 * @property boundVar Variable representing the index of the array
 * @property markers List of wildcards occurring in the refinement, each one with its type
 * @property assertion The refinement itself
 */
data class GenericSingleQualifier(val nu: HMTypedVar, val boundVar: String, val markers: List<HMTypedVar>, val assertion: Assertion) : ASTElem()


/**
 * A qualifier from the `QII` or `QEE` set. It may contain placeholders ([markers]) and references to a context [nu] variable, and
 * references to variables [boundVar1] and [boundVar2] representing indices of the array.
 *
 * @property nu Context variable, representing the value being refined
 * @property boundVar1 Variable representing the first index of the array
 * @property boundVar2 Variable representing the second index of the array
 * @property markers List of wildcards occurring in the refinement, each one with its type
 * @property assertion The refinement itself
 */
data class GenericDoubleQualifier(val nu: HMTypedVar, val boundVar1: String, val boundVar2: String, val markers: List<HMTypedVar>, val assertion: Assertion) : ASTElem()


/**
 * A qualifier from the `QI*` or `QE*` set. It references indices of the array.
 *
 * @property boundVar Variable representing the index of the array
 * @property assertion The refinement itself
 */
data class SingleQualifier(val boundVar: String, val assertion: Assertion) : ASTElem()

/**
 * A qualifier from the `QII*` or `QEE*` set. It references indices of the array.
 *
 * @property boundVar1 Variable representing the first index of the array
 * @property boundVar2 Variable representing the second index of the array
 * @property assertion The refinement itself
 */
data class DoubleQualifier(val boundVar1: String, val boundVar2: String, val assertion: Assertion) : ASTElem()

/**
 * Explicit declaration of a kappa variable with its allowed refinements
 *
 * @property name Name of the kappa
 * @property nuVar Nu variable to which the kappa is applied
 * @property parameters Rest of the kappa parameters (variables in scope)
 * @property qSet (Optional) set of allowed refinements (Q*). If not specified, the generic set Q defined in the verification unit will be applied.
 */
data class KappaDeclaration(val name: String,
                            val nuVar: HMTypedVar,
                            val parameters: List<HMTypedVar>,
                            val qSet: Set<Assertion>?) : ASTElem()

/**
 * Explicit declaration of a mu variable with its allowed refinements
 *
 * @property name Name of the kappa
 * @property nuVar Nu variable to which the kappa is applied
 * @property parameters Rest of the kappa parameters (variables in scope)
 * @property qISet (Optional) specific set QI*. If not specified, the generic set QI defined in the verification unit will be applied.
 * @property qESet (Optional) specific set QE*. If not specified, the generic set QE defined in the verification unit will be applied.
 * @property qIISet (Optional) specific set QII*. If not specified, the generic set QII defined in the verification unit will be applied.
 * @property qEESet (Optional) specific set QEE*. If not specified, the generic set QEE defined in the verification unit will be applied.
 * @property qLenSet (Optional) specific set QLen*. If not specified, the generic set QLen defined in the verification unit will be applied.
 */
data class MuDeclaration(val name: String, val nuVar: HMTypedVar, val parameters: List<HMTypedVar>,
                         val qISet: Set<SingleQualifier>?, val qESet: Set<SingleQualifier>?,
                         val qIISet: Set<DoubleQualifier>?, val qEESet: Set<DoubleQualifier>?,
                         val qLenSet: Set<Assertion>?) : ASTElem()

/**
 * Instances of this class represent verification units
 *
 * @property name Name of the verification unit
 * @property external Environment with the external function definitions
 * @property qSet Set of generic refinements (Q)
 * @property qSet Set of generic refinements on the index variable (QI)
 * @property qISet Set of generic refinements on the index variable (QI)
 * @property qESet Set of generic refinements on the array elements (QE)
 * @property qIISet Set of generic double refinements on the index variables (QII)
 * @property qEESet Set of generic double refinements on the array elements (QEE)
 * @property qLenSet Set of generic refinements on the length of the array (QLen)
 * @property kappaDeclarations Declaration of kappa template variables
 * @property muDeclarations Declaration of mu template variables
 * @property definitions Toplevel function definitions
 */
data class VerificationUnit(val name: String,
                            val external: Map<String, FunctionalType>,
                            val qSet: Set<GenericQualifier>,
                            val qISet: Set<GenericSingleQualifier>,
                            val qESet: Set<GenericSingleQualifier>,
                            val qIISet: Set<GenericDoubleQualifier>,
                            val qEESet: Set<GenericDoubleQualifier>,
                            val qLenSet: Set<GenericQualifier>,
                            val kappaDeclarations: Set<KappaDeclaration>,
                            val muDeclarations: Set<MuDeclaration>,
                            val definitions: List<FunctionDefinition>) : ASTElem()


/**
 * Delegate for accessing dynamic properties of a node of the AST (line numbers, etc)
 */
class ASTDelegate {
    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any?> getValue(thisRef: ASTElem, property: KProperty<*>): T =
            thisRef.properties.getOrElse(property.name, { throw PropertyNotFoundException(property.name) }) as T

    operator fun <T> setValue(thisRef: ASTElem, property: KProperty<*>, value: T) {
        thisRef.properties[property.name] = value
    }
}

private class PropertyNotFoundException(property: String) : RuntimeException("Property not found: $property")

/**
 * Exception that will be thrown when applying a function to an unsupported AST type.
 */
class InvalidASTException(elem: ASTElem) : RuntimeException("Invalid AST element: $elem")


/**
 * It gets the variables occurring in a binding expression.
 *
 * @return Set of variable names of the expression
 */
fun BindingExpression.getVariables(): Set<String> = when (this) {
    is Literal -> emptySet()

    is Variable -> setOf(name)

    is FunctionApplication -> arguments.flatMap { it.getVariables() }.toSet()

    is ConstructorApplication -> arguments.flatMap { it.getVariables() }.toSet()

    is Tuple -> arguments.flatMap { it.getVariables() }.toSet()

    else -> throw InvalidASTException(this)
}

/**
 * It returns the set of free variables occurring in an assertion
 *
 * @return Set of free variable names
 */
fun Assertion.getVariables(): Set<String> = when (this) {
    is True -> emptySet()

    is False -> emptySet()

    is BooleanVariable -> setOf(name)

    is PredicateApplication -> arguments.flatMap { it.getVariables() }.toSet()

    is Not -> assertion.getVariables()

    is And -> conjuncts.flatMap { it.getVariables() }.toSet()

    is Or -> disjuncts.flatMap { it.getVariables() }.toSet()

    is Implication -> operands.flatMap { it.getVariables() }.toSet()

    is Iff -> operands.flatMap { it.getVariables() }.toSet()

    is ForAll -> assertion.getVariables() - boundVars.map { it.varName }

    is Exists -> assertion.getVariables() - boundVars.map { it.varName }

    else -> throw InvalidASTException(this)
}



fun BindingExpression.findArrayAccesses(indexVar: String, environment: Map<String, HMType>): Set<Pair<String, HMType>> = when (this) {
    is Literal -> emptySet()

    is Variable -> emptySet()

    is FunctionApplication -> {
        if (this.name == "get-array" || this.name == "set-array") {
            val arrayName = (this.arguments[0] as Variable).name
            val indexName = (this.arguments[1] as Variable).name
            if (indexVar == indexName) {
                val arrayType = environment[arrayName]
                if (arrayType == null || arrayType !is ConstrType || arrayType.typeConstructor != "array") {
                    throw RuntimeException("get-array applied to $arrayName of non-array type, but it has not been caught before (?)")
                } else {
                    setOf(arrayName to arrayType.arguments[0])
                }
            } else {
                emptySet()
            }
        } else {
            emptySet()
        }
    }

    is ConstructorApplication -> emptySet()

    is Tuple -> emptySet()

    else -> throw InvalidASTException(this)
}



fun Assertion.findArrayAccesses(indexVar: String, environment: Map<String, HMType>): Set<Pair<String, HMType>> = when (this) {
    is True -> emptySet()

    is False -> emptySet()

    is BooleanVariable -> emptySet()

    is PredicateApplication -> arguments.flatMap { it.findArrayAccesses(indexVar, environment) }.toSet()

    is Not -> assertion.findArrayAccesses(indexVar, environment)

    is And -> conjuncts.flatMap { it.findArrayAccesses(indexVar, environment) }.toSet()

    is Or -> disjuncts.flatMap { it.findArrayAccesses(indexVar, environment) }.toSet()

    is Implication -> operands.flatMap { it.findArrayAccesses(indexVar, environment) }.toSet()

    is Iff -> operands.flatMap { it.findArrayAccesses(indexVar, environment) }.toSet()

    is ForAll -> assertion.findArrayAccesses(indexVar, environment + boundVars.map { it.varName to it.HMType })

    is Exists -> assertion.findArrayAccesses(indexVar, environment + boundVars.map { it.varName to it.HMType })

    else -> throw InvalidASTException(this)

}




