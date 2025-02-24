package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerPilgrimages @Inject constructor() {


    suspend fun invoke(page: Int, size: Int, sort: String): ResponseRunner<List<PilgrimageModel>> {
        return ResponseRunner.NullOrEmptyData()
    }
}