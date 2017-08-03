package es.ucm.caviart

import java.io.Reader

abstract class Token(val line: Int, val column: Int)

class LeftParen(line: Int, column: Int) : Token(line, column) {
    override fun equals(other: Any?): Boolean {
        return other is LeftParen && other.line == line && other.column == column
    }

    override fun toString(): String {
        return "LeftParen($line, $column)"
    }
}

class RightParen(line: Int, column: Int) : Token(line, column) {
    override fun equals(other: Any?): Boolean {
        return other is RightParen && other.line == line && other.column == column
    }

    override fun toString(): String {
        return "RightParen($line, $column)"
    }
}

class TokenLiteral(line: Int, column: Int, val value: String) : Token(line, column) {
    override fun equals(other: Any?): Boolean {
        return other is TokenLiteral && other.line == line && other.column == column && other.value == value
    }

    override fun toString(): String {
        return "TokenLiteral($line, $column, \"$value\")"
    }
}

val LEFT_PAREN = '('.toInt()
val RIGHT_PAREN = ')'.toInt()
val QUOTE = '"'.toInt()
val LF = '\n'.toInt()
val CR = '\r'.toInt()
val UTF8_BOM = 0xFEFF

fun readTokens(reader: Reader): List<Token> {
    val result = mutableListOf<Token>()

    var currentChar = reader.read()
    var currentWord = StringBuffer()
    var betweenQuotes = false
    var line = 1
    var column = 1
    var beginningColumnToken = 1
    var lastCR = false

    if (currentChar == UTF8_BOM) currentChar = reader.read()

    while (currentChar != -1) {
        when {
            currentChar != QUOTE && betweenQuotes ->
                currentWord.appendCodePoint(currentChar)

            currentChar == QUOTE && betweenQuotes -> {
                result.add(TokenLiteral(line, beginningColumnToken, currentWord.toString()))
                currentWord = StringBuffer()
                betweenQuotes = false
            }

            currentChar == QUOTE && !betweenQuotes -> {
                if (currentWord.isEmpty()) {
                    betweenQuotes = true
                    beginningColumnToken = column
                } else {
                    throw UnexpectedQuoteException(line, column)
                }
            }

            Character.isWhitespace(currentChar) -> {
                if (!currentWord.isEmpty()) {
                    result.add(TokenLiteral(line, beginningColumnToken, currentWord.toString()))
                    currentWord = StringBuffer()
                }
            }

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

            else -> {
                if (currentWord.isEmpty()) {
                    beginningColumnToken = column
                }
                currentWord.appendCodePoint(currentChar)
            }
        }

        when {
            currentChar == CR -> {
                line++
                column = 1
                lastCR = true
            }

            currentChar == LF && !lastCR -> {
                line++
                column = 1
                lastCR = false
            }

            else -> {
                column++
                lastCR = false
            }
        }

        currentChar = reader.read()
    }

    if (!currentWord.isEmpty()) {
        result.add(TokenLiteral(line, beginningColumnToken, currentWord.toString()))
    }

    return result
}

class UnexpectedQuoteException(val line: Int, val column: Int): Exception("Unexpected quote symbol: line $line, column $column")