package com.virgen.peregrina.data.model

data class TestimonyModel(
    val id: Long? = null,
    var date: String? = null,
    val user_id: Long,
    val user_name: String?,
    val replica_id: Long,
    val replica_code: String?,
    val value: String
)