package com.virgen.peregrina.domain.pilgrimage

import android.util.Log
import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.repository.UserRepository
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.base.BaseResultUseCase
import javax.inject.Inject

class GetAllPilgrimsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    companion object {
        private const val TAG = "GetAllPilgrimsUseCase"
    }

    suspend operator fun invoke(): BaseResultUseCase<List<UserModel>> = try {
        Log.i(TAG, "$METHOD_CALLED invoke()")
        when (val result = userRepository.getAllPilgrims()) {
            is BaseResultRepository.Success -> {
                BaseResultUseCase.Success(result.data)
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