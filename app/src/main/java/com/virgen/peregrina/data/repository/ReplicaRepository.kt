package com.virgen.peregrina.data.repository

import com.virgen.peregrina.data.api.service.VirgenPeregrinaApiClient
import com.virgen.peregrina.util.provider.ResourceProvider
import javax.inject.Inject

class ReplicaRepository @Inject constructor(
    private val virgenPeregrinaApiClient: VirgenPeregrinaApiClient,
    private val resourceProvider: ResourceProvider
) {

    companion object {
        private const val TAG = "ReplicaRepository"
    }

//    suspend fun getAll(): BaseResponseRepository<List<ReplicaModel>> =
//        withContext(Dispatchers.IO) {
//            try {
//                val result = virgenPeregrinaApiClient.getAllReplicas()
//                BaseResponseRepository.Success(result.data)
//            } catch (ex: Exception) {
//                Log.e(TAG, "getAllReplicas(): Exception -> $ex")
//                BaseResponseRepository.Error(ex)
//            }
//        }
//
//    suspend fun create(data: CreateReplicaRequest): BaseResponseRepository<ReplicaModel> {
//        return try {
//            Log.i(TAG, "$METHOD_CALLED invoke() PARAMS: ${Gson().toJson(data)}")
//            val result = virgenPeregrinaApiClient.createReplica(data)
//            if(result.data != null)
//                BaseResponseRepository.Success(result.data)
//            else
//                BaseResponseRepository.ApiError(result.message ?: resourceProvider.getStringResource(R.string.error_generic))
//        } catch (ex:Exception) {
//            getExceptionLog(TAG, "create", ex)
//            BaseResponseRepository.Error(ex)
//        }
//    }

//    suspend fun getReplicasByUserFromApi(id: Long): BaseResponseRepository<List<ReplicaModel>> {
//        return try {
//            Log.i(TAG, "getReplicasByUserFromApi() PARAMS: $id")
//            val result = virgenPeregrinaApiClient.getAllReplicasByUser(id)
//            if(result.data != null)
//                BaseResponseRepository.Success(result.data)
//            else
//                BaseResponseRepository.ApiError(result.message ?: resourceProvider.getStringResource(R.string.error_generic))
//        } catch (ex:Exception) {
//            getExceptionLog(TAG, "getReplicasByUserFromApi", ex)
//            BaseResponseRepository.Error(ex)
//        }
//    }

}