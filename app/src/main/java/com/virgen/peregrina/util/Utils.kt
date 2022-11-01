package com.virgen.peregrina.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.virgen_peregrina_app.R
import java.util.*

fun getCellphone(code: String, number: String) = "$code$number"

fun formatDate(day: Int, month: Int, year: Int): String =
    formatLessThanTen(day) + SLASH + formatLessThanTen(month + 1) + SLASH + year

fun formatLessThanTen(value: Int): String {
    return if (value < 10) "0$value" else "$value"
}

fun formatDateForView(
    context: Context,
    date: String
): String {
    val list = date.split("/")
    val day = list[0]
    val month = list[1]
    val year = list[2]
    val monthAux = when (month) {
        "01" -> context.getString(R.string.month_january)
        "02" -> context.getString(R.string.month_february)
        "03" -> context.getString(R.string.month_march)
        "04" -> context.getString(R.string.month_april)
        "05" -> context.getString(R.string.month_may)
        "06" -> context.getString(R.string.month_june)
        "07" -> context.getString(R.string.month_july)
        "08" -> context.getString(R.string.month_august)
        "09" -> context.getString(R.string.month_september)
        "10" -> context.getString(R.string.month_october)
        "11" -> context.getString(R.string.month_november)
        "12" -> context.getString(R.string.month_december)
        else -> ""
    }
    return "$day $monthAux"
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

fun formatLocation(city:String, country:String): String {
    return "$city, $country"
}

