package com.virgen.peregrina.util

import android.util.Log
import com.google.gson.Gson

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
    return str.toString()
}