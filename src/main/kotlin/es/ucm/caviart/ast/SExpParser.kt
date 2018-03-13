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

import es.ucm.caviart.utils.LiquidException
import java.io.Reader
import java.io.StringReader
import java.util.*

/**
 * Instances of this class represent S-Expressions, which can be single tokens
 * ([TokenSExp]) or sequences of S-Expressions surrended by parentheses ([ParenSExp])
 *
 * @property line Line in which the S-Expression starts
 * @property column column in which the S-Expression starts
 */
abstract class SExp(val line: Int, val column: Int) {
    /**
     * It returns an indented representation of a S-expression, assuming that
     * the current cursor position is `margin` and that each line of the result
     * must not have more than `maxWidth - margin` characters.
     */
    abstract fun prettyPrint(margin: Int, maxWidth: Int = 80): String

    /**
     * It returns an indented representation of a S-expression, in which each
     * line of the result does not have more than `margin` characters.
     *
     * @param maxWidth Maximum number of characters per line
     * @return Pretty-printed representation of the string.
     */
    fun prettyPrint(maxWidth: Int = 80): String = prettyPrint(0, maxWidth)
}


/**
 * Representation of atomic S-Expressions (i.e. a single token)
 *
 * @param line Line in which the S-Expression starts
 * @param column column in which the S-Expression starts
 * @property value Text of the atomic expression
 */
class TokenSExp(line: Int, column: Int, val value: String) : SExp(line, column) {
    override fun prettyPrint(margin: Int, maxWidth: Int): String = this.toString()

    /**
     * It returns a string representation of an S-expression
     */
    override fun toString(): String =
            if (value.contains(Regex("\\s"))) "\"$value\"" else value

}


/**
 * Representation of compound S-Expressions
 *
 * @param line Line in which the S-Expression starts (left parenthesis)
 * @param column column in which the S-Expression starts (right parenthesis)
 * @property children S-Expressions contained within the `this` object
 *
 */
class ParenSExp(line: Int, column: Int, val children: List<SExp>) : SExp(line, column) {
    override fun prettyPrint(margin: Int, maxWidth: Int): String {
        if (children.isEmpty()) {
            return "()"
        } else {
            return buildString {
                // Whether we have to insert a new line before the next expression
                var insertNLBefore = false
                // Whether the next expression to print will be the first in its line
                var isFirstInCurrentLine = true
                // The next expression to print
                var nextExpression = ""
                // The number of characters remaining in the current line
                var spaceRemaining = maxWidth - (margin + 1)
                append("(")
                children.forEach {
                    if (insertNLBefore) {
                        append("\n" + " ".repeat(margin + 2))
                    }
                    append(nextExpression)

                    // We assign the parameters for the next expression to be printed
                    val pprinted = it.prettyPrint(margin + 2, maxWidth)
                    if (!pprinted.contains("\n") && pprinted.length <= spaceRemaining) {
                        // It fits in the current line
                        if (!isFirstInCurrentLine) {
                            append(" ")
                        } else {
                            isFirstInCurrentLine = false
                        }
                        insertNLBefore = false
                        spaceRemaining = spaceRemaining - 2 - pprinted.length
                    } else {
                        // It does not fit in the current line
                        insertNLBefore = true
                        isFirstInCurrentLine = true
                        spaceRemaining = margin + 2
                    }
                    nextExpression = pprinted
                }
                if (insertNLBefore) {
                    append("\n" + " ".repeat(margin + 2))
                }
                append(nextExpression)
                append(")")
            }
        }
    }

    constructor (line: Int, column: Int, vararg children: SExp) : this(line, column, children.toList())

    /**
     * It returns a string  representation of an S-expression
     */
    override fun toString(): String {
        return "(${children.joinToString(" ")})"
    }
}


/**
 * It transforms a list of tokens into a list of S-Expressions
 *
 * @param tokens List of instances of [Token] from which the S-expressions will be built
 * @return List of S-expressions
 */
fun getSExps(tokens: List<Token>): List<SExp> {

    // We will have a stack with all currently open S-expressions.
    //
    // We assume that all the S-expressions parsed will be contained within a
    // toplevel S-expression, from which the children will be extracted at the end.
    data class StackElement(val sExps: MutableList<SExp>, val line: Int, val column: Int)

    val stack = Stack<StackElement>()
    stack.push(StackElement(mutableListOf(), 1, 1))


    for (token in tokens) {
        when (token) {
        // A left parenthesis starts a new open S-expression
            is LeftParen -> {
                stack.push(StackElement(mutableListOf(), token.line, token.column))
            }

        // A right parenthesis ends the current S-expression and adds it to
        // the children its parent.
            is RightParen -> {
                val element = stack.pop()
                if (stack.empty()) {
                    throw UnexpectedRightParenException(token.line, token.column)
                } else {
                    stack.peek().sExps.add(ParenSExp(element.line, element.column, element.sExps))
                }
            }

        // A single token is inserted into the innermost currently open S-expression
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

/**
 * It transforms a character input stream into a list of S-expressions. It uses [readTokens] to
 * transform the stream into a list of tokens previously.
 *
 * @param reader Sequence of characters from which the result will be built.
 * @return List of S-expressions
 */
fun getSExps(reader: Reader): List<SExp> = getSExps(readTokens(reader))

/**
 * The same as [getSExps(Reader)], but it receives a String instead

 *
 * @param string String from which the result will be built.
 * @return List of S-expressions
 */
fun getSExps(string: String): List<SExp> = getSExps(StringReader(string))

/**
 * Exception that will be thrown when an unmatched right parenthesis is found, as in `((hello)))`.
 */
class UnexpectedRightParenException(val line: Int, val column: Int) : LiquidException("Unexpected ')': line $line, column $column")

/**
 * Exception that will be thrown when we reach the end of the file and there are left parenthesis to be closed.
 */
class UnmatchedLeftParenException(val line: Int, val column: Int) : LiquidException("Unmatched '(': line $line, column $column")
