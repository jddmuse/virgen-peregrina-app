package com.virgen.peregrina.data.repository

import android.util.Log
import com.virgen.peregrina.data.api.service.VirgenPeregrinaApiClient
import com.virgen.peregrina.data.model.pilgrimage.PilgrimageLiteModel
import com.virgen.peregrina.data.model.pilgrimage.PilgrimageModel
import com.virgen.peregrina.data.repository.helper.RepositoryHelper
import com.virgen.peregrina.data.request.CreatePilgrimageRequest
import com.virgen.peregrina.util.response.ResponsePage
import com.virgen.peregrina.util.response.ResponseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PilgrimageRepository @Inject constructor(
    private val apiService: VirgenPeregrinaApiClient,
    private val repositoryHelper: RepositoryHelper
) {

    companion object {
        private const val TAG = "PilgrimageRepository"
    }

    suspend fun list(page: Int, size: Int, sort: String): ResponseRepository<ResponsePage<PilgrimageModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.listPilgrimages(page, size, sort)
                repositoryHelper.response(response)
            } catch (ex: Exception) {
                Log.e(TAG, "list(): Exception -> $ex")
                ResponseRepository.Error(ex)
            }
        }
    }

    suspend fun create(data: CreatePilgrimageRequest): ResponseRepository<PilgrimageLiteModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.createPilgrimage(data)
                repositoryHelper.response(response)
            } catch (ex: Exception) {
                Log.e(TAG, "create(): Exception -> $ex")
                ResponseRepository.Error(ex)
            }
        }
    }

}