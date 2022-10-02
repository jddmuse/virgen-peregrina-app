package com.virgen.peregrina.data.model

data class ReplicaModel(
    val id: Int? = null,
    val code: String,
    val received_date: String,
    val repair_required: Boolean
)