package com.virgen.peregrina.util

fun getCellphone(code: String, number: String) = "$code$number"

fun formatDate(day: Int, month: Int, year: Int): String =
    formatLessThanTen(day) + SLASH + formatLessThanTen(month+1) + SLASH + year

fun formatLessThanTen(value: Int): String {
    return if (value < 10) "0$value" else "$value"
}