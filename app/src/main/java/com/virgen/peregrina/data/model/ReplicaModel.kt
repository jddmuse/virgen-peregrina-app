package com.virgen.peregrina.data.model

data class ReplicaModel(
    val id: Long? = null,
    val code: String,
    val received_date: String,
    val repair_required: Boolean,
    val user_id: Int? = null,
    val user_name: String? = null,
) : java.io.Serializable