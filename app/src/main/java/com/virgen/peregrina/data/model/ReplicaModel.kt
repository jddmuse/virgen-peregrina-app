package com.virgen.peregrina.data.model

import java.util.Date

data class ReplicaModel(
    val id: Long,
    val photoUrl: String?,
    val code: String,
    val birthdate: Date,
    val user: UserModel
)