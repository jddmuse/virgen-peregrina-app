package com.virgen.peregrina.data.request

import com.virgen.peregrina.data.model.user.UserModel

data class CreateUserRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val address: String,
    val city: String,
    val country: String,
    val cellphone: String,
    val telephone: String,
    val photoUrl: String,
    val pass: String
)

fun CreateUserRequest.toModel(): UserModel {
    return UserModel(
        id = -1,
        name = this.name,
        lastName = this.lastName,
        email = this.email,
        address = this.address,
        city = this.city,
        country = this.country,
        cellphone = this.cellphone,
        telephone = this.telephone,
        photoUrl = this.photoUrl,
        pass = pass
    )
}