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

/**
 * This module exports a function for iterating over the supersets of a given set.
 *
 * Created by manuel on 5/07/17.
 */

/**
 * Supersets iterator. This structure will be returned by the `iterateSupersetsOf` function.
 */
interface SupersetsIterator<out T> {
    /**
     * Obtain the next superset.
     *
     * @param superset if set to false, the iterator will discard all the supersets
     *          of the last set returned by the iterator. Otherwise, these
     *          will be returned in the successive calls to next()
     *
     * @return The next set in the iterator
     */
    fun next(superset: Boolean): Set<T>?
}

/**
 * The class will use a FilterQueue, whose elements will be instances of QueueEntry
 *
 * It assumes that there exists a list of elements to be considered for inclusion of the
 * supersets, so `remainingIndex` tell us an index inside this list. The elements before
 * that index have already been considered for inclusion in the supersets to be generated,
 * whereas those after the index are still pending to be considered.
 *
 * @property generatedSet The set that will be returned when this entry becomes the first in the queue
 * @property remainingIndex We
 */
private data class QueueEntry<out T>(val generatedSet: Set<T>, val remainingIndex: Int)


/**
 * It returns an iterator that provides the supersets of the set given as first parameter.
 * The second parameter is given as an "upper bound set", in the sense that all sets generated
 * by the iterator are contained within this upper bound set.
 *
 * If `start` is the first parameter and `total` is the second parameter, all the elements X
 * returned by the iterator satisfy the property: start < X <= total.
 *
 * The iterator provides the capability of skipping some of the supersets by means of the
 * parameter that the SupersetsIterator<T>.next() receives.
 *
 * @param start Initial set whose supersets will be returned by the iterator.
 * @param total Upper bound set
 *
 * @return A pair made up of the first obtained superset (if any) and the iterator that will
 *        allow us to obtain the next supersets.
 */
fun <T> iterateSupersetsOf(start: Set<T>, total: Set<T>): Pair<Set<T>?, SupersetsIterator<T>> {

    // We obtain the elements that we have to add to `start` in order to generate supersets.
    val remaining = (total - start).toList()

    val queue = FilterQueue<QueueEntry<T>>()

    // List of `banned` sets. Every superset that contains one of the sets inside this list
    // will not be generated
    val banned: MutableList<Set<T>> = mutableListOf()


    // For each of them, we generate the corresponding superset (of cardinality n + 1) and
    // enqueue it with the remaining index being the next element, as the previous elements have already
    // been considered for inclusion (in fact, they are already in the queue).
    for ((i, r) in remaining.withIndex()) {
        queue.enqueue(QueueEntry(start + r, i + 1))
    }

    // If no element has been queued, then there are no supersets, so we just return
    // null as the next subset and the iterator that always returns null
    if (queue.empty) {
        return Pair(null, object : SupersetsIterator<T> {
            override fun next(superset: Boolean): Nothing? = null
        })
    } else {
        // Otherwise, we return the first superset and the iterator
        return Pair(queue.first.generatedSet, object : SupersetsIterator<T> {
            override fun next(superset: Boolean): Set<T>? {
                // We obtain and dequeue the last returned superset
                val lastGenerated = queue.dequeue()

                // The superset parameter tells us whether we should generate
                // supersets of `lastGenerated` or not.
                if (superset) {
                    // We have to traverse the remaining elements and generate
                    // new supersets
                    for (i in lastGenerated.remainingIndex until remaining.size) {
                        val newSet = lastGenerated.generatedSet + remaining[i]

                        // If the new set does not contain any of the banned supersets
                        if (banned.all { !newSet.containsAll(it) }) {
                            // We add it to the queue
                            queue.enqueue(QueueEntry(newSet, i + 1))
                        }
                    }
                } else {
                    // The superset parameters tells us that we must not generate
                    // supersets of the last returned set, so we add it to the
                    // list of banned sets and we remove from the filter queue all
                    // the sets that contain the last generated set
                    banned.add(lastGenerated.generatedSet)
                    queue.removeElements { it.generatedSet.containsAll(lastGenerated.generatedSet) }
                }

                return if (queue.empty) null else queue.first.generatedSet
            }
        })
    }
}