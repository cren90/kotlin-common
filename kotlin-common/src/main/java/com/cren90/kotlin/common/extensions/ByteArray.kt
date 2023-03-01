@file:Suppress("unused")

package com.cren90.kotlin.common.extensions

import java.util.*

fun ByteArray.toBase64String(): String = Base64.getEncoder().encodeToString(this)

/** A helper method to convert a byte array to a hex-encoded string. */
fun ByteArray.toHexLower(): String =
    joinToString(separator = "", transform = Byte::toHexLower)

private fun Byte.toHexLower(): String =
    String.format("%02x", toInt() and 0xFF)