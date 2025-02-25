package com.virgen.peregrina.data.model

import com.virgen.peregrina.ui.pilgrimage.util.PilgrimageParcelableModel
import java.time.LocalDate

data class PilgrimageModel(
    val replicaId: Long,
    val userId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val intention: String
)

fun PilgrimageModel.parcelable(): PilgrimageParcelableModel {
    return PilgrimageParcelableModel(replicaId, userId, startDate.toString(), endDate.toString(), intention)
}