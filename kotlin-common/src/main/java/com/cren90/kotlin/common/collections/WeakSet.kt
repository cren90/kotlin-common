@file:Suppress("unused")
package com.cren90.kotlin.common.collections

import java.util.*

class WeakMutableSet<T>(initialCapacity: Int = 0): MutableSet<T>{

    private val map = WeakHashMap<T, Unit>(initialCapacity)
    private val set: MutableSet<T>
        get() = map.keys.toMutableSet()

    override fun contains(element: T): Boolean = set.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = set.containsAll(elements)

    override fun isEmpty(): Boolean = set.isEmpty()

    override fun iterator(): MutableIterator<T> = set.iterator()

    override val size: Int
        get() = set.size

    override fun add(element: T): Boolean {
        map[element] = Unit
        return true
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val result = !set.containsAll(elements)
        elements.forEach {
            map[it] = Unit
        }
        return result
    }

    override fun clear() {
        map.clear()
    }

    override fun remove(element: T): Boolean {
        val result = map.containsKey(element)
        map.remove(element)
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        val result = elements.any { map.containsKey(it) }
        elements.forEach{
            map.remove(it)
        }
        return result
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        var result = false
        set.forEach {
            if(!elements.contains(it))
            {
                map.remove(it)
                result = true
            }
        }
        return result
    }
}

fun <T> weakSetOf(vararg elements: T): WeakMutableSet<T> = WeakMutableSet<T>(elements.size).apply {
    addAll(elements)
}