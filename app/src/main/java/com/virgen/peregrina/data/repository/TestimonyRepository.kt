package com.virgen.peregrina.data.repository

import android.util.Log
import com.virgen.peregrina.data.api.api_client.VirgenPeregrinaApiClient
import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.util.base.BaseResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TestimonyRepository @Inject constructor(
    private val virgenPeregrinaApiClient: VirgenPeregrinaApiClient
) {

    companion object {
        private const val TAG = "TestimonyRepository"
    }

    suspend fun getTestimoniesByReplica(
        replica_id: Long
    ): BaseResultRepository<List<TestimonyModel>> = withContext(Dispatchers.IO) {
        try {
            val result = virgenPeregrinaApiClient.getTestimoniesByReplica(replica_id)
            BaseResultRepository.Success(result.data)
        } catch (ex: Exception) {
            Log.e(TAG, "getAllReplicas(): Exception -> $ex")
            BaseResultRepository.Error(ex)
        }
    }


}