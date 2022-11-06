package com.virgen.peregrina.data.model

data class UserModel(
    val id: Long,
    val uuid: String,
    val name: String,
    val lastName: String,
    val email: String,
    val city: String,
    val country: String,
    val cellphone: String,
    val telephone: String,
    val photo_url: String,
    val role: String,
    val address: String,
    val replicas: List<ReplicaModel>,
    val isPilgrim: Boolean,
    val pilgrimages: List<PilgrimageModel> = emptyList()
)