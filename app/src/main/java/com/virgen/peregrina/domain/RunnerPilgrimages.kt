package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.repository.PilgrimageRepository
import com.virgen.peregrina.domain.helper.RunnerHelper
import com.virgen.peregrina.util.provider.NetworkProvider
import com.virgen.peregrina.util.response.ResponsePage
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerPilgrimages @Inject constructor(
    private val repository: PilgrimageRepository,
    private val networkProvider: NetworkProvider,
    private val runnerHelper: RunnerHelper
) {

    suspend fun invoke(
        page: Int = 0,
        size: Int = 10,
        sort: String = ""
    ): ResponseRunner<ResponsePage<PilgrimageModel>> {
        if (!networkProvider.isConnected())
            return ResponseRunner.NoInternetConnection()
        val response = repository.list(page, size, sort)
        return runnerHelper.response(response)
    }
}