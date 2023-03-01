@file:Suppress("unused")

package com.cren90.kotlin.common.extensions

fun <K, V> MutableMap<K, V>.putAll(from: Map<K, V>?) {
    from?.let {
        putAll(it)
    }
}