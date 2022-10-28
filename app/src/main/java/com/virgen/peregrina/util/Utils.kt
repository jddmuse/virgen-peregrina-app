package com.virgen.peregrina.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.util.*

fun getCellphone(code: String, number: String) = "$code$number"

fun formatDate(day: Int, month: Int, year: Int): String =
    formatLessThanTen(day) + SLASH + formatLessThanTen(month + 1) + SLASH + year

fun formatLessThanTen(value: Int): String {
    return if (value < 10) "0$value" else "$value"
}

fun getExceptionLog(tag: String, method: String, exception: Exception) =
    Log.e(tag, "$method() -> Exception=$exception")

fun getToast(
    context: Context,
    message: String,
    duration: Int = Toast.LENGTH_LONG
) = Toast.makeText(context, message, duration).show()

fun getCurrentTime(): String {
    val calendar = Calendar.getInstance()
    return formatLessThanTen(calendar.get(Calendar.HOUR_OF_DAY)) +
            DOUBLE_POINT + formatLessThanTen(calendar.get(Calendar.MINUTE)) +
            DOUBLE_POINT + formatLessThanTen(calendar.get(Calendar.SECOND))
}

fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    return formatLessThanTen(calendar.get(Calendar.DAY_OF_MONTH)) +
            SPLIT + formatLessThanTen(calendar.get(Calendar.MONTH)) +
            SPLIT + calendar.get(Calendar.YEAR)
}

