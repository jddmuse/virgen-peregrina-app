package com.virgen.peregrina.data.repository

import android.util.Log
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.api.api_client.VirgenPeregrinaApiClient
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.request.CreateReplicaRequest
import com.virgen.peregrina.util.base.BaseResponseApi
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.getExceptionLog
import com.virgen.peregrina.util.provider.ResourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReplicaRepository @Inject constructor(
    private val virgenPeregrinaApiClient: VirgenPeregrinaApiClient,
    private val resourceProvider: ResourceProvider
) {

    companion object {
        private const val TAG = "ReplicaRepository"
    }

    suspend fun getAll(): BaseResultRepository<List<ReplicaModel>> =
        withContext(Dispatchers.IO) {
            try {
                val result = virgenPeregrinaApiClient.getAllReplicas()
                BaseResultRepository.Success(result.data)
            } catch (ex: Exception) {
                Log.e(TAG, "getAllReplicas(): Exception -> $ex")
                BaseResultRepository.Error(ex)
            }
        }

    suspend fun create(data: CreateReplicaRequest): BaseResultRepository<ReplicaModel> {
        return try {
            val result: BaseResponseApi<ReplicaModel> = virgenPeregrinaApiClient.createReplica(data)
            if(result.data != null)
                BaseResultRepository.Success(result.data)
            else
                BaseResultRepository.ApiError(result.message ?: resourceProvider.getStringResource(R.string.error_generic))
        } catch (ex:Exception) {
            getExceptionLog(TAG, "create", ex)
            BaseResultRepository.Error(ex)
        }
    }
}