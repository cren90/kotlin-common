@file:Suppress("unused")

package com.cren90.kotlin.common.extensions

import java.io.PrintWriter
import java.io.StringWriter

/**
 * Generates a human readable string representation of the [Throwable]'s stack trace.
 */
fun Throwable.stackTraceToString(): String = StringWriter().use { stringWriter ->
    PrintWriter(stringWriter).use { printWriter ->
        this.printStackTrace(printWriter)
        stringWriter.toString()
    }
}