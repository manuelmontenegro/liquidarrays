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
/**
 * We want to avoid warnings about NAME_SHADOWING, because it is convenient
 * for us in this file.
 */
@file:Suppress("NAME_SHADOWING")

package es.ucm.caviart.ast

import es.ucm.caviart.utils.LiquidException

/*
    This constant specifies how many characters of an S-Expression should be printed
    in error messages
 */
private const val TRIM_LENGTH = 20

/**
 * Property for expressing the line of the source CLIR in which a given AST element starts
 */
var ASTElem.line: Int by ASTDelegate()

/**
 * Property for expression the column of the source CLIR in which a given AST element starts
 */
var ASTElem.column: Int by ASTDelegate()


/**
 * It changes the (line, column) pair of the `this` element and returns this
 * element.
 */
private fun <T : ASTElem> T.setLineColumn(line: Int, column: Int): T {
    this.line = line
    this.column = column
    return this
}

/**
 * It returns the AST representation of a verification unit from a list of
 * S-expressions.
 *
 * @param sexps List of S-expressions to parse
 * @return A VerificationUnit corresponding to the given S-expressions
 */
fun parseVerificationUnit(sexps: List<SExp>): VerificationUnit {
    // At least an S-Expression is required.
    if (sexps.isEmpty()) throw MissingHeaderException()

    // The first S-Expression must correspond to the :verification-unit header
    val header = sexps[0]
    if (header !is ParenSExp || header.children.size <= 1 || header.children[0] !is TokenSExp
            || (header.children[0] as TokenSExp).value != "verification-unit") {
        throw MissingHeaderException()
    }

    // We get the name of the verification unit
    val nameSExp = header.children[1]
    val vuName = (nameSExp as? TokenSExp)?.value ?: throw InvalidHeaderNoName()

    // We obtain the list of directives
    val directives = parseDirectives(header.children.subList(2, header.children.size))


    // We gather all the :external directives, build an environment with each
    // directive, and join all the environments (flatMap)
    val externals =
            directives.filter { (dir, _) -> dir == ":external" }
                    .flatMap { (_, exts) -> exts.map(::parseEnvironmentEntry) }

    // We gather all the :qset directives that represent generic Q sets.
    val qsets =
            directives.filter { (dir, _) -> dir == ":qset" }
                    .flatMap { (_, qsets) -> qsets }
                    .map { expectCompoundGreaterOrEqual(it, 1) }

    // We check whether all the parameters given to :qset begin with one of the Q set names (Q, QE, QI, ...)
    qsets.forEach { expectKeyword(it.children[0], "Q", "QE", "QI", "QEE", "QII", "QLen") }

    // Split the qsets into the different categories. Each resulting variable
    // is an S-Expression that will be parsed at the end of this function
    val q = qsets.find { (it.children[0] as TokenSExp).value == "Q" }
    val qI = qsets.find { (it.children[0] as TokenSExp).value == "QI" }
    val qE = qsets.find { (it.children[0] as TokenSExp).value == "QE" }
    val qII = qsets.find { (it.children[0] as TokenSExp).value == "QII" }
    val qEE = qsets.find { (it.children[0] as TokenSExp).value == "QEE" }
    val qLen = qsets.find { (it.children[0] as TokenSExp).value == "QLen" }

    // Parse the :kappa directives
    val kappas =
            directives.filter { (dir, _) -> dir == ":kappa" }
                    .flatMap { (_, kappaDecls) -> kappaDecls }
                    .map(::parseKappaDeclaration).toSet()

    // Parse the :mu directives
    val mus =
            directives.filter { (dir, _) -> dir == ":mu" }
                    .flatMap { (_, muDecls) -> muDecls }
                    .map(::parseMuDeclaration).toSet()

    // After discarding the header with the directives, we parse
    // the top level function definitions
    val defs = sexps.subList(1, sexps.size).map(::parseTopLevelFunctionDefinition)

    return VerificationUnit(vuName,
            externals.toMap(),
            if (q != null) parseGenericQSet(q) else setOf(),
            if (qI != null) parseGenericQIESet(qI) else setOf(),
            if (qE != null) parseGenericQIESet(qE) else setOf(),
            if (qII != null) parseGenericQIIEESet(qII) else setOf(),
            if (qEE != null) parseGenericQIIEESet(qEE) else setOf(),
            if (qLen != null) parseGenericQSet(qLen) else setOf(),
            kappas, mus,
            defs).setLineColumn(sexps[0].line, sexps[0].column)
}


