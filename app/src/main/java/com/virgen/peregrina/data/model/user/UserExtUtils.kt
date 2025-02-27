package com.virgen.peregrina.data.model.user

fun UserLiteModel.parselable(): UserLiteParcelableModel {
    return UserLiteParcelableModel(id, name, lastName, email, address, city, country, cellphone)
}