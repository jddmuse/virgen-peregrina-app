package com.virgen.peregrina.data.model.pilgrimage

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class PilgrimageLiteModel(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("startDate") val startDate: LocalDate,
    @SerializedName("endDate") val endDate: LocalDate,
    @SerializedName("intention") val intention: String,
    @SerializedName("replicaId") val replica: Long,
    @SerializedName("userId") val user: Long
)

