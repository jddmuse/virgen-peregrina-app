package com.virgen.peregrina.data.request

import com.google.gson.annotations.SerializedName
import com.virgen.peregrina.data.model.ReplicaModel

data class SignUpRequest(
    @SerializedName("uuid") val uuid: String,
    val name: String,
    @SerializedName("lastName") val last_name: String,
    val email: String,
    val city: String,
    val country: String,
    val cellphone: String,
    @SerializedName("photoUrl") val photo_url: String? = null,
    val address: String,
    val replicas: List<ReplicaModel>? = null
)
