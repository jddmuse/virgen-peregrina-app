package com.virgen.peregrina.data.model

import java.time.LocalDate

data class PilgrimageModel(
    val replicaId: Long,
    val userId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val intention: String
)