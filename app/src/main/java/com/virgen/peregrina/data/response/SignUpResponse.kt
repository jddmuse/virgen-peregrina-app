package com.virgen.peregrina.data.response

data class SignUpResponse(
    val id:Int,
    val uuid: String,
    val name: String,
    val last_name: String,
    val email: String,
    val city: String,
    val country: String,
    val cellphone: String,
    val telephone: String,
    val photo_url: String,
    val role: String,
    val address: String
)
