package com.virgen.peregrina.data.model.user

data class UserLiteModel(
    val id: Long,
    val name: String,
    val lastName: String,
    val email: String,
    val address: String,
    val city: String,
    val country: String,
    val cellphone: String,
)