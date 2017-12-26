package es.ucm.caviart

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

    @Test fun Quotes() {
        val tokens = readTokens(StringReader("(\"hello other\" guy)"))
        assertEquals(4, tokens.size, "Four tokens")
        assertEqualsToken(LeftParen(1, 1), tokens[0])
        assertEqualsToken(TokenLiteral(1, 2, "hello other"), tokens[1])
        assertEqualsToken(TokenLiteral(1, 16, "guy"), tokens[2])
        assertEqualsToken(RightParen(1, 19), tokens[3])
    }

    @Test fun QuotesAfterToken() {
        try {
            readTokens(StringReader("(d\"hello other\" guy)"))
            fail("No quote allowed after literal token")
        } catch (e : UnexpectedQuoteException) {
            assertEquals(1, e.line)
            assertEquals(3, e.column)
        }

    }
}