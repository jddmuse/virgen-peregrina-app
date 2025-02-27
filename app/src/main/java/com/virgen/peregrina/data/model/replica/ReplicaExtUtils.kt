package com.virgen.peregrina.data.model.replica

import com.virgen.peregrina.data.model.pilgrimage.parcelable
import com.virgen.peregrina.data.model.user.parselable

fun ReplicaLiteModel.parcelable(): ReplicaLiteParcelableModel {
    return ReplicaLiteParcelableModel(code, birthdate.toString())
}

fun ReplicaModel.parcelable(): ReplicaParcelableModel {
    return ReplicaParcelableModel(id, code, birthdate.toString(), user.parselable(), pilgrimages?.map { it.parcelable() })
}