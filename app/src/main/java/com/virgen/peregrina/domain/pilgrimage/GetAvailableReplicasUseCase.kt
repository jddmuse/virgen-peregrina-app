package com.virgen.peregrina.domain.pilgrimage

import android.util.Log
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.repository.ReplicaRepository
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.base.BaseResultUseCase
import javax.inject.Inject

class GetAvailableReplicasUseCase @Inject constructor(
    private val replicaRepository: ReplicaRepository
) {

    companion object {
        private const val TAG = "GetAvailableReplicasUseCase"
    }

    suspend operator fun invoke(): BaseResultUseCase<List<ReplicaModel>> = try {
        when (val result = replicaRepository.getAllReplicas()) {
            is BaseResultRepository.Success -> {
                Log.i(TAG, "result.data=${result.data}")
                BaseResultUseCase.Success(result.data)
            }
            is BaseResultRepository.Error -> {
                BaseResultUseCase.NullOrEmptyData()
            }
        }
    } catch (ex: Exception) {
        Log.e(TAG, "invoke(): Exception -> $ex")
        BaseResultUseCase.Error(ex)
    }

}