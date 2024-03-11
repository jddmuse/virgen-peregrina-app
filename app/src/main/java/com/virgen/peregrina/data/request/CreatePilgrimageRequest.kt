package com.virgen.peregrina.data.request

import com.google.gson.annotations.SerializedName

data class CreatePilgrimageRequest(
    @SerializedName("city")
    val city: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("endDate")
    val endDate: String,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("intention")
    val intention: String,

    @SerializedName("receiverId")
    val receiverId: Long,

    @SerializedName("replicaId")
    val replicaId: Long,

    @SerializedName("userId")
    val userId: Long
)
