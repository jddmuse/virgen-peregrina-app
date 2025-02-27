package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.pilgrimage.PilgrimageModel
import com.virgen.peregrina.data.repository.PilgrimageRepository
import com.virgen.peregrina.data.request.CreatePilgrimageRequest
import com.virgen.peregrina.domain.helper.RunnerHelper
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerCreatePilgrimage @Inject constructor(
    private val repository: PilgrimageRepository,
    private val runnerHelper: RunnerHelper
) {


    suspend fun invoke(data: CreatePilgrimageRequest): ResponseRunner<PilgrimageModel> {
        val response = repository.create(data)
        return runnerHelper.response(response)
    }

}