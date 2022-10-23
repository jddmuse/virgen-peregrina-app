package com.virgen.peregrina.data.model

data class PilgrimageModel(
    val id: Long? = null,
    val date_start: String,
    val date_end: String,
    val intention: String,
    val user_id: Long,
    val replica_id: Long,
    val receiver_user_id: Long
)