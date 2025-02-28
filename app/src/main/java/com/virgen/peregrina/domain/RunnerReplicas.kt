package com.virgen.peregrina.domain

import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.replica.ReplicaModel
import com.virgen.peregrina.data.repository.ReplicaRepository
import com.virgen.peregrina.domain.helper.RunnerHelper
import com.virgen.peregrina.util.provider.NetworkProvider
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponsePage
import com.virgen.peregrina.util.response.ResponseRunner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RunnerReplicas @Inject constructor(
    private val repository: ReplicaRepository,
    private val networkProvider: NetworkProvider,
    private val resourceProvider: ResourceProvider,
    private val runnerHelper: RunnerHelper
) {

    private var lockInvoke = false

    suspend fun invoke(
        page: Int = 0,
        size: Int = 10,
        sort: String = ""
    ): ResponseRunner<ResponsePage<ReplicaModel>> {
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