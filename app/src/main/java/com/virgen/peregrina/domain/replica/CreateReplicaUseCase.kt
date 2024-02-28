package com.virgen.peregrina.domain.replica

import android.util.Log
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.repository.ReplicaRepository
import com.virgen.peregrina.data.request.CreateReplicaRequest
import com.virgen.peregrina.domain.signup.SignUpWithVirgenPeregrinaUseCase
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.getExceptionLog
import javax.inject.Inject

class CreateReplicaUseCase @Inject constructor(
 private val replicaRepository: ReplicaRepository
){

    companion object {
        private const val TAG = "CreateReplicaUseCase"
    }

    suspend operator fun invoke(data: CreateReplicaRequest): BaseResultUseCase<ReplicaModel> {
        return try {
            Log.i(TAG, "$METHOD_CALLED invoke() PARAMS: $data")
            when(val result = replicaRepository.create(data)) {
                is BaseResultRepository.Success -> { BaseResultUseCase.Success(result.data) }
                is BaseResultRepository.Error -> { BaseResultUseCase.Error(result.exception) }
                is BaseResultRepository.ApiError -> { BaseResultUseCase.APIError(null, result.message) }
            }
        } catch (ex:Exception) {
            getExceptionLog(TAG, "invoke", ex)
            BaseResultUseCase.Error(ex)
        }
    }

}