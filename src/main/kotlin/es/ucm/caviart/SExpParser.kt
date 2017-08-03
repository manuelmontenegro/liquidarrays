package es.ucm.caviart

import java.io.Reader
import java.io.StringReader
import java.util.*

abstract class SExp(val line: Int, val column: Int)

class TokenSExp(line: Int, column: Int, val value: String): SExp(line, column) {
    override fun equals(other: Any?): Boolean {
        return other is TokenSExp && other.line == line && other.column == column && other.value == value
    }

    override fun toString(): String =
        if (value.contains(Regex("\\s"))) "\"$value\"" else value

    override fun hashCode(): Int {
        return value.hashCode()
    }

}

class ParenSExp(line: Int, column: Int, val children: List<SExp>): SExp(line, column) {
    constructor (line: Int, column: Int, vararg children: SExp) : this(line, column, children.toList())

    override fun equals(other: Any?): Boolean {
        return other is ParenSExp && other.line == line && other.column == column && other.children == children
    }

    override fun toString(): String {
        return "(${children.joinToString(" ")})"
    }

    override fun hashCode(): Int {
        return children.hashCode()
    }
}


data class StackElement(val sExps: MutableList<SExp>, val line: Int, val column: Int)

fun getSExps(tokens: List<Token>): List<SExp> {
    val stack = Stack<StackElement>()
    stack.push(StackElement(mutableListOf(), 1, 1))

    for (token in tokens) {
        when (token) {
            is LeftParen -> {
                stack.push(StackElement(mutableListOf(), token.line, token.column))
            }

            is RightParen -> {
                val element = stack.pop()
                if (stack.empty()) {
                    throw UnexpectedRightParenException(token.line, token.column)
                } else {
                    stack.peek().sExps.add(ParenSExp(element.line, element.column, element.sExps))
                }
            }

            is TokenLiteral -> {
                stack.peek().sExps.add(TokenSExp(token.line, token.column, token.value))
            }
        }
    }

    assert(stack.size >= 1)

    if (stack.size > 1) {
        val element = stack.peek()
        throw UnmatchedLeftParenException(element.line, element.column)
    } else {
        return stack.peek().sExps
    }
}

fun getSExps(reader: Reader): List<SExp> = getSExps(readTokens(reader))

fun getSExps(string: String): List<SExp> = getSExps(StringReader(string))

class UnexpectedRightParenException(val line: Int, val column: Int) : Exception("Unexpected ')': line $line, column $column")
class UnmatchedLeftParenException(val line: Int, val column: Int) : Exception("Unmatched '(': line $line, column $column")
