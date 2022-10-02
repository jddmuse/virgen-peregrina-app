package com.virgen.peregrina.data.repository

import android.util.Log
import com.virgen.peregrina.data.api.api_client.VirgenPeregrinaApiClient
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.util.base.BaseResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReplicaRepository @Inject constructor(
    private val virgenPeregrinaApiClient: VirgenPeregrinaApiClient
) {

    companion object {
        private const val TAG = "ReplicaRepository"
    }

    suspend fun getAllReplicas(): BaseResultRepository<List<ReplicaModel>> =
        withContext(Dispatchers.IO) {
            try {
                val result = virgenPeregrinaApiClient.getAllReplicas()
                BaseResultRepository.Success(result.data)
            } catch (ex: Exception) {
                Log.e(TAG, "getAllReplicas(): Exception -> $ex")
                BaseResultRepository.Error(ex)
            }
        }


}