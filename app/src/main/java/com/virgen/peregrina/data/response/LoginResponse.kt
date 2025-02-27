package com.virgen.peregrina.data.response

data class LoginResponse(
    val userId: Long,
    val userName: String,
    val userEmail: String,
    val userAddress: String,
    val userCity: String,
    val userCountry: String,
    val userCellphone: String
)