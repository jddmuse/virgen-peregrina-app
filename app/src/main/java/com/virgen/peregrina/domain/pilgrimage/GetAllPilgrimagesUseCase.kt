package com.virgen.peregrina.domain.pilgrimage

import android.util.Log
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.repository.PilgrimageRepository
import com.virgen.peregrina.data.response.GetPilgrimagesResponse
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.getExceptionLog
import javax.inject.Inject

class GetAllPilgrimagesUseCase @Inject constructor(
    private val pilgrimageRepository: PilgrimageRepository
) {

    companion object {
        private const val TAG = "GetAllPilgrimages"
    }

    suspend operator fun invoke(): BaseResultUseCase<List<GetPilgrimagesResponse>> = try {
        when (val result = pilgrimageRepository.getAll()) {
            is BaseResultRepository.Error -> {
                BaseResultUseCase.Error(result.exception)
            }
            is BaseResultRepository.Success -> {
                Log.i(TAG, "result=${result.data}")
                BaseResultUseCase.Success(result.data)
            }
            is BaseResultRepository.ApiError -> {
                BaseResultUseCase.APIError(null, result.message)
            }
        }
    } catch (ex: Exception) {
        getExceptionLog(TAG, "invoke", ex)
        BaseResultUseCase.Error(ex)
    }

}