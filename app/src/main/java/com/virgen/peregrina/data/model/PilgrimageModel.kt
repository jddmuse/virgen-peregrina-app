package com.virgen.peregrina.data.model

import com.virgen.peregrina.data.response.GetPilgrimagesResponse

data class PilgrimageModel(
    val id: Long? = null,
    val date_start: String? = "",
    val date_end: String? = "",
    val intention: String,
    val user_id: Long,
    val replica_id: Long,
    val replica_code: String = "",
    val isFinished: Boolean = false,
    val city: String = "",
    val country: String = "",
    val state: String = "",
    val replica_is_returned: Boolean = false,
    val have_testimony: Boolean = false,
    val receiver_user_id: Long,
    val receiver_user_name: String = "",
    val receiver_user_telephone: String = "",
    val receiver_user_email: String = "",
    val replica_owner_user_id: Long? = null,
    val replica_owner_name_id: String = "",
    val replica_owner_user_email: String = "",
    val replica_owner_user_telephone: String = "",
    val attendantName: String = "",
    val attendantEmail: String = "",
    val attendantPhone: String = "",
)

fun GetPilgrimagesResponse.toModel(): PilgrimageModel {
    return PilgrimageModel(
        id = id,
        date_start = startDate,
        date_end = endDate,
        intention = intention,
        user_id = userId,
        replica_id = replicaId,
        replica_code = replicaCode,
        isFinished = isFinished,
        city = city,
        country = country,
        state = status,
        replica_is_returned = replicaIsReturned,
        have_testimony = haveTestimony,
        receiver_user_id = receiverId,
        receiver_user_name = receiverName,
        receiver_user_telephone = receiverTelephone,
        receiver_user_email = receiverEmail,
        replica_owner_user_id = replicaOwnerId,
        replica_owner_name_id = replicaOwnerName,
        replica_owner_user_email = replicaOwnerEmail,
        replica_owner_user_telephone = replicaOwnerTelephone,
        attendantName = attendantName,
        attendantEmail = attendantEmail,
        attendantPhone = attendantPhone
    )
}