package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.request.CreateReplicaRequest
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerCreateReplica @Inject constructor(){


    suspend fun invoke(data: CreateReplicaRequest): ResponseRunner<ReplicaModel> {
        return ResponseRunner.NullOrEmptyData()
    }

}