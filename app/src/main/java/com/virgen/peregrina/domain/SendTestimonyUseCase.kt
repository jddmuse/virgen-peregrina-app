package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.data.repository.TestimonyRepository
import com.virgen.peregrina.util.provider.NetworkProvider
import com.virgen.peregrina.util.base.BaseResponseRepository
import com.virgen.peregrina.util.base.BaseResponseRunner
import com.virgen.peregrina.util.getExceptionLog
import javax.inject.Inject

class SendTestimonyUseCase @Inject constructor(
    private val testimonyRepository: TestimonyRepository,
    private val networkProvider: NetworkProvider
) {

    companion object {
        private const val TAG = "SendTestimonyUseCase"
    }

    suspend operator fun invoke(data: TestimonyModel): BaseResponseRunner<TestimonyModel> = try {
        if (networkProvider.isConnected()) {
            when (val result = testimonyRepository.sendTestimony(data)) {
                is BaseResponseRepository.Success -> {
                    BaseResponseRunner.Success(result.data)
                }
                is BaseResponseRepository.Error -> {
                    BaseResponseRunner.Error(result.exception)
                }
                is BaseResponseRepository.ApiError -> {
                    BaseResponseRunner.APIError(null, result.message)
                }
            }
        } else {
            BaseResponseRunner.NoInternetConnection()
        }
    } catch (ex: Exception) {
        getExceptionLog(TAG, "invoke", ex)
        BaseResponseRunner.Error(ex)
    }

}