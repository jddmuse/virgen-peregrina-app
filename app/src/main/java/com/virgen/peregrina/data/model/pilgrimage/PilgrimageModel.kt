package com.virgen.peregrina.data.model.pilgrimage

import com.google.gson.annotations.SerializedName
import com.virgen.peregrina.data.model.replica.ReplicaLiteModel
import com.virgen.peregrina.data.model.user.UserLiteModel
import java.time.LocalDate

data class PilgrimageModel(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("startDate") val startDate: LocalDate,
    @SerializedName("endDate") val endDate: LocalDate,
    @SerializedName("intention") val intention: String,
    @SerializedName("replica") val replica: ReplicaLiteModel,
    @SerializedName("user") val user: UserLiteModel
)