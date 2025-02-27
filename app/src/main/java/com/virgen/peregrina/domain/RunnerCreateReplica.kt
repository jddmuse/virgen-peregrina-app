package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.replica.ReplicaModel
import com.virgen.peregrina.data.repository.ReplicaRepository
import com.virgen.peregrina.data.request.CreateReplicaRequest
import com.virgen.peregrina.domain.helper.RunnerHelper
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerCreateReplica @Inject constructor(
    private val runnerHelper: RunnerHelper,
    private val repository: ReplicaRepository
){


    suspend fun invoke(data: CreateReplicaRequest): ResponseRunner<ReplicaModel> {
        val response = repository.create(data)
        return runnerHelper.response(response)
    }

}