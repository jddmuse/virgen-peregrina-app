package com.virgen.peregrina.domain

import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.pilgrimage.PilgrimageModel
import com.virgen.peregrina.data.repository.PilgrimageRepository
import com.virgen.peregrina.domain.helper.RunnerHelper
import com.virgen.peregrina.util.provider.NetworkProvider
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponsePage
import com.virgen.peregrina.util.response.ResponseRunner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RunnerPilgrimages @Inject constructor(
    private val repository: PilgrimageRepository,
    private val networkProvider: NetworkProvider,
    private val resourceProvider: ResourceProvider,
    private val runnerHelper: RunnerHelper
) {

    private var lockInvoke = false

    suspend fun invoke(
        page: Int = 0,
        size: Int = 10,
        sort: String = ""
    ): ResponseRunner<ResponsePage<PilgrimageModel>> {
        if(lockInvoke) {
            return ResponseRunner.NullOrEmptyData(resourceProvider.getStringResource(R.string.locked_invoke_runner))
        }
        if (!networkProvider.isConnected()) {
            return ResponseRunner.NoInternetConnection()
        }
        lockInvoke = true
        CoroutineScope(Dispatchers.IO).launch {
            delay(5000L)
            lockInvoke = false
        }
        val response = repository.list(page, size, sort)
        return runnerHelper.response(response)
    }
}