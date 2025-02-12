package com.virgen.peregrina.data.repository

import com.virgen.peregrina.data.api.service.VirgenPeregrinaApiClient
import javax.inject.Inject

class PilgrimageRepository @Inject constructor(
    private val virgenPeregrinaApiClient: VirgenPeregrinaApiClient
) {

    companion object {
        private const val TAG = "PilgrimageRepository"
    }

//    suspend fun create(data: CreatePilgrimageRequest): BaseResponseRepository<PilgrimageModel> =
//        withContext(Dispatchers.IO) {
//            try {
//                val result = virgenPeregrinaApiClient.createPilgrimage(data)
//                BaseResponseRepository.Success(result.data)
//            } catch (ex: Exception) {
//                Log.e(TAG, "create(): Exception -> $ex")
//                BaseResponseRepository.Error(ex)
//            }
//        }
//
//    suspend fun getAll(): BaseResponseRepository<List<GetPilgrimagesResponse>> =
//        withContext(Dispatchers.IO) {
//            try {
//                val result = virgenPeregrinaApiClient.getAllPilgrimages()
//                BaseResponseRepository.Success(result.data)
//            } catch (ex: Exception) {
//                getExceptionLog(TAG, "getAll", ex)
//                BaseResponseRepository.Error(ex)
//            }
//        }


}