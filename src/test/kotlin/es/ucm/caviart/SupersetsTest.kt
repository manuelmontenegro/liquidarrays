package es.ucm.caviart

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Created by manuel on 5/07/17.
 */

class SupersetsTest {

    @Test fun superSetsOf() {
        val (first, iterator) = iterateSupersetsOf(setOf(1, 2, 4), setOf(1, 2, 3, 4, 5))

        assertEquals(setOf(1, 2, 4, 3), first)
        assertEquals(setOf(1, 2, 4, 5), iterator.next(true))
        assertEquals(setOf(1, 2, 4, 3, 5), iterator.next(true))
        assertNull(iterator.next(true))
    }

    @Test fun neverSuperSets() {
        val (first, iterator) = iterateSupersetsOf(setOf(1, 2, 4), setOf(1, 2, 3, 4, 5, 6))

        assertEquals(setOf(1, 2, 4, 3), first)
        assertEquals(setOf(1, 2, 4, 5), iterator.next(false))
        assertEquals(setOf(1, 2, 4, 6), iterator.next(false))
        assertNull(iterator.next(false))
    }

    @Test fun sometimesSuperSets() {
        val (first, iterator) = iterateSupersetsOf(setOf(1, 2, 4), setOf(1, 2, 3, 4, 5, 6))

        assertEquals(setOf(1, 2, 4, 3), first)
        assertEquals(setOf(1, 2, 4, 5), iterator.next(false))
        assertEquals(setOf(1, 2, 4, 6), iterator.next(true))
        assertEquals(setOf(1, 2, 4, 5, 6), iterator.next(true))
        assertNull(iterator.next(true))
    }

    @Test fun needsFiltering() {
        val (first, iterator) = iterateSupersetsOf(setOf(1, 2), setOf(3, 4, 5))
        assertEquals(setOf(1, 2, 3), first)
        assertEquals(setOf(1, 2, 4), iterator.next(true))
        assertEquals(setOf(1, 2, 5), iterator.next(true))
        assertEquals(setOf(1, 2, 3, 4), iterator.next(true))
        assertEquals(setOf(1, 2, 3, 5), iterator.next(true))
        assertEquals(setOf(1, 2, 4, 5), iterator.next(false))
        assertNull(iterator.next(false))
    }
}