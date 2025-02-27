package com.virgen.peregrina.data.request

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CreatePilgrimageRequest(
    @SerializedName("replicaId") val replicaId: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("startDate") val startDate: LocalDate,
    @SerializedName("endDate") val endDate: LocalDate,
    @SerializedName("intention") val intention: String
)