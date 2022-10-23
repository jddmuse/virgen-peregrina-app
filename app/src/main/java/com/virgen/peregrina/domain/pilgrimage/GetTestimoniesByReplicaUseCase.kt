package com.virgen.peregrina.domain.pilgrimage

import android.util.Log
import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.data.repository.TestimonyRepository
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.base.BaseResultUseCase
import javax.inject.Inject

class GetTestimoniesByReplicaUseCase @Inject constructor(
    private val testimonyRepository: TestimonyRepository
) {

    companion object {
        private const val TAG = "GetTestimoniesByReplicaUseCase"
    }

    suspend operator fun invoke(replica_id: Long): BaseResultUseCase<List<TestimonyModel>> = try {
        when (val result = testimonyRepository.getTestimoniesByReplica(replica_id)) {
            is BaseResultRepository.Success -> {
                if (result.data.isNullOrEmpty())
                    BaseResultUseCase.NullOrEmptyData()
                else
                    BaseResultUseCase.Success(result.data)
            }
            is BaseResultRepository.Error -> {
                BaseResultUseCase.Error(result.exception)
            }
        }
    } catch (ex: Exception) {
        Log.e(TAG, "invoke(): Exception -> $ex")
        BaseResultUseCase.Error(ex)
    }


}