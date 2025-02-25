package com.virgen.peregrina.data.repository

import com.virgen.peregrina.data.api.service.VirgenPeregrinaApiClient
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.repository.helper.RepositoryHelper
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReplicaRepository @Inject constructor(
    private val apiService: VirgenPeregrinaApiClient,
    private val repositoryHelper: RepositoryHelper
) {

    companion object {
        private const val TAG = "ReplicaRepository"
    }

    suspend fun list(page: Int, size: Int, sort: String): ResponseRepository<List<ReplicaModel>> {
        return withContext(Dispatchers.IO) {
            val response = apiService.listReplicas(page, size, sort)
            return@withContext repositoryHelper.response(response)
        }
    }
}