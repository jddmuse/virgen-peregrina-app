package com.virgen.peregrina.domain.pilgrimage

import android.util.Log
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.repository.PilgrimageRepository
import com.virgen.peregrina.data.request.CreatePilgrimageRequest
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.base.BaseResultUseCase
import javax.inject.Inject

class CreatePilgrimageUseCase @Inject constructor(
    private val pilgrimageRepository: PilgrimageRepository
) {

    companion object {
        private const val TAG = "CreatePilgrimageUseCase"
    }

    suspend operator fun invoke(
        data: CreatePilgrimageRequest
    ): BaseResultUseCase<PilgrimageModel> = try {
        when (val result = pilgrimageRepository.create(data)) {
            is BaseResultRepository.Success -> {
                if (result.data != null)
                    BaseResultUseCase.Success(result.data)
                else
                    BaseResultUseCase.NullOrEmptyData()
            }
            is BaseResultRepository.Error -> {
                BaseResultUseCase.Error(result.exception)
            }
            is BaseResultRepository.ApiError -> {
                BaseResultUseCase.APIError(null, result.message)
            }
        }
    } catch (ex: Exception) {
        Log.e(TAG, "invoke(): Exception -> $ex")
        BaseResultUseCase.Error(ex)
    }


}