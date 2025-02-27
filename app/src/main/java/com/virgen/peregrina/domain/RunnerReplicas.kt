package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.replica.ReplicaModel
import com.virgen.peregrina.data.repository.ReplicaRepository
import com.virgen.peregrina.domain.helper.RunnerHelper
import com.virgen.peregrina.util.provider.NetworkProvider
import com.virgen.peregrina.util.response.ResponsePage
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerReplicas @Inject constructor(
    private val repository: ReplicaRepository,
    private val networkProvider: NetworkProvider,
    private val runnerHelper: RunnerHelper
) {


    suspend fun invoke(
        page: Int = 0,
        size: Int = 10,
        sort: String = ""
    ): ResponseRunner<ResponsePage<ReplicaModel>> {
        if (!networkProvider.isConnected())
            return ResponseRunner.NoInternetConnection()
        val response = repository.list(page, size, sort)
        return runnerHelper.response(response)
    }
}