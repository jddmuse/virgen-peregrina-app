package com.virgen.peregrina.data.model

import java.util.Date

data class PilgrimageModel(
    val replicaId: Long,
    val userId: Long,
    val startDate: Date,
    val endDate: Date,
    val intention: String
)