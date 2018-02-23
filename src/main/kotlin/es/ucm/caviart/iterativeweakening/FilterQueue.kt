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

package es.ucm.caviart.iterativeweakening

import java.util.*
import kotlin.NoSuchElementException

/**
 * This module implements a queue that allows filtering (that is, removal of some
 * elements that satisfy a given predicate)
 *
 * Created by manuel on 6/07/17.
 */

private class FilterQueueElement<out T>(val element: T, var discarded: Boolean = false)

class FilterQueue<T> {
    private val list = ArrayList<FilterQueueElement<T>>()
    private var currentIdx = 0

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