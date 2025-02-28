package com.virgen.peregrina.util

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.virgen.peregrina.MainActivity
import com.virgen.peregrina.ui.login.LoginActivity
import com.virgen.peregrina.util.enumerator.EnumDaysOfWeekSpanish
import com.virgen.peregrina.util.enumerator.EnumMonthsSpanish
import java.time.DayOfWeek
import java.time.Month

fun <T> convertJsonString2DataClass(json: String?, type: Class<T>): T? {
    return try {
        if(json.isNullOrEmpty())
            return null
        val data = Gson().fromJson(json, type)
        data
    } catch (ex: Exception) {
        getExceptionLog("ExtensionUtils", "convertJsonString2DataClass", ex)
        null
    }
}

fun getExceptionLog(tag: String, method: String, exception: Exception) =
    Log.e(tag, "$method() -> Exception=$exception")

fun String.camelCase(delimiter: String = " "): String {
    val words = this.split(delimiter)
    val str = StringBuilder()
    words.forEach { word ->
        str.append(word.first().uppercase())
        if(word.length > 1) {
            for(c in word.substring(1)) {
                str.append(c.lowercase())
            }
        }
        str.append(delimiter)
    }
    return str.toString().trim()
}

fun AppCompatActivity.navigateToMainActivity(flag: Int = Intent.FLAG_ACTIVITY_CLEAR_TOP) {
    val intent = Intent(this, MainActivity::class.java)
        .apply { flags = flag }
    startActivity(intent)
}

fun AppCompatActivity.navigateToMainActivity(json: String) {

}

fun AppCompatActivity.navigateToLoginActivity() {
    val intent = Intent(this, LoginActivity::class.java)
        .apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP }
    startActivity(intent)
}

fun Int.lessThanTen(): String {
    if (this < 10) {
        return "0$this"
    }
    return "$this"
}

fun DayOfWeek.spanish(): String {
    return when(this) {
        DayOfWeek.SUNDAY -> EnumDaysOfWeekSpanish.DOMINGO.name.camelCase()
        DayOfWeek.MONDAY -> EnumDaysOfWeekSpanish.LUNES.name.camelCase()
        DayOfWeek.TUESDAY -> EnumDaysOfWeekSpanish.MARTES.name.camelCase()
        DayOfWeek.WEDNESDAY -> EnumDaysOfWeekSpanish.MIERCOLES.name.camelCase()
        DayOfWeek.THURSDAY -> EnumDaysOfWeekSpanish.JUEVES.name.camelCase()
        DayOfWeek.FRIDAY -> EnumDaysOfWeekSpanish.VIERNES.name.camelCase()
        DayOfWeek.SATURDAY -> EnumDaysOfWeekSpanish.SABADO.name.camelCase()
    }
}

fun Month.spanish(): String {
    return when(this) {
        Month.JANUARY -> EnumMonthsSpanish.ENERO.name.camelCase()
        Month.FEBRUARY -> EnumMonthsSpanish.FEBRERO.name.camelCase()
        Month.MARCH -> EnumMonthsSpanish.MARZO.name.camelCase()
        Month.APRIL -> EnumMonthsSpanish.ABRIL.name.camelCase()
        Month.MAY -> EnumMonthsSpanish.MAYO.name.camelCase()
        Month.JUNE -> EnumMonthsSpanish.JUNIO.name.camelCase()
        Month.JULY -> EnumMonthsSpanish.JULIO.name.camelCase()
        Month.AUGUST -> EnumMonthsSpanish.AGOSTO.name.camelCase()
        Month.SEPTEMBER -> EnumMonthsSpanish.SEPTIEMBRE.name.camelCase()
        Month.OCTOBER -> EnumMonthsSpanish.OCTUBRE.name.camelCase()
        Month.NOVEMBER -> EnumMonthsSpanish.NOVIEMBRE.name.camelCase()
        Month.DECEMBER -> EnumMonthsSpanish.DICIEMBRE.name.camelCase()
    }
}