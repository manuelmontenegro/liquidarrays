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
package es.ucm.caviart

import es.ucm.caviart.ast.*
import org.junit.Test
import java.io.StringReader
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class LexerTest {

    private fun assertEqualsToken(t1: Token, t2: Token) {
        assertEquals(t1.toString(), t2.toString())
    }

    @Test fun emptyString() {
        val tokens = readTokens(StringReader(""))

        assertTrue(tokens.isEmpty(), "No tokens are generated")
    }

    @Test fun singleToken() {
        val tokens = readTokens(StringReader("token"))

        assertEquals(1, tokens.size, "Single token")
        assertEqualsToken(TokenLiteral(1, 1, "token"), tokens[0])
    }

    @Test fun twoTokens() {
        val tokens = readTokens(StringReader("val 3"))

        assertEquals(2, tokens.size, "Two tokens")
        assertEqualsToken(TokenLiteral(1, 1, "val"), tokens[0])
        assertEqualsToken(TokenLiteral(1, 5, "3"), tokens[1])
    }

    @Test fun threeTokensNewLine() {
        val tokens = readTokens(StringReader("val 45\n 54"))

        assertEquals(3, tokens.size, "Three tokens")
        assertEqualsToken(TokenLiteral(1, 1, "val"), tokens[0])
        assertEqualsToken(TokenLiteral(1, 5, "45"), tokens[1])
        assertEqualsToken(TokenLiteral(2, 2, "54"), tokens[2])
    }

    @Test fun parens() {
        val tokens = readTokens(StringReader("f((d\n) ("))
        assertEquals(6, tokens.size, "Six tokens")
        assertEqualsToken(TokenLiteral(1, 1, "f"), tokens[0])
        assertEqualsToken(LeftParen(1, 2), tokens[1])
        assertEqualsToken(LeftParen(1, 3), tokens[2])
        assertEqualsToken(TokenLiteral(1, 4, "d"), tokens[3])
        assertEqualsToken(RightParen(2, 1), tokens[4])
        assertEqualsToken(LeftParen(2, 3), tokens[5])
    }

    @Test fun parensWithSpaces() {
        val tokens = readTokens(StringReader("f ( ( \n d \n ) ("))
        assertEquals(6, tokens.size, "Six tokens")
        assertEqualsToken(TokenLiteral(1, 1, "f"), tokens[0])
        assertEqualsToken(LeftParen(1, 3), tokens[1])
        assertEqualsToken(LeftParen(1, 5), tokens[2])
        assertEqualsToken(TokenLiteral(2, 2, "d"), tokens[3])
        assertEqualsToken(RightParen(3, 2), tokens[4])
        assertEqualsToken(LeftParen(3, 4), tokens[5])
    }

    @Test fun onlyParens() {
        val tokens = readTokens(StringReader(" (( ) ( )"))
        assertEquals(5, tokens.size, "Five tokens")
        assertEqualsToken(LeftParen(1, 2), tokens[0])
        assertEqualsToken(LeftParen(1, 3), tokens[1])
        assertEqualsToken(RightParen(1, 5), tokens[2])
        assertEqualsToken(LeftParen(1, 7), tokens[3])
        assertEqualsToken(RightParen(1, 9), tokens[4])
    }

    @Test fun quotes() {
        val tokens = readTokens(StringReader("(\"hello other\" guy)"))
        assertEquals(4, tokens.size, "Four tokens")
        assertEqualsToken(LeftParen(1, 1), tokens[0])
        assertEqualsToken(TokenLiteral(1, 2, "hello other"), tokens[1])
        assertEqualsToken(TokenLiteral(1, 16, "guy"), tokens[2])
        assertEqualsToken(RightParen(1, 19), tokens[3])
    }

    @Test fun quotesAfterToken() {
        try {
            readTokens(StringReader("(d\"hello other\" guy)"))
            fail("No quote allowed after literal token")
        } catch (e : UnexpectedQuoteException) {
            assertEquals(1, e.line)
            assertEquals(3, e.column)
        }
    }

    @Test fun ignoredComments() {
        val tokens = readTokens(StringReader(";; This is a comment"))
        assertEquals(0, tokens.size, "A single comment has no tokens")
    }

    @Test fun singleSemicolonNoComment() {
        val tokens = readTokens(StringReader("; This is NOT a comment"))
        assertEquals(6, tokens.size, "A single semicolon is not a comment")
    }

    @Test fun tokenBeforeComment() {
        val tokens = readTokens(StringReader("bubu;; This is a comment"))
        assertEquals(1, tokens.size, "Token before comment")
        assertEqualsToken(TokenLiteral(1, 1, "bubu"), tokens[0])
    }

    @Test fun tokenAfterComment() {
        val tokens = readTokens(StringReader(";; This is a comment\nbubu"))
        assertEquals(1, tokens.size, "Token after comment")
        assertEqualsToken(TokenLiteral(2, 1, "bubu"), tokens[0])
    }

    @Test fun tokenBeforeAndAfterComment() {
        val tokens = readTokens(StringReader("baba;; This is a comment\nbubu"))
        assertEquals(2, tokens.size, "Token after comment")
        assertEqualsToken(TokenLiteral(1, 1, "baba"), tokens[0])
        assertEqualsToken(TokenLiteral(2, 1, "bubu"), tokens[1])
    }
}