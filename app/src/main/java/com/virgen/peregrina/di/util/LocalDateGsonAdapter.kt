package com.virgen.peregrina.di.util

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDate

class LocalDateGsonAdapter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        if(src != null)
            return JsonArray().apply {
                add(src.year)
                add(src.monthValue)
                add(src.dayOfMonth)
            }
        return JsonArray()
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate? {
        val year = json?.asJsonArray?.get(0)?.asInt
        val month = json?.asJsonArray?.get(1)?.asInt
        val day = json?.asJsonArray?.get(2)?.asInt
        return if (day != null && month != null && year != null) LocalDate.of(year, month, day) else null
    }
}