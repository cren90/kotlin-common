@file:Suppress("unused")

package com.cren90.kotlin.common.extensions

import kotlin.math.round

fun Float.round(): Float = round(this)

fun Float.roundtoHalf(): Float = round(this * 2.0f) / 2.0f

fun Float.format(digitsAfterDecimal: Int) = "%.${digitsAfterDecimal}f".format(this)