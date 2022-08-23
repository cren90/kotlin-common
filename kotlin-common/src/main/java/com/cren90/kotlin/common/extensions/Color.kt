@file:Suppress("unused")

package com.cren90.kotlin.common.extensions

import android.graphics.Color

fun Int.isLight(): Boolean {
    val tone =
        1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return tone < 0.5
}