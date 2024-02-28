package com.virgen.peregrina.domain.pilgrimage

import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.data.repository.TestimonyRepository
import com.virgen.peregrina.util.NetworkChecker
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.getExceptionLog
import javax.inject.Inject

class SendTestimonyUseCase @Inject constructor(
    private val testimonyRepository: TestimonyRepository,
    private val networkChecker: NetworkChecker
) {

    companion object {
        private const val TAG = "SendTestimonyUseCase"
    }

    suspend operator fun invoke(data: TestimonyModel): BaseResultUseCase<TestimonyModel> = try {
        if (networkChecker.isConnected()) {
            when (val result = testimonyRepository.sendTestimony(data)) {
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
        } else {
            BaseResultUseCase.NoInternetConnection()
        }
    } catch (ex: Exception) {
        getExceptionLog(TAG, "invoke", ex)
        BaseResultUseCase.Error(ex)
    }

}