/**
 * It gethers the set of directives of a verification unit from a list
 * of toplevel S-expressions. Directives always start with a token that
 * begins with the `:` symbol (e.g. `:verification-unit`) and it can be
 * followed with a list of parameters.
 *
 * @param sexps List of toplevel S-expressions to watch
 * @return List of pairs <directive_name => [directive-value1, directive-value2, ...]>
 */
fun parseDirectives(sexps: List<SExp>): List<Pair<String, List<SExp>>> {
    val result = mutableListOf<Pair<String, List<SExp>>>()

    // Parameters of the current directive (initially null)
    var currentList: MutableList<SExp>? = null

    for (sexp in sexps) {
        when {
            /* If is a directive token, we associate it with a fresh list of parameters */
            sexp is TokenSExp && sexp.value.startsWith(":") -> {
                currentList = mutableListOf()
                result.add(sexp.value to currentList)
            }

            /* If it os not a directive token, it is one of the parameters of the previous directive token */
            currentList != null -> {
                currentList.add(sexp)
            }

            else -> throw DanglingExpression(sexp)
        }
    }

    return result
}

/**
 * It parses a generic set of qualifiers. This applies to Q and QLen sets.
 *
 * The input S-expression must be of the form
 *
 * ```
 * (Q qual1St qual2St ...)
 * ```
 *
 * or
 *
 * ```
 * (QLen qual1St qual2St ...)
 * ```
 *
 * where `qual1St`, `qual2St` are parametric qualifiers with placeholders.
 *
 * @param sexp S-expression to generate.
 * @return set of generic qualifiers.
 */
fun parseGenericQSet(sexp: SExp): Set<GenericQualifier> {
    val sexp = expectCompoundGreaterOrEqual(sexp, 1)
    expectKeyword(sexp.children[0], "Q", "QLen")
    return sexp.children.subList(1, sexp.children.size).map(::parseGenericQualifier).toSet()
}

/**
 * It parses a generic set of qualifiers with single quantification. This applies to QI and QE sets.
 *
 * The input S-expression must be of the form
 *
 * ```
 * (QI i qual1St qual2St ...)
 * ```
 *
 * or
 *
 * ```
 * (QE i qual1St qual2St ...)
 * ```
 *
 * where `i` is the name of the index variable, and `qual1St`, `qual2St` are parametric qualifiers
 * with placeholders.
 *
 * @param sexp S-expression to generate.
 * @return set of generic singly-quantified qualifiers.
 */
fun parseGenericQIESet(sexp: SExp): Set<GenericSingleQualifier> {
    val sexp = expectCompoundGreaterOrEqual(sexp, 2)
    expectKeyword(sexp.children[0], "QI", "QE")
    val boundVar = expectSimple(sexp.children[1]).value
    return sexp.children.subList(2, sexp.children.size)
            .map(::parseGenericQualifier)
            .map { qual -> GenericSingleQualifier(qual.nu, boundVar, qual.markers, qual.assertion).addPropertiesFrom(qual) }
            .toSet()
}

/**
 * It parses a generic set of qualifiers with double quantification. This applies to QII and QEE sets.
 *
 * The input S-expression must be of the form
 *
 * ```
 * (QII i j qual1St qual2St ...)
 * ```
 *
 * or
 *
 * ```
 * (QEE i j qual1St qual2St ...)
 * ```
 *
 * where `i` and `j` are the names of the index variables, and `qual1St`, `qual2St` are parametric qualifiers
 * with placeholders.
 *
 * @param sexp S-expression to generate.
 * @return set of generic doubly-quantified qualifiers.
 */
fun parseGenericQIIEESet(sexp: SExp): Set<GenericDoubleQualifier> {
    val sexp = expectCompoundGreaterOrEqual(sexp, 3)
    expectKeyword(sexp.children[0], "QII", "QEE")
    val boundVar1 = expectSimple(sexp.children[1]).value
    val boundVar2 = expectSimple(sexp.children[2]).value
    return sexp.children.subList(3, sexp.children.size)
            .map(::parseGenericQualifier)
            .map { qual -> GenericDoubleQualifier(qual.nu, boundVar1, boundVar2, qual.markers, qual.assertion).addPropertiesFrom(qual) }
            .toSet()
}

