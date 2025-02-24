package com.virgen.peregrina.ui.replica.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReplicaDetailsViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _testimonies = MutableLiveData<List<TestimonyModel>>()
    val testimonies: LiveData<List<TestimonyModel>> get() = _testimonies

    var getReplicaId: Long = -1

    companion object {
        private const val TAG = "ReplicaDetailsViewModel"
    }

//    fun onCreate(replica_id: Long?) {
//        viewModelScope.launch {
//            try {
//                if (replica_id != null) {
//                    getReplicaId = replica_id
//                    when (val result = getTestimoniesByReplicaUseCase(getReplicaId)) {
//                        is BaseResponseRunner.Success -> {
//                            _testimonies.value = result.data ?: emptyList()
//                        }
//                        is BaseResponseRunner.Error -> {}
//                        is BaseResponseRunner.NullOrEmptyData -> {}
//                    }
//                }
//            } catch (ex: Exception) {
//                Log.e(TAG, "onCreate(): Exception -> $ex")
//            }
//        }
//    }


}