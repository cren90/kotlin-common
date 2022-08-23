@file:Suppress("unused")

package com.cren90.kotlin.common.extensions

import android.os.Build
import android.telephony.PhoneNumberUtils
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Base64
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

/**
 * Replaces the final instance of [oldValue] with [newValue] based on [ignoreCase] and returns the
 * resulting string.
 */
fun String.replaceLast(
    oldValue: String,
    newValue: String,
    ignoreCase: Boolean = false
): String {

    val index = this.lastIndexOf(oldValue, ignoreCase = ignoreCase)

    var newString = this
    if (index >= 0) {
        newString = "${
            newString.substring(
                0,
                index
            )
        }$newValue${newString.substring(index + oldValue.length)}"
    }

    return newString
}

/**
 * Simple email pattern validation.
 */
fun String.isValidEmail(): Boolean {
    val pattern = "^(.*@.+\\..+)$"

    return matches(pattern.toRegex())
}

/**
 * Checks if the string is a phone number
 */
fun String.isValidPhone(): Boolean = PhoneNumberUtils.isGlobalPhoneNumber(this)

/**
 * Checks if the string is a 10 digit phone number
 */
fun String.is10DigitPhone(): Boolean {
    val pattern = "^(\\(\\d{3}\\) (\\d{3}-\\d{4})|(\\d{10}))$"
    return matches(pattern.toRegex())
}

/**
 * Returns a new string based on the original with the prepending numbers removed to limit the
 * phone number to 10 digits.
 */
fun String.removePhoneCountryPrefix(): String {
    val length = this.length
    var result = this
    if (length > 10) {
        val start = length - 10
        result = this.removeRange(0, start)
    }
    return result
}

fun String.fetchVerificationCodeFromSms(): String {
    return Regex("(\\d{6})").find(this)?.value ?: ""
}

fun String.underscoreToWords(): String = replace("_", " ")

fun String.toTitleCase(): String {
    var capNext = true
    val sb = StringBuilder()

    this.toCharArray().forEach {
        val c = if (capNext) Character.toUpperCase(it) else Character.toLowerCase(it)
        sb.append(c)
        capNext = Character.isSpaceChar(it)
    }

    return sb.toString()
}

fun String.toPascalCase(): String {
    var capNext = true
    val sb = StringBuilder()

    this.toCharArray().forEach {
        capNext = if (it != ' ') {
            val c = if (capNext) Character.toUpperCase(it) else Character.toLowerCase(it)
            sb.append(c)
            false
        } else {
            true
        }
    }

    return sb.toString()
}

fun String.capitalizeWords(): String {
    if (this.isEmpty()) return ""

    val stringBuilder = StringBuilder()

    val parts = this.trim { it <= ' ' }.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
        .toTypedArray()
    for (part in parts) {
        if (part.isNotEmpty()) {
            stringBuilder
                .append(part.substring(0, 1).uppercase(Locale.getDefault()))
                .append(part.substring(1).lowercase(Locale.getDefault()))
                .append(" ")
        }
    }

    return stringBuilder.toString().trim { it <= ' ' }
}

fun String.colorPhoneNumber(color: Int): CharSequence {
    val pattern = "\\d? ?\\(?\\d{3}\\)?-? ?\\d{3}–? ?\\d{4}".toRegex()

    val phoneNumber = pattern.find(this)?.value

    return phoneNumber?.let { colorSubstring(phoneNumber, color) } ?: this
}

fun String.colorSubstring(substring: String, color: Int): CharSequence {
    val spannable = SpannableString(this)
    var substringStart = 0
    var start: Int
    while (this.indexOf(substring, substringStart).also { start = it } >= 0) {
        spannable.setSpan(
            ForegroundColorSpan(color), start, start + substring.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        substringStart = start + substring.length
    }

    return spannable
}

fun String.urlEncode(): String = try {
    URLEncoder.encode(this, "UTF-8")
} catch (e: UnsupportedEncodingException) {
    ""
}

@Suppress("DEPRECATION", "kotlin:S1874")
fun String.toHtmlSpan(): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
    Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
else
    Html.fromHtml(this)

fun String.toUUID(): UUID = UUID.fromString(this)

fun String.fromBase64(): ByteArray = Base64.decode(this, Base64.DEFAULT)

/** A helper method to convert a hex-encoded string to a byte array. */
fun String.fromHex(): ByteArray {
    require(length % 2 == 0) { "Hex string must be of even length." }

    return chunked(2)
        .map {
            @Suppress("experimental_api_usage")
            it.toUByte(16).toByte()
        }
        .toByteArray()
}