/**
 * It parses a specific QSet (without placeholders). It is just one of the following
 * expressions:
 *
 * ```
 * (Q assertion1 assertion2 ...)
 * (QLen assertion1 assertion2 ...)
 * ```
 *
 * @param sexp S-Expression from which the AST will be obtained
 * @return The set of assertions corresponding to the parameters of the S-expression (excluding the preceding 'Q' or 'QLen')
 */
fun parseSpecificQSet(sexp: SExp): Set<Assertion> {
    val sexp = expectCompoundGreaterOrEqual(sexp, 1)
    expectKeyword(sexp.children[0], "Q", "QLen")
    return sexp.children.subList(1, sexp.children.size).map(::parseAssertion).toSet()
}

/**
 * It parses a specific QI or QE (without placeholders). It is just one of the following
 * expressions:
 *
 * ```
 * (QI i assertion1 assertion2 ...)
 * (QE i assertion1 assertion2 ...)
 * ```
 *
 * where `i` is the index variable
 *
 * @param sexp S-Expression from which the AST will be obtained
 * @return The set of specific single qualifiers corresponding to the parameters of the
 *         S-expression (excluding the preceding 'QI' or 'QE')
 */
fun parseSpecificQIESet(sexp: SExp): Set<SingleQualifier> {
    val sexp = expectCompoundGreaterOrEqual(sexp, 2)
    expectKeyword(sexp.children[0], "QI", "QE")
    val boundVar = expectSimple(sexp.children[1]).value
    return sexp.children.subList(2, sexp.children.size).map(::parseAssertion)
            .map { SingleQualifier(boundVar, it).addPropertiesFrom(it) }.toSet()
}

/**
 * It parses a specific QII or QEE (without placeholders). It is just one of the following
 * expressions:
 *
 * ```
 * (QII i j assertion1 assertion2 ...)
 * (QEE i j assertion1 assertion2 ...)
 * ```
 *
 * where `i` and `j` are index variables.
 *
 * @param sexp S-Expression from which the AST will be obtained
 * @return The set of specific double qualifiers corresponding to the parameters of the
 *         S-expression (excluding the preceding 'QII' or 'QEE')
 */
fun parseSpecificQIIEESet(sexp: SExp): Set<DoubleQualifier> {
    val sexp = expectCompoundGreaterOrEqual(sexp, 3)
    expectKeyword(sexp.children[0], "QII", "QEE")
    val boundVar1 = expectSimple(sexp.children[1]).value
    val boundVar2 = expectSimple(sexp.children[2]).value
    return sexp.children.subList(3, sexp.children.size).map(::parseAssertion)
            .map { DoubleQualifier(boundVar1, boundVar2, it).addPropertiesFrom(it) }.toSet()
}

/**
 * It parses a qualifier with parameters and placeholders. It has the form:
 *
 * ```
 * (nu typeNu ((placeholdername1 type1) (placeholdername2 type2) ...) assertion)
 * ```
 *
 * @param sexp S-Expression from which the AST will be obtained
 * @return The GenericQualifier object corresponding to the AST.
 */
fun parseGenericQualifier(sexp: SExp): GenericQualifier {
    val sexp = expectCompound(sexp, 4)
    val nu = expectSimple(sexp.children[0]).value
    val hmType = parseHMType(sexp.children[1])
    val markers = expectCompound(sexp.children[2]).children.map(::parseHMTypedVar)
    val assertion = parseAssertion(sexp.children[3])

    return GenericQualifier(HMTypedVar(nu, hmType), markers, assertion).setLineColumn(sexp.line, sexp.column)
}

/**
 * It returns a mapping name -> type from an entry in an :environment directive
 *
 * An environment entry has the form `(name ((input-param1 type1) ...) ((output-param1 type1) ...))`
 *
 * @param sexp S-expression to transform
 * @return Pair `name -> type`.
 */
fun parseEnvironmentEntry(sexp: SExp): Pair<String, FunctionalType> {
    val sexp = expectCompound(sexp, 3)
    val name = expectSimple(sexp.children[0]).value
    val input = expectCompound(sexp.children[1]).children.map(::parseTypedVar)
    val output = expectCompound(sexp.children[2]).children.map(::parseTypedVar)

    return name to FunctionalType(input, output).setLineColumn(sexp.line, sexp.column)
}

/**
 * It returns the AST representation of the argument given to a :kappa declaration.
 * Allowed kappas are of the form:
 *
 * ```
 * (name ((var1 type1) ...))
 * ```
 *
 * or with a specific Q*-set
 *
 * ```
 * (name ((var1 type1) ...) (Q assertion1 assertion2 ...))
 * ```
 *
 * @param sexp S-Expression to parse
 * @return AST corresponding to a Kappa declaration
 */
