@file:Suppress("unused")

package com.cren90.kotlin.common.extensions

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*

/**
 * Converts the [UUID] to a [ByteArray] with the specified [ByteOrder]
 */
fun UUID.toByteArray(
    byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
): ByteArray = ByteBuffer.allocate(Long.SIZE_BYTES * 2).apply {
    order(byteOrder)
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
        putLong(mostSignificantBits)
        putLong(leastSignificantBits)
    } else {
        putLong(leastSignificantBits)
        putLong(mostSignificantBits)
    }
}.array()