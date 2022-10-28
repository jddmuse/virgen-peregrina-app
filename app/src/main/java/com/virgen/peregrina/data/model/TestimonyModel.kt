package com.virgen.peregrina.data.model

data class TestimonyModel(
    val id: Long? = null,
    var date: String? = null,
    val user_id: Long,
    val user_name: String? = null,
    val replica_id: Long,
    val replica_code: String? = null,
    val pilgrimage_id: Long,
    val value: String
)