package com.virgen.peregrina.util

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