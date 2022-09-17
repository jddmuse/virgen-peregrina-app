package com.virgen.peregrina.data.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("firebaseUid") val uuid: String,
    val name: String,
    @SerializedName("lastName") val last_name: String,
    val email: String,
    val city: String,
    val country: String,
    val cellphone: String,
    @SerializedName("photoUrl") val photo_url: String? = null,
    val role: String? = "ADMIN",
    val address: String
)
