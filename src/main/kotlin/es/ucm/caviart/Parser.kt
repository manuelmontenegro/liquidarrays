package es.ucm.caviart

val TRIM_LENGTH = 20;

var ASTElem.line: Int by ASTDelegate()
var ASTElem.column: Int by ASTDelegate()

fun <T: ASTElem>T.setLineColumn(line: Int, column: Int): T {
    this.line = line
    this.column = column
    return this
}


fun parseVerificationUnit(sexps: List<SExp>): VerificationUnit {
    if (sexps.size == 0) throw MissingHeaderException();

    val header = sexps[0];
    if (header !is ParenSExp || header.children.size <= 1 || header.children[0] !is TokenSExp
            || (header.children[0] as TokenSExp).value != "verification-unit") {
        throw MissingHeaderException()
    }

    val nameSExp = header.children[1]
    val vuName = if (nameSExp is TokenSExp) nameSExp.value else throw InvalidHeaderNoName()

    val defs = sexps.subList(1, sexps.size).map(::parseTopLevelFunctionDefinition)

    return VerificationUnit(vuName,
            emptyMap(),
            emptySet(), emptySet(), emptySet(), emptySet(),
            emptySet(), emptySet(), emptySet(), emptySet(),
            defs)
}

fun parseTopLevelFunctionDefinition(sexp: SExp): FunctionDefinition {
    val sexp = expectCompound(sexp, 5, 6)
    expectKeyword(sexp.children[0], "define")

    val newSExp = ParenSExp(sexp.line, sexp.column, sexp.children.subList(1, sexp.children.size))

    return parseFunctionDefinition(newSExp)
}


