package com.virgen.peregrina.data.request

data class CreateReplicaRequest(
    val code: String,
    val year: String,
    val requireRepair: Boolean,
    val ownerId: Long,
    val container: Boolean
)