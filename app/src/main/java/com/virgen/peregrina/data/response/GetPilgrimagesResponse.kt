package com.virgen.peregrina.data.response

import com.google.gson.annotations.SerializedName

data class GetPilgrimagesResponse(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("startDate")
    val startDate: String? = "",

    @SerializedName("endDate")
    val endDate: String? = "",

    @SerializedName("intention")
    val intention: String,

    @SerializedName("userId")
    val userId: Long,

    @SerializedName("replicaId")
    val replicaId: Long,

    @SerializedName("isFinished")
    val isFinished: Boolean = false,

    @SerializedName("city")
    val city: String = "",

    @SerializedName("country")
    val country: String = "",

    @SerializedName("state")
    val status: String = "",

    @SerializedName("replica_is_returned")
    val replicaIsReturned: Boolean = false,

    @SerializedName("have_testimony")
    val haveTestimony: Boolean = false,

    @SerializedName("receiverId")
    val receiverId: Long,

    @SerializedName("receiver_user_name")
    val receiverName: String = "",

    @SerializedName("receiver_user_telephone")
    val receiverTelephone: String = "",

    @SerializedName("receiver_user_email")
    val receiverEmail: String = "",

    @SerializedName("replica_owner_user_id")
    val replicaOwnerId: Long? = null,

    @SerializedName("replica_owner_name_id")
    val replicaOwnerName: String = "",

    @SerializedName("replica_owner_user_email")
    val replicaOwnerEmail: String = "",

    @SerializedName("replica_owner_user_telephone")
    val replicaOwnerTelephone: String = "",

    @SerializedName("replica_code")
    val replicaCode: String = "",
)