fun parseFunctionDefinition(sexp: SExp): FunctionDefinition {
    val sexp = expectCompound(sexp, 4, 5)

    val name = expectSimple(sexp.children[0]);
    val inputParams = expectCompound(sexp.children[1]);
    val outputParams = expectCompound(sexp.children[2]);
    val body = sexp.children[sexp.children.size - 1];

    fun lookForAssertion(str: String, sexp: SExp) : Assertion? {
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

    val precondition = if (sexp.children.size == 5) lookForAssertion("precd", sexp.children[3]) else null
    val postcondition = if (sexp.children.size == 5) lookForAssertion("postcd", sexp.children[3]) else null


    return FunctionDefinition(
            name.value,
            inputParams.children.map(::parseTypedVar),
            outputParams.children.map(::parseTypedVar),
            parseExpression(body),
            precondition ?: False(),
            postcondition ?: True()
    ).setLineColumn(sexp.line, sexp.column)
}

fun parseHMType(sexp: SExp) : HMType = when {
    sexp is TokenSExp && sexp.value.startsWith("'") -> VarType(sexp.value).setLineColumn(sexp.line, sexp.column)
    sexp is TokenSExp -> ConstrType(sexp.value).setLineColumn(sexp.line, sexp.column)
    sexp is ParenSExp &&  sexp.children.size > 0 -> {
        val constructor = expectSimple(sexp.children[0])
        ConstrType(constructor.value, sexp.children.subList(1, sexp.children.size).map(::parseHMType)).setLineColumn(sexp.line, sexp.column)
    }
    else -> throw UnknownType(sexp.line, sexp.column)
}

fun parseType(sexp: SExp) : Type = when {
    sexp is ParenSExp && sexp.children.size == 4 && sexp.children[0] is TokenSExp && (sexp.children[0] as TokenSExp).value == "qual" -> {
        val nuVar = expectSimple(sexp.children[1])
        val hmType = parseHMType(sexp.children[2])
        val assertion = parseAssertion(sexp.children[3])
        QualType(nuVar.value, hmType, assertion).setLineColumn(sexp.line, sexp.column)
    }
    else -> parseHMType(sexp)
}


fun parseTypedVar(sexp: SExp) : TypedVar {
    val sexp = expectCompound(sexp, 2)
    val name = expectSimple(sexp.children[0])
    val type = parseType(sexp.children[1])
    return TypedVar(name.value, type).setLineColumn(sexp.line, sexp.column)
}

fun parseHMTypedVar(sexp: SExp) : HMTypedVar {
    val sexp = expectCompound(sexp, 2)
    val name = expectSimple(sexp.children[0])
    val type = parseHMType(sexp.children[1])
    return HMTypedVar(name.value, type).setLineColumn(sexp.line, sexp.column)
}


fun parseAssertion(sexp: SExp): Assertion = when(sexp) {
    is TokenSExp -> when(sexp.value) {
        "true" -> True().setLineColumn(sexp.line, sexp.column)
        "false" -> False().setLineColumn(sexp.line, sexp.column)
        else -> throw IncorrectAssertion(sexp)
    }

    is ParenSExp -> {
        expectCompoundGreaterOrEqual(sexp, 1)
        val keyword = expectSimple(sexp.children[0]).value
        when(keyword) {
            "@" -> {
                expectCompoundGreaterOrEqual(sexp, 2)
                val predName = expectSimple(sexp.children[1]).value
                val arguments = sexp.children.subList(2, sexp.children.size).map(::parseBindingExpression)
                PredicateApplication(predName, arguments).setLineColumn(sexp.line, sexp.column)
            }

            "and" -> {
                And(sexp.children.subList(1, sexp.children.size).map(::parseAssertion)).setLineColumn(sexp.line, sexp.column)
            }

            "or" -> {
                Or(sexp.children.subList(1, sexp.children.size).map(::parseAssertion)).setLineColumn(sexp.line, sexp.column)
            }

            "not" -> {
                expectCompound(sexp, 2)
                Not(parseAssertion(sexp.children[1])).setLineColumn(sexp.line, sexp.column)
            }

            "->" -> {
                Implication(sexp.children.subList(1, sexp.children.size).map(::parseAssertion)).setLineColumn(sexp.line, sexp.column)
            }

            "<->" -> {
                Iff(sexp.children.subList(1, sexp.children.size).map(::parseAssertion)).setLineColumn(sexp.line, sexp.column)
            }

            "forall" -> {
                expectCompound(sexp, 3)
                val typedVars = expectCompound(sexp.children[1])
                val boundVars = typedVars.children.map(::parseHMTypedVar)
                val assertion = parseAssertion(sexp.children[2])
                ForAll(boundVars, assertion).setLineColumn(sexp.line, sexp.column)
            }

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

fun parseAtomicExpression(sexp: SExp): Atomic = when(sexp) {
    is ParenSExp -> {
        expectCompoundGreaterOrEqual(sexp, 1)
        expectKeyword(sexp.children[0], "the")
        expectCompound(sexp, 3)
        val type = parseHMType(sexp.children[1])
        val literal = expectSimple(sexp.children[2])
        Literal(literal.value, type).setLineColumn(sexp.line, sexp.column)
    }

    is TokenSExp -> {
        Variable(sexp.value).setLineColumn(sexp.line, sexp.column)
    }

    else -> throw IncorrectExpression(sexp)
}

fun parseBindingExpression(sexp: SExp): BindingExpression = when(sexp) {
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

fun parseExpression(sexp: SExp) : Term = when(sexp) {
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

                Case(Variable(discriminant), branches, defaultBranch).setLineColumn(sexp.line, sexp.column)
            }
            else -> parseBindingExpression(sexp)
        }
    }
    else -> parseAtomicExpression(sexp)
}

fun  parsePattern(sexp: SExp): Pattern = when (sexp) {
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

fun expectKeyword(sexp: SExp, keyword: String) {
    if (sexp !is TokenSExp) {
        throw UnexpectedKeyword(sexp.line, sexp.column, sexp.toString().take(30), null)
    }

    if (sexp.value != keyword) {
        throw UnexpectedKeyword(sexp.line, sexp.column, sexp.value, keyword)
    }
}

fun expectCompound(sexp: SExp, vararg numChildren: Int) : ParenSExp {
    if (sexp !is ParenSExp) {
        throw ExpectedCompound(sexp)
    }

    if (numChildren.size > 0 && sexp.children.size !in numChildren) {
        throw WrongArguments(sexp.line, sexp.column, sexp.trim(), sexp.children.size, numChildren.toList())
    }

    return sexp
}

fun expectCompoundGreaterOrEqual(sexp: SExp, minimum: Int) : ParenSExp {
    if (sexp !is ParenSExp) {
        throw ExpectedCompound(sexp)
    }

    if (sexp.children.size < minimum) {
        throw FewArguments(sexp.line, sexp.column, sexp.trim(), sexp.children.size, minimum)
    }

    return sexp

}
fun expectSimple(sexp: SExp) : TokenSExp {
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

class MissingHeaderException : Exception("Missing 'verification-unit' header");
class InvalidHeaderNoName : Exception("Invalid 'verification-unit' header. No varName given");


class ExpectedCompound(given: SExp) :
        Exception("In L${given.line}, C${given.column}: expected compound S-Expression, but found ${given.trim()}")
class ExpectedSimple(given: SExp) :
        Exception("In L${given.line}, C${given.column}: unexpected compound S-Expression, but found ${given.trim()}")
class WrongArguments(line: Int, column: Int, name: String, given: Int, expected: List<Int>) :
        Exception("In L$line, C$column: wrong number of arguments ($given) given to $name. Expected ${expected.joinToString(" or ")}")
class FewArguments(line: Int, column: Int, name: String, given: Int, minimum: Int) :
        Exception("In L$line, C$column: wrong number of arguments ($given) given to $name. Expected $minimum or more")
class UnexpectedKeyword(line: Int, column: Int, given: String, expected: String?) :
        Exception("In L$line, C$column: unexpected '$given'${if (expected != null) ". Expected $expected" else ""}")

class IncorrectExpression(given: SExp) :
        Exception("In L${given.line}, C${given.column}: incorrect expression: ${given.trim()}")

class IncorrectPattern(given: SExp) :
        Exception("In L${given.line}, C${given.column}: incorrect pattern: ${given.trim()}")

class IncorrectAssertion(given: SExp) :
        Exception("In L${given.line}, C${given.column}: incorrect assertion: ${given.trim()}")

class UnknownType(line: Int, column: Int) :
        Exception("In L$line C$column: expected type")