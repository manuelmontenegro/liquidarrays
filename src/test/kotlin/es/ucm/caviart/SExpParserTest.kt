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
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class SExpParserTest {
    private fun assertEqualsSExp(sexp1: SExp, sexp2: SExp) {
        assertEquals(sexp1.toString(), sexp2.toString())
    }

    @Test fun emptySExp() {
        val sexps = getSExps("")
        assertEquals(0, sexps.size, "The list of S-Exps should be empty given an empty string")
    }

    @Test fun singletonSExp() {
        val sexps = getSExps("pepe")
        assertEquals(1, sexps.size, "The list of S-Exps should be of size one")
        assertEqualsSExp(TokenSExp(1, 1, "pepe"), sexps[0])
    }

    @Test fun parenSExp() {
        val sexps = getSExps("(pepe 4 5)")
        assertEquals(1, sexps.size, "The list of S-Exps should be of size one")
        assertEqualsSExp(
                ParenSExp(1, 1,
                        TokenSExp(1, 2, "pepe"),
                        TokenSExp(1, 7, "4"),
                        TokenSExp(1, 9, "5")
                ), sexps[0])
    }

    @Test fun nestedSExps() {
        val sexps = getSExps("(pepe 4 \n(let x 1) 5 (let 2))")
        assertEquals(1, sexps.size, "The list of S-Exps should be of size one")
        assertEqualsSExp(
                ParenSExp(1, 1,
                        TokenSExp(1, 2, "pepe"),
                        TokenSExp(1, 7, "4"),
                        ParenSExp(2, 1,
                                TokenSExp(2, 2, "let"),
                                TokenSExp(2, 6, "x"),
                                TokenSExp(2, 8, "1")
                        ),
                        TokenSExp(2, 11, "5"),
                        ParenSExp(2, 13,
                                TokenSExp(2, 14, "let"),
                                TokenSExp(2, 18, "2")
                        )
                ), sexps[0])
    }

    @Test fun twoSExps() {
        val sexps = getSExps("() (())")
        assertEquals(2, sexps.size, "The list of S-Exps should be of size one")
        assertEqualsSExp(ParenSExp(1, 1), sexps[0])
        assertEqualsSExp(ParenSExp(1, 4, ParenSExp(1, 5)), sexps[1])
    }

    @Test fun unbalancedExpRight() {
        try {
            getSExps("())")
            fail("Unbalanced right parenthesis must throw an exception")
        } catch (e: Exception) {
            assertTrue(e is UnexpectedRightParenException)
            assertEquals(1, (e as UnexpectedRightParenException).line)
            assertEquals(3, e.column)
        }
    }

    @Test fun unbalancedExpLeft() {
        try {
            getSExps("() (()")
            fail("Unbalanced left parenthesis must throw an exception")
        } catch (e: Exception) {
            assertTrue(e is UnmatchedLeftParenException)
            assertEquals(1, (e as UnmatchedLeftParenException).line)
            assertEquals(4, e.column)
        }
    }
}