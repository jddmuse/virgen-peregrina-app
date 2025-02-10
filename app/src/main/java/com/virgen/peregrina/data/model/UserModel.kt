package com.virgen.peregrina.data.model

data class UserModel(
    val id: Long,
    val name: String,
    val lastName: String,
    val email: String,
    val address: String,
    val city: String,
    val country: String,
    val cellphone: String,
    val telephone: String? = null,
    val photoUrl: String? = null,
    val pass: String
)