fun parseKappaDeclaration(sexp: SExp): KappaDeclaration {
    val sexp = expectCompound(sexp, 2, 3)
    val name = expectSimple(sexp.children[0]).value
    val boundVars = expectCompoundGreaterOrEqual(sexp.children[1], 1).children.map(::parseHMTypedVar)
    val qsets = if (sexp.children.size == 3) parseSpecificQSet(sexp.children[2]) else null

    return KappaDeclaration(name, boundVars[0], boundVars.subList(1, boundVars.size), qsets).setLineColumn(sexp.line, sexp.column)
}

/**
 * It returns the AST representation of the argument given to a :mu declaration.
 * Allowed mus are of the form:
 *
 * ```
 * (name ((var1 type1) ...))
 * ```
 *
 * or with a sequence of specific Q*-sets
 *
 * ```
 * (name ((var1 type1) ...) qset1 qset2 ...)
 * ```
 *
 * @param sexp S-Expression to parse
 * @return AST corresponding to a Kappa declaration
 */
fun parseMuDeclaration(sexp: SExp): MuDeclaration {
    // In addition to the two mandatory sub-expressions, up to five Q-Set declarations
    // can be supplied
    val sexp = expectCompound(sexp, 2, 3, 4, 5, 6, 7)
    val name = expectSimple(sexp.children[0]).value
    val boundVars = expectCompoundGreaterOrEqual(sexp.children[1], 1).children.map(::parseHMTypedVar)

    val qsets = sexp.children.subList(2, sexp.children.size)
            .map { expectCompoundGreaterOrEqual(it, 1) }

    // Each qset declaration must be an expression (K assertion1 assertion2 ...) where
    // K is one of the allowed set names: QI, QE, QII, ...
    qsets.forEach { expectKeyword(it.children[0], "QI", "QE", "QII", "QEE", "QLen") }

    // We obtain each of the QSets
    val qI = qsets.find { (it.children[0] as TokenSExp).value == "QI" }
    val qE = qsets.find { (it.children[0] as TokenSExp).value == "QE" }
    val qII = qsets.find { (it.children[0] as TokenSExp).value == "QII" }
    val qEE = qsets.find { (it.children[0] as TokenSExp).value == "QEE" }
    val qLen = qsets.find { (it.children[0] as TokenSExp).value == "QLen" }

    return MuDeclaration(name,
            boundVars[0],
            boundVars.subList(1, boundVars.size),
            if (qI != null) parseSpecificQIESet(qI) else null,
            if (qE != null) parseSpecificQIESet(qE) else null,
            if (qII != null) parseSpecificQIIEESet(qII) else null,
            if (qEE != null) parseSpecificQIIEESet(qEE) else null,
            if (qLen != null) parseSpecificQSet(qLen) else null
    )
}

/**
 * It builds the AST of a top-level function definition from an S-expression
 *
 * A function definition has the form:
 *
 * ```
 * (define fun-name input-params output-params body)
 * ```
 *
 * or
 *
 * ```
 * (define fun-name input-params output-params specification body)
 * ```
 *
 * @param sexp S-Expression to parse
 * @return FunctionDefinition object
 */
fun parseTopLevelFunctionDefinition(sexp: SExp): FunctionDefinition {

    val sexp = expectCompound(sexp, 5, 6)
    expectKeyword(sexp.children[0], "define")

    // We just get rid of the initial `define` token and handle the top-level
    // function definition as if it were a letfun binding
    val newSExp = ParenSExp(sexp.line, sexp.column, sexp.children.subList(1, sexp.children.size))

    return parseFunctionDefinition(newSExp)
}

/**
 * It builds the AST of a letfun-bound function definition from an S-expression
 *
 * A function definition has the form:
 *
 * ```
 * (fun-name input-params output-params body)
 * ```
 *
 * or
 *
 * ```
 * (fun-name input-params output-params specification body)
 * ```
 *
 * where `specification` is an expression of the form:
 *
 * ```
 * (declare (assertion (precd assertion-precd) (postcd assertion-postcd))
 * ```
 *
 * The first `assertion` shown above is literally the word "assertion", not an assertion itself
 * given by the grammar of assertions.
 *
 * @param sexp S-Expression to parse
 * @return FunctionDefinition object
 */
