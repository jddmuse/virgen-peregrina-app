package com.virgen.peregrina.data.model

data class PilgrimageModel(
    val id: Long? = null,
    val date_start: String,
    val date_end: String,
    val intention: String,
    val user_id: Long,
    val replica_id: Long,
    val replica_code: String = "",
    val isFinished: Boolean = false,
    val city: String = "",
    val country: String = "",
    val state: String = "",
    val replica_is_returned: Boolean = false,
    val have_testimony: Boolean = false,
    val receiver_user_id: Long,
    val receiver_user_name: String = "",
    val receiver_user_telephone: String = "",
    val receiver_user_email: String = "",
    val replica_owner_user_id: Long? = null,
    val replica_owner_name_id: String = "",
    val replica_owner_user_email: String = "",
    val replica_owner_user_telephone: String = ""
)