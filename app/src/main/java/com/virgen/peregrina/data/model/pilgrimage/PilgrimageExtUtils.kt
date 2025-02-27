package com.virgen.peregrina.data.model.pilgrimage

import com.virgen.peregrina.data.model.replica.parcelable
import com.virgen.peregrina.data.model.user.parselable

fun PilgrimageModel.parcelable(): PilgrimageParcelableModel {
    return PilgrimageParcelableModel(replica.parcelable(), user.parselable(), startDate.toString(), endDate.toString(), intention)
}

fun PilgrimageOnlyDatesModel.parcelable(): PilgrimageOnlyDatesParcelableModel {
    return PilgrimageOnlyDatesParcelableModel(startDate.toString(), endDate.toString())
}