fun parseFunctionDefinition(sexp: SExp): FunctionDefinition {
    val sexp = expectCompound(sexp, 4, 5)

    // We obtain the mandatory parameters
    val name = expectSimple(sexp.children[0])
    val inputParams = expectCompound(sexp.children[1])
    val outputParams = expectCompound(sexp.children[2])
    val body = sexp.children[sexp.children.size - 1]

    // A function for looking into the (declare) of the specification
    // for a precondition or postcondition (depending on `str`).
    fun lookForAssertion(str: String, sexp: SExp): Assertion? {
        val sexp = expectCompound(sexp, 2)
        expectKeyword(sexp.children[0], "declare")

        val sexpInner = expectCompoundGreaterOrEqual(sexp.children[1], 1)
        expectKeyword(sexpInner.children[0], "assertion")

        sexpInner.children.subList(1, sexpInner.children.size).forEach {
            val pair = expectCompound(it, 2)
            val first = expectSimple(pair.children[0])
            if (first.value == str) {
                return parseAssertion(pair.children[1])
            }
        }

        return null
    }

    // If there are five parameters, expect
    val precondition = if (sexp.children.size == 5) lookForAssertion("precd", sexp.children[3]) else null
    val postcondition = if (sexp.children.size == 5) lookForAssertion("postcd", sexp.children[3]) else null


    return FunctionDefinition(
            name.value,
            inputParams.children.map(::parseTypedVar),
            outputParams.children.map(::parseTypedVar),
            parseExpression(body),
            precondition ?: True(),
            postcondition ?: True()
    ).setLineColumn(sexp.line, sexp.column)
}

/**
 * It parses a Hindley-Milner type from an S-Expression
 *
 * @param sexp S-expression to parse
 * @return Resulting HM-Type
 */
fun parseHMType(sexp: SExp): HMType = when {
    sexp is TokenSExp && sexp.value.startsWith("'") -> throw UnsupportedTypeVariables(sexp.line, sexp.column)
    sexp is TokenSExp -> ConstrType(sexp.value).setLineColumn(sexp.line, sexp.column)
    sexp is ParenSExp && sexp.children.isNotEmpty() -> {
        val constructor = expectSimple(sexp.children[0])
        ConstrType(constructor.value, sexp.children.subList(1, sexp.children.size).map(::parseHMType)).setLineColumn(sexp.line, sexp.column)
    }
    else -> throw UnknownType(sexp.line, sexp.column)
}

/**
 * It parses a possibly qualified type from an S-Expression
 *
 * A qualified type has the form `(qual nu hmType assertion)`
 *
 * @param sexp S-expression to parse
 * @return Resulting Type
 */
fun parseType(sexp: SExp): Type = when {
    // If it is a qualified type...
    sexp is ParenSExp && sexp.children.size == 4 && sexp.children[0] is TokenSExp && (sexp.children[0] as TokenSExp).value == "qual" -> {
        val nuVar = expectSimple(sexp.children[1])
        val hmType = parseHMType(sexp.children[2])
        val assertion = parseAssertion(sexp.children[3])
        QualType(nuVar.value, hmType, assertion).setLineColumn(sexp.line, sexp.column)
    }

    // otherwise is a plain HM type
    else -> parseHMType(sexp)
}


/**
 * It returns the AST representation from an S-expression representing
 * a typed variable of the form `(var type)`, where `type` can be a qualified
 * type.
 *
 * @param sexp Expression to parse
 * @return AST of the given S-Expression
 */
fun parseTypedVar(sexp: SExp): TypedVar {
    val sexp = expectCompound(sexp, 2)
    val name = expectSimple(sexp.children[0])
    val type = parseType(sexp.children[1])
    return TypedVar(name.value, type).setLineColumn(sexp.line, sexp.column)
}

/**
 * It returns the AST representation from an S-expression representing
 * a typed variable of the form `(var type)`, where `type` is a HM-type.
 *
 * @param sexp Expression to parse
 * @return AST of the given S-Expression
 */
fun parseHMTypedVar(sexp: SExp): HMTypedVar {
    val sexp = expectCompound(sexp, 2)
    val name = expectSimple(sexp.children[0])
    val type = parseHMType(sexp.children[1])
    return HMTypedVar(name.value, type).setLineColumn(sexp.line, sexp.column)
}

