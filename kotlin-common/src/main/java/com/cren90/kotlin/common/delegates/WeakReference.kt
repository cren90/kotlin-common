@file:Suppress("unused")

package com.cren90.kotlin.common.delegates

import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Delegate function for initializing a weak reference to an object of type [T]
 *
 * Usage:
 * `val foo by weakReference(Foo())
 */
fun <T> weakReference(initial: T? = null): ReadWriteProperty<Any?, T?> {
    return object : ReadWriteProperty<Any?, T?> {
        var t = WeakReference<T?>(initial)
        override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
            return t.get()
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            t = WeakReference(value)
        }
    }
}