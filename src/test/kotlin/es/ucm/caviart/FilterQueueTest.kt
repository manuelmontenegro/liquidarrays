package es.ucm.caviart

import es.ucm.caviart.iterativeweakening.FilterQueue
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by manuel on 6/07/17.
 */

class FilterQueueTest {
    @Test fun emptyQueue() {
        val queue = FilterQueue<Int>()
        assertTrue(queue.empty, "queue.empty must be true for the empty queue")
    }

    @Test fun nonemptyQueue() {
        val queue = FilterQueue<Int>()
        queue.enqueue(3)
        assertTrue(!queue.empty, "queue.empty must be false after enqueing")
    }

    @Test fun emptyQueueAfterEnqDeq() {
        val queue = FilterQueue<Int>()
        queue.enqueue(3)
        queue.dequeue()
        assertTrue(queue.empty, "queue.empty must be true after enqueing and dequeing")
    }

    @Test fun firstAfterEnqueue() {
        val queue = FilterQueue<Int>()
        queue.enqueue(1)
        assertEquals(1, queue.first, "First element after enqueing 1 must be 1")
    }

    @Test fun firstAfterEnqueueTwo() {
        val queue = FilterQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        assertEquals(1, queue.first, "First element after enqueing 1 and 2 must be 1")
    }

    @Test fun firstAfterEnqueueTwoAndDequeue() {
        val queue = FilterQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        val dequeued = queue.dequeue()
        assertEquals(1, dequeued, "Dequeued element must be 1")
        assertEquals(2, queue.first, "First element after enqueing 1 and 2 and dequeueing must be 2")
    }

    @Test fun queueAfterDequeue() {
        val queue = FilterQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        val deq1 = queue.dequeue()
        assertEquals(1, deq1, "Dequeued element must be 1")
        queue.enqueue(3)
        assertEquals(2, queue.first, "First element must be 2")
        val deq2 = queue.dequeue()
        assertEquals(2, deq2, "Dequeued element must be 2")
        assertEquals(3, queue.first, "First element must be 3")
        assertFalse(queue.empty, "After dequeuing two, queue must not be empty")
        queue.dequeue()
        assertTrue(queue.empty, "After dequeuing three, queue must be empty")
    }

    @Test fun filterFollowing() {
        val queue = FilterQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.removeElements { it >= 0 }
        assertTrue(queue.empty, "After removing all elements, queue must be empty")
    }

    @Test fun queueAfterFilterAll() {
        val queue = FilterQueue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.removeElements { it >= 0 }
        queue.enqueue(3)
        assertEquals(3, queue.first, "First element after filtering should be 3")
    }

    @Test fun queueFilterPairs() {
        val queue = FilterQueue<Int>()
        for (i in 1..10) {
            queue.enqueue(i)
        }
        queue.removeElements { it % 2 == 0 }
        assertEquals(1, queue.first, "First element must be 1")
        assertEquals(1, queue.dequeue(), "First element dequeued must be 1")
        assertEquals(3, queue.dequeue(), "Element dequeued must be 3")
        assertEquals(5, queue.dequeue(), "Element dequeued must be 5")
        assertEquals(7, queue.dequeue(), "Element dequeued must be 7")
        assertEquals(9, queue.dequeue(), "Element dequeued must be 9")
        assertTrue(queue.empty, "After that, queue must be empty")
    }

    @Test fun erathostenesSieve() {
        val queue = FilterQueue<Int>()

        val primeNumbers = mutableListOf<Int>()
        for (i in 2..30) {
            queue.enqueue(i)
        }

        while (!queue.empty) {
            val element = queue.dequeue()
            primeNumbers.add(element)
            queue.removeElements { it % element == 0 }
        }

        assertEquals(listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29), primeNumbers)
    }
}