/**
 * It transforms an S-Expression into the AST representation of an assertion.
 *
 * @param sexp S-Expression to parse
 * @result AST corresponding to the S-Expression
 */
fun parseAssertion(sexp: SExp): Assertion = when (sexp) {
    is TokenSExp -> when (sexp.value) {
        "true" -> True().setLineColumn(sexp.line, sexp.column)
        "false" -> False().setLineColumn(sexp.line, sexp.column)
        else -> throw IncorrectAssertion(sexp)
    }

    is ParenSExp -> {
        expectCompoundGreaterOrEqual(sexp, 1)
        val keyword = expectSimple(sexp.children[0]).value
        when (keyword) {
            // Predicate application
            "@" -> {
                expectCompoundGreaterOrEqual(sexp, 2)
                val predName = expectSimple(sexp.children[1]).value
                val arguments = sexp.children.subList(2, sexp.children.size).map(::parseBindingExpression)
                PredicateApplication(predName, arguments).setLineColumn(sexp.line, sexp.column)
            }

            // Conjunction
            "and" -> {
                And(sexp.children.subList(1, sexp.children.size).map(::parseAssertion)).setLineColumn(sexp.line, sexp.column)
            }

            // Disjunction
            "or" -> {
                Or(sexp.children.subList(1, sexp.children.size).map(::parseAssertion)).setLineColumn(sexp.line, sexp.column)
            }

            // Negation
            "not" -> {
                expectCompound(sexp, 2)
                Not(parseAssertion(sexp.children[1])).setLineColumn(sexp.line, sexp.column)
            }

            // Implication
            "->" -> {
                Implication(sexp.children.subList(1, sexp.children.size).map(::parseAssertion)).setLineColumn(sexp.line, sexp.column)
            }

            // Two-sided implication
            "<->" -> {
                Iff(sexp.children.subList(1, sexp.children.size).map(::parseAssertion)).setLineColumn(sexp.line, sexp.column)
            }

            // Universal cuantification
            "forall" -> {
                expectCompound(sexp, 3)
                val typedVars = expectCompound(sexp.children[1])
                val boundVars = typedVars.children.map(::parseHMTypedVar)
                val assertion = parseAssertion(sexp.children[2])
                ForAll(boundVars, assertion).setLineColumn(sexp.line, sexp.column)
            }

            // Existential quantification
            "exists" -> {
                expectCompound(sexp, 3)
                val typedVars = expectCompound(sexp.children[1])
                val boundVars = typedVars.children.map(::parseHMTypedVar)
                val assertion = parseAssertion(sexp.children[2])
                Exists(boundVars, assertion).setLineColumn(sexp.line, sexp.column)
            }

            else -> throw IncorrectAssertion(sexp)
        }
    }
    else -> throw IncorrectAssertion(sexp)
}

/**
 * It transforms an S-Expression into the AST representation of an atomic expression
 *
 * @param sexp S-Expression to parse
 * @result AST corresponding to the S-Expression
 */
fun parseAtomicExpression(sexp: SExp): Atomic = when (sexp) {
    // If it is a simple expression, it is a variable
    is TokenSExp -> {
        Variable(sexp.value).setLineColumn(sexp.line, sexp.column)
    }

    // Otherwise, it is a literal of the form `(the type literal)`.
    is ParenSExp -> {
        expectCompoundGreaterOrEqual(sexp, 1)

        // We allow all the keywords, because the fallback cases of the remaining
        // functions for parsing expressions (parseBindingExpression and parseExpression)
        // will call this function.
        expectKeyword(sexp.children[0], "the", "@", "tuple", "@@", "let", "letfun", "case")

        expectCompound(sexp, 3)
        val type = parseHMType(sexp.children[1])
        val literal = expectSimple(sexp.children[2])
        Literal(literal.value, type).setLineColumn(sexp.line, sexp.column)
    }

    else -> throw IncorrectExpression(sexp)
}

/**
 * It transforms an S-Expression into the AST representation of an atomic expression
 *
 * @param sexp S-Expression to parse
 * @result AST corresponding to the S-Expression
 */
