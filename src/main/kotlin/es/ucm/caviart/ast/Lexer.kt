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

/*
 * The following constants represent special characters
 * in CLIR source code files.
 */
private const val LEFT_PAREN = '('.toInt()
private const val RIGHT_PAREN = ')'.toInt()
private const val QUOTE = '"'.toInt()
private const val LF = '\n'.toInt()
private const val CR = '\r'.toInt()
private const val SEMICOLON = ';'.toInt()
private const val UTF8_BOM = 0xFEFF


/**
 * Instances of this class represent a token of a CLIR file.
 *
 * @property line Line number of this token (starting from 1)
 * @property column Column number of this token (starting from 1)
 */
abstract class Token(val line: Int, val column: Int)

/**
 * Token specifying a left parenthesis symbol.
 *
 * @param line Line number of this token (starting from 1)
 * @param column Column number of this token (starting from 1)
 */
class LeftParen(line: Int, column: Int) : Token(line, column) {
    /**
     * String representation of the token
     */
    override fun toString(): String {
        return "LeftParen($line, $column)"
    }

}

/**
 * Token specifying a right parenthesis symbol.
 *
 * @param line Line number of this token (starting from 1)
 * @param column Column number of this token (starting from 1)
 */
class RightParen(line: Int, column: Int) : Token(line, column) {
    /**
     * String representation of the token
     */
    override fun toString(): String {
        return "RightParen($line, $column)"
    }
}

/**
 * Token specifying a literal symbol (a sequence of characters different from parenthesis or quotes)
 *
 * @param line Line number of this token (starting from 1)
 * @param column Column number of this token (starting from 1)
 */
class TokenLiteral(line: Int, column: Int, val value: String) : Token(line, column) {
    /**
     * String representation of the token
     */
    override fun toString(): String {
        return "TokenLiteral($line, $column, \"$value\")"
    }
}

/**
 * It returns the sequence of tokens appearing in the given input stream.
 *
 * @param reader Input stream to examine
 * @return List of [Token] instances.
 */
fun readTokens(reader: Reader): List<Token> {
    val result = mutableListOf<Token>()

    // Last character read
    var currentChar = reader.read()

    // Current word (sequence of characters separated by spaces)
    var currentWord = StringBuffer()

    // If we are inside a quoted string
    var betweenQuotes = false

    // Line number of the last character read
    var line = 1

    // Column number of the last character read
    var column = 1

    // Column number in which the current token starts
    var beginningColumnToken = 1

    // Whether the previous character was a CR symbol
    var lastCR = false

    // Whether the previous character was a semicolon (we need two in a row to enable comment mode)
    var lastSemicolon = false

    // Whether we are in a comment
    var comment = false

    // If the first character of the file is a BOM mark, we simply
    // discard it
    if (currentChar == UTF8_BOM) currentChar = reader.read()





    while (currentChar != -1) {
        when {

            // If we are in a comment, we skip this part
            comment -> {
                /* do nothing */
            }

            // If we are inside a quoted string, we add the current character to the current word
            // or finish the word, if the character is a quote mark

            betweenQuotes && currentChar != QUOTE ->
                currentWord.appendCodePoint(currentChar)

            betweenQuotes && currentChar == QUOTE -> {
                result.add(TokenLiteral(line, beginningColumnToken, currentWord.toString()))
                currentWord = StringBuffer()
                betweenQuotes = false
            }


            // If we have two semicolons in a row, we start a comment until
            // the next line

            lastSemicolon && currentChar == SEMICOLON -> {
                if (currentWord.endsWith(";")) {
                    currentWord.deleteCharAt(currentWord.length - 1)
                }

                if (!currentWord.isEmpty()) {
                    result.add(TokenLiteral(line, beginningColumnToken, currentWord.toString()))
                    currentWord = StringBuffer()
                }

                comment = true
            }



            // Starting quoted string, if we are not within a word.

            currentChar == QUOTE && !betweenQuotes -> {
                if (currentWord.isEmpty()) {
                    betweenQuotes = true
                    beginningColumnToken = column
                } else {
                    throw UnexpectedQuoteException(line, column)
                }
            }

            // A whitespace character finishes the current word

            Character.isWhitespace(currentChar) -> {
                if (!currentWord.isEmpty()) {
                    result.add(TokenLiteral(line, beginningColumnToken, currentWord.toString()))
                    currentWord = StringBuffer()
                }
            }

            // A left or right parenthesis finishes the current word and adds the corresponding token

            currentChar == LEFT_PAREN -> {
                if (!currentWord.isEmpty()) {
                    result.add(TokenLiteral(line, beginningColumnToken, currentWord.toString()))
                    currentWord = StringBuffer()
                }
                result.add(LeftParen(line, column))
            }

            currentChar == RIGHT_PAREN -> {
                if (!currentWord.isEmpty()) {
                    result.add(TokenLiteral(line, beginningColumnToken, currentWord.toString()))
                    currentWord = StringBuffer()
                }
                result.add(RightParen(line, column))
            }

            // Otherwise, we add the character to the current word

            else -> {
                if (currentWord.isEmpty()) {
                    beginningColumnToken = column
                }
                currentWord.appendCodePoint(currentChar)
            }
        }

        // We advance the line and column counters if necessary
        when {
            currentChar == CR -> {
                line++
                column = 1
                comment = false
            }

            currentChar == LF && !lastCR -> {
                line++
                column = 1
                comment = false
            }

            else -> {
                column++
            }
        }

        // We set the auxiliary variables based on the last character
        lastCR = false
        lastSemicolon = false
        when (currentChar) {
            CR -> lastCR = true
            SEMICOLON -> lastSemicolon = true
        }

        currentChar = reader.read()
    }

    if (!currentWord.isEmpty()) {
        result.add(TokenLiteral(line, beginningColumnToken, currentWord.toString()))
    }

    return result
}

/**
 * Exception thrown when a quote symbol follows directly a token symbol. For example, as in `hello"symb"`.
 *
 * There must be a space in between, as in `hello "symb"`.
 */
class UnexpectedQuoteException(val line: Int, val column: Int) : LiquidException("Unexpected quote symbol: line $line, column $column")