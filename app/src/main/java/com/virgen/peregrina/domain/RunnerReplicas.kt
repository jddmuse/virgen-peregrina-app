package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerReplicas @Inject constructor() {


    suspend fun invoke(page: Int, size: Int, sort: String): ResponseRunner<List<ReplicaModel>> {
        return ResponseRunner.NullOrEmptyData()
    }
}