fun parseBindingExpression(sexp: SExp): BindingExpression = when (sexp) {
    is ParenSExp -> {
        expectCompoundGreaterOrEqual(sexp, 1)
        val keyword = expectSimple(sexp.children[0])
        when (keyword.value) {
            "@" -> {
                expectCompoundGreaterOrEqual(sexp, 2)
                val funName = expectSimple(sexp.children[1]).value
                val arguments = sexp.children.subList(2, sexp.children.size).map(::parseAtomicExpression)
                FunctionApplication(funName, arguments).setLineColumn(sexp.line, sexp.column)
            }
            "tuple" -> {
                val arguments = sexp.children.subList(1, sexp.children.size).map(::parseAtomicExpression)
                Tuple(arguments).setLineColumn(sexp.line, sexp.column)
            }
            "@@" -> {
                expectCompoundGreaterOrEqual(sexp, 2)
                val consName = expectSimple(sexp.children[1]).value
                val arguments = sexp.children.subList(2, sexp.children.size).map(::parseAtomicExpression)
                ConstructorApplication(consName, arguments).setLineColumn(sexp.line, sexp.column)
            }
            else -> parseAtomicExpression(sexp)
        }
    }

    else -> parseAtomicExpression(sexp)

}

/**
 * It transforms an S-Expression into the AST representation of an expression
 *
 * @param sexp S-Expression to parse
 * @result AST corresponding to the S-Expression
 */
fun parseExpression(sexp: SExp): Term = when (sexp) {
    is ParenSExp -> {
        expectCompoundGreaterOrEqual(sexp, 1)
        val keyword = expectSimple(sexp.children[0])
        when (keyword.value) {
            "let" -> {
                expectCompound(sexp, 4)
                val bindings = expectCompoundGreaterOrEqual(sexp.children[1], 1).children.map(::parseHMTypedVar)
                val boundExp = parseBindingExpression(sexp.children[2])
                val mainExp = parseExpression(sexp.children[3])
                Let(bindings, boundExp, mainExp).setLineColumn(sexp.line, sexp.column)
            }

            "letfun" -> {
                expectCompound(sexp, 3)
                val definitions = expectCompoundGreaterOrEqual(sexp.children[1], 1).children.map(::parseFunctionDefinition)
                val mainExp = parseExpression(sexp.children[2])
                LetFun(definitions, mainExp).setLineColumn(sexp.line, sexp.column)
            }

            "case" -> {
                expectCompound(sexp, 3, 4)
                val discriminant = expectSimple(sexp.children[1]).value
                val branches = expectCompoundGreaterOrEqual(sexp.children[2], 1).children.map {
                    val alt = expectCompound(it, 2)
                    val pattern = parsePattern(alt.children[0])
                    val expression = parseExpression(alt.children[1])
                    CaseBranch(pattern, expression).setLineColumn(sexp.line, sexp.column)
                }

                val defaultBranch = if (sexp.children.size == 4) parseExpression(sexp.children[3]) else null

                Case(Variable(discriminant).setLineColumn(sexp.children[1].line, sexp.children[1].column),
                        branches, defaultBranch).setLineColumn(sexp.line, sexp.column)
            }
            else -> parseBindingExpression(sexp)
        }
    }
    else -> parseAtomicExpression(sexp)
}

/**
 * It transforms an S-Expression into the AST representation of a pattern
 *
 * @param sexp S-Expression to parse
 * @result AST corresponding to the S-Expression
 */
fun parsePattern(sexp: SExp): Pattern = when (sexp) {
    is TokenSExp -> {
        LiteralPattern(sexp.value).setLineColumn(sexp.line, sexp.column)
    }

    is ParenSExp -> {
        expectCompoundGreaterOrEqual(sexp, 1)
        expectKeyword(sexp.children[0], "@@")
        expectCompoundGreaterOrEqual(sexp, 2)
        val constructor = expectSimple(sexp.children[1]).value
        val vars = sexp.children.subList(2, sexp.children.size).map {
            expectSimple(it).value
        }
        ConstructorPattern(constructor, vars).setLineColumn(sexp.line, sexp.column)
    }

    else -> throw IncorrectPattern(sexp)
}

/**
 * It checks whether the given S-expression is a token of one of the
 * specified in the `keyword` list of arguments.
 *
 * @param sexp S-Expression to check
 * @param keyword List of allowed keywords
 */
fun expectKeyword(sexp: SExp, vararg keyword: String) {
    if (sexp !is TokenSExp) {
        throw UnexpectedKeyword(sexp.line, sexp.column, sexp.toString().take(30), listOf())
    }

    if (sexp.value !in keyword) {
        throw UnexpectedKeyword(sexp.line, sexp.column, sexp.value, keyword.toList())
    }
}

