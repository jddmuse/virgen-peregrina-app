package com.virgen.peregrina.data.repository

import android.util.Log
import com.example.virgen_peregrina_app.R
import com.google.gson.Gson
import com.virgen.peregrina.data.api.service.VirgenPeregrinaApiClient
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.request.CreateReplicaRequest
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResponseRepository
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

    suspend fun getAll(): BaseResponseRepository<List<ReplicaModel>> =
        withContext(Dispatchers.IO) {
            try {
                val result = virgenPeregrinaApiClient.getAllReplicas()
                BaseResponseRepository.Success(result.data)
            } catch (ex: Exception) {
                Log.e(TAG, "getAllReplicas(): Exception -> $ex")
                BaseResponseRepository.Error(ex)
            }
        }

    suspend fun create(data: CreateReplicaRequest): BaseResponseRepository<ReplicaModel> {
        return try {
            Log.i(TAG, "$METHOD_CALLED invoke() PARAMS: ${Gson().toJson(data)}")
            val result = virgenPeregrinaApiClient.createReplica(data)
            if(result.data != null)
                BaseResponseRepository.Success(result.data)
            else
                BaseResponseRepository.ApiError(result.message ?: resourceProvider.getStringResource(R.string.error_generic))
        } catch (ex:Exception) {
            getExceptionLog(TAG, "create", ex)
            BaseResponseRepository.Error(ex)
        }
    }

    suspend fun getReplicasByUserFromApi(id: Long): BaseResponseRepository<List<ReplicaModel>> {
        return try {
            Log.i(TAG, "getReplicasByUserFromApi() PARAMS: $id")
            val result = virgenPeregrinaApiClient.getAllReplicasByUser(id)
            if(result.data != null)
                BaseResponseRepository.Success(result.data)
            else
                BaseResponseRepository.ApiError(result.message ?: resourceProvider.getStringResource(R.string.error_generic))
        } catch (ex:Exception) {
            getExceptionLog(TAG, "getReplicasByUserFromApi", ex)
            BaseResponseRepository.Error(ex)
        }
    }

}