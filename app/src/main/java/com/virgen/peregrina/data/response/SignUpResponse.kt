package com.virgen.peregrina.data.response

import com.virgen.peregrina.data.model.ReplicaModel

data class SignUpResponse(
    val id: Long,
    val uuid: String?,
    val name: String,
    val last_name: String,
    val email: String,
    val city: String,
    val country: String,
    val cellphone: String,
    val telephone: String,
    val photo_url: String,
    val role: String,
    val address: String,
    val replicas: List<ReplicaModel>,
    val isPilgrim: Boolean = true
)
