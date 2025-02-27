package com.virgen.peregrina.data.request

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

class CreateReplicaRequest(
    @SerializedName("code") val code: String,
    @SerializedName("birthdate") val birthdate: LocalDate,
    @SerializedName("ownerId") val ownerId: Long
)