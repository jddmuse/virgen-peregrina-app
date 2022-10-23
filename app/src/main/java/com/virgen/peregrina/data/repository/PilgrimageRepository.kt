package com.virgen.peregrina.data.repository

import android.util.Log
import com.virgen.peregrina.data.api.api_client.VirgenPeregrinaApiClient
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.util.base.BaseResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PilgrimageRepository @Inject constructor(
    private val virgenPeregrinaApiClient: VirgenPeregrinaApiClient
) {

    companion object {
        private const val TAG = "PilgrimageRepository"
    }

    suspend fun create(pilgrimageModel: PilgrimageModel): BaseResultRepository<PilgrimageModel> =
        withContext(Dispatchers.IO) {
            try {
                val result = virgenPeregrinaApiClient.createPilgrimage(pilgrimageModel)
                BaseResultRepository.Success(result.data)
            } catch (ex: Exception) {
                Log.e(TAG, "create(): Exception -> $ex")
                BaseResultRepository.Error(ex)
            }
        }


}