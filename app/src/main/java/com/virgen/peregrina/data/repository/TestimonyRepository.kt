package com.virgen.peregrina.data.repository

import com.virgen.peregrina.data.api.service.VirgenPeregrinaApiClient
import javax.inject.Inject

class TestimonyRepository @Inject constructor(
    private val virgenPeregrinaApiClient: VirgenPeregrinaApiClient
) {

    companion object {
        private const val TAG = "TestimonyRepository"
    }

//    suspend fun getTestimoniesByReplica(
//        replica_id: Long
//    ): BaseResponseRepository<List<TestimonyModel>> = withContext(Dispatchers.IO) {
//        try {
//            val result = virgenPeregrinaApiClient.getTestimoniesByReplica(replica_id)
//            BaseResponseRepository.Success(result.data)
//        } catch (ex: Exception) {
//            Log.e(TAG, "getAllReplicas(): Exception -> $ex")
//            BaseResponseRepository.Error(ex)
//        }
//    }
//
//    suspend fun sendTestimony(
//        data: TestimonyModel
//    ): BaseResponseRepository<TestimonyModel> = withContext(Dispatchers.IO) {
//        try {
//            val result = virgenPeregrinaApiClient.sendTestimony(data)
//            BaseResponseRepository.Success(result.data)
//        } catch (ex: Exception) {
//            getExceptionLog(TAG, "sendTestimony", ex)
//            BaseResponseRepository.Error(ex)
//        }
//    }


}