/**
 * It transforms the type of an S-expression into a compound one, provided
 * the latter is actually a compound S-expression with the given number of
 * children. Otherwise it throws an exception.
 *
 * @param sexp S-Expression to analyze
 * @param numChildren Expected number of components in the S-expression. The programmer
 *                    can supply more than one expected number.
 *
 * @return The same S-expression as `sexp`, but with type `ParenSExp`.
 */
fun expectCompound(sexp: SExp, vararg numChildren: Int): ParenSExp {
    if (sexp !is ParenSExp) {
        throw ExpectedCompound(sexp)
    }

    if (numChildren.isNotEmpty() && sexp.children.size !in numChildren) {
        throw WrongArguments(sexp.line, sexp.column, sexp.trim(), sexp.children.size, numChildren.toList())
    }

    return sexp
}

/**
 * It transforms the type of an S-expression into a compound one, provided
 * the latter is actually a compound S-expression with the a number of
 * children equal or greater than the given one. Otherwise it throws an exception.
 *
 * @param sexp S-Expression to analyze
 * @param minimum Expected number of components in the S-expression
 *
 * @return The same S-expression as `sexp`, but with type `ParenSExp`.
 */
fun expectCompoundGreaterOrEqual(sexp: SExp, minimum: Int): ParenSExp {
    if (sexp !is ParenSExp) {
        throw ExpectedCompound(sexp)
    }

    if (sexp.children.size < minimum) {
        throw FewArguments(sexp.line, sexp.column, sexp.trim(), sexp.children.size, minimum)
    }

    return sexp

}

/**
 * It transforms the type of an S-expression into a compound one, provided
 * the latter is actually a simple S-expression with no children.
 * Otherwise it throws an exception.
 *
 * @param sexp S-Expression to analyze
 *
 * @return The same S-expression as `sexp`, but with type `TokenSExp`.
 */
fun expectSimple(sexp: SExp): TokenSExp {
    if (sexp !is TokenSExp) {
        throw ExpectedSimple(sexp)
    }
    return sexp
}

fun SExp.trim(): String {
    val str = this.toString()
    return if (str.length > TRIM_LENGTH) {
        str.substring(0, TRIM_LENGTH) + "..."
    } else str
}


fun String.toAssertion(): Assertion {
    val sexps = getSExps(this)
    return parseAssertion(sexps[0])
}

fun String.toTerm(): Term {
    val sexps = getSExps(this)
    return parseExpression(sexps[0])
}

fun String.toDef(): FunctionDefinition {
    val sexps = getSExps(this)
    return parseFunctionDefinition(sexps[0])
}

class MissingHeaderException : LiquidException("Missing 'verification-unit' header")
class InvalidHeaderNoName : LiquidException("Invalid 'verification-unit' header. No varName given")
class DanglingExpression(sexp: SExp) : LiquidException("Expression ${sexp.trim()} is not preceeded by a directive")


class ExpectedCompound(given: SExp) :
        Exception("In L${given.line}, C${given.column}: expected compound S-Expression, but found ${given.trim()}")

class ExpectedSimple(given: SExp) :
        Exception("In L${given.line}, C${given.column}: unexpected compound S-Expression, but found ${given.trim()}")

class WrongArguments(line: Int, column: Int, name: String, given: Int, expected: List<Int>) :
        Exception("In L$line, C$column: wrong number of arguments ($given) given to $name. Expected ${expected.joinToString(" or ")}")

class FewArguments(line: Int, column: Int, name: String, given: Int, minimum: Int) :
        Exception("In L$line, C$column: wrong number of arguments ($given) given to $name. Expected $minimum or more")

class UnexpectedKeyword(line: Int, column: Int, given: String, expected: List<String>) :
        Exception("In L$line, C$column: unexpected '$given'${if (expected.isNotEmpty()) ". Expected ${expected.joinToString(" or ")}" else ""}")

class IncorrectExpression(given: SExp) :
        Exception("In L${given.line}, C${given.column}: incorrect expression: ${given.trim()}")

class IncorrectPattern(given: SExp) :
        Exception("In L${given.line}, C${given.column}: incorrect pattern: ${given.trim()}")

class IncorrectAssertion(given: SExp) :
        Exception("In L${given.line}, C${given.column}: incorrect assertion: ${given.trim()}")

class UnsupportedTypeVariables(line: Int, column: Int) :
        Exception("In L$line C$column: type variables are not currently supported")

class UnknownType(line: Int, column: Int) :
        Exception("In L$line C$column: expected type")