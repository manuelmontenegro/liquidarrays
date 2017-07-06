package es.ucm.caviart

import java.util.*

/**
 * Created by manuel on 5/07/17.
 */

interface SupersetsIterator<out T> {
    fun next(subset: Boolean) : Set<T>?
}

data class QueueEntry<T>(val generatedSet: Set<T>, val remainingIndex: Int)


fun <T> iterateSupersetsOf(start : Set<T>, total: Set<T>) : Pair<Set<T>?, SupersetsIterator<T>> {
    val remaining = (total - start).toList()

    val queue = FilterQueue<QueueEntry<T>>()


    for ((i, r) in remaining.withIndex()) {
        queue.enqueue(QueueEntry(start + r, i + 1))
    }

    if (queue.empty) {
        return Pair(null, object: SupersetsIterator<T> {
            override fun next(subset: Boolean) = null
        })
    } else {
        return Pair(queue.first.generatedSet, object: SupersetsIterator<T> {
            override fun next(subset: Boolean): Set<T>? {
                val lastGenerated = queue.dequeue()
                if (subset) {
                    for (i in lastGenerated.remainingIndex until remaining.size) {
                        queue.enqueue(QueueEntry(lastGenerated.generatedSet + remaining[i], i + 1))
                    }
                } else {
                    queue.removeElements { it.generatedSet.containsAll(lastGenerated.generatedSet) }
                }

                return if (queue.empty) null else queue.first.generatedSet
            }
        })
    }
}