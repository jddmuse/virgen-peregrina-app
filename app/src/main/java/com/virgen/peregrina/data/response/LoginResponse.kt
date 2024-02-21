package com.virgen.peregrina.data.response

import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.model.ReplicaModel

data class LoginResponse(
    val id: Long,
    val uuid: String,
    val name: String,
    val lastName: String,
    val email: String,
    val city: String,
    val country: String,
    val cellphone: String?,
    val telephone: String?,
    val photo_url: String?,
    val role: String?,
    val address: String,
    val replicas: List<ReplicaModel>?,
    val isPilgrim: Boolean,
    val pilgrimages: List<PilgrimageModel> = emptyList()
)

data class UserSessionData(
    val id: Long,
    val uuid: String,
    val name: String,
    val lastName: String,
    val email: String,
    val city: String,
    val country: String,
    val cellphone: String?,
    val telephone: String?,
    val photo_url: String?,
    val role: String?,
    val address: String,
    val replicas: List<ReplicaModel>?,
    val isPilgrim: Boolean,
    val pilgrimages: List<PilgrimageModel> = emptyList()
)

fun LoginResponse.toSessionData(): UserSessionData {
    return UserSessionData(
        id = id, uuid = uuid, name = name, lastName = lastName,
        email = email, city = city, country = country, cellphone = cellphone,
        telephone = telephone, photo_url = photo_url, role = role, address = address,
        replicas = replicas, isPilgrim = isPilgrim, pilgrimages = pilgrimages
    )
}