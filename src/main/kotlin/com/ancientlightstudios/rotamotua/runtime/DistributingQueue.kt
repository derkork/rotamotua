package com.ancientlightstudios.rotamotua.runtime

import java.lang.UnsupportedOperationException
import java.util.*

class DistributingQueue<T> : Queue<T> {
    val receivers = mutableSetOf<Queue<T>>()

    fun addReceiver(queue: Queue<T>){
        receivers.add(queue)
    }

    override fun contains(element: T): Boolean {
        throw UnsupportedOperationException("this queue is write-only")
    }

    override fun addAll(elements: Collection<T>): Boolean {
        receivers.forEach { it.addAll(elements) }
        return true
    }

    override fun clear() {
        receivers.forEach { it.clear() }
    }

    override fun element(): T {
        throw UnsupportedOperationException("this queue is write-only")
    }

    override fun isEmpty(): Boolean {
        throw UnsupportedOperationException("this queue is write-only")
    }

    override fun remove(): T {
        throw UnsupportedOperationException("this queue is write-only")
    }

    override val size: Int
        get() = throw UnsupportedOperationException("this queue is write-only")


    override fun containsAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("this queue is write-only")
    }

    override fun iterator(): MutableIterator<T> {
        throw UnsupportedOperationException("this queue is write-only")
    }

    override fun remove(element: T): Boolean {
        throw UnsupportedOperationException("this queue is write-only")
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("this queue is write-only")
    }

    override fun add(element: T): Boolean {
        receivers.forEach { it.add(element) }
        return true
    }

    override fun offer(element: T): Boolean {
        receivers.forEach { it.offer(element) }
        return true
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("this queue is write-only")
    }

    override fun peek(): T {
        throw UnsupportedOperationException("this queue is write-only")
    }

    override fun poll(): T {
        throw UnsupportedOperationException("this queue is write-only")
    }
}