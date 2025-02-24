package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.request.CreatePilgrimageRequest
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerCreatePilgrimage @Inject constructor() {


    suspend fun invoke(data: CreatePilgrimageRequest): ResponseRunner<PilgrimageModel> {
        return ResponseRunner.NullOrEmptyData()
    }

}