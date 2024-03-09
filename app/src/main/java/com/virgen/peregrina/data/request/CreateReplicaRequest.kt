package com.virgen.peregrina.data.request

import com.google.gson.annotations.SerializedName

data class CreateReplicaRequest(
    val code: String,
    val year: String,

    @SerializedName("requiredRestore")
    val requireRepair: Boolean,

    @SerializedName("user_id")
    val ownerId: String,

    val container: Boolean,

    @SerializedName("state")
    val status: String
)