package es.ucm.caviart

import java.util.*
import kotlin.NoSuchElementException

/**
 * Created by manuel on 6/07/17.
 */

private class FilterQueueElement<T>(val element: T, var discarded: Boolean = false)

class FilterQueue<T> {
    private val list = ArrayList<FilterQueueElement<T>>()
    private var currentIdx = 0;

    private fun advanceIterator() {
        while (currentIdx < list.size && list[currentIdx].discarded) {
            currentIdx++
        }
    }

    fun enqueue(element: T) {
        list.add(FilterQueueElement(element))
    }

    val first: T
        get () {
            advanceIterator()
            return if (currentIdx < list.size) list[currentIdx].element else throw NoSuchElementException("First of empty queue")
        }

    fun dequeue(): T {
        advanceIterator()
        if (currentIdx < list.size) {
            val result = list[currentIdx].element
            currentIdx++
            return result
        } else {
            throw NoSuchElementException("Popping from empty queue")
        }
    }

    val empty: Boolean
        get() {
            advanceIterator()
            return currentIdx >= list.size
        }

    fun removeElements(predicate: (T) -> Boolean) {
        advanceIterator()
        var filterIdx = currentIdx
        while (filterIdx < list.size) {
            if (!list[filterIdx].discarded && predicate(list[filterIdx].element)) {
                list[filterIdx].discarded = true
            }
            filterIdx++
        }
    }
}