package com.virgen.peregrina.data.model.pilgrimage

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class PilgrimageOnlyDatesModel(
    @SerializedName("startDate") val startDate: LocalDate,
    @SerializedName("endDate") val endDate: LocalDate,
)