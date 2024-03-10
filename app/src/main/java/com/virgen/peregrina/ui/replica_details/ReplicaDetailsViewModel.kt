package com.virgen.peregrina.ui.replica_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.domain.pilgrimage.GetTestimoniesByReplicaUseCase
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReplicaDetailsViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getTestimoniesByReplicaUseCase: GetTestimoniesByReplicaUseCase
) : ViewModel() {

    private val _testimonies = MutableLiveData<List<TestimonyModel>>()
    val testimonies: LiveData<List<TestimonyModel>> get() = _testimonies

    var getReplicaId: Long = -1

    companion object {
        private const val TAG = "ReplicaDetailsViewModel"
    }

    fun onCreate(replica_id: Long?) {
        viewModelScope.launch {
            try {
                if (replica_id != null) {
                    getReplicaId = replica_id
                    when (val result = getTestimoniesByReplicaUseCase(getReplicaId)) {
                        is BaseResultUseCase.Success -> {
                            _testimonies.value = result.data ?: emptyList()
                        }
                        is BaseResultUseCase.Error -> {}
                        is BaseResultUseCase.NullOrEmptyData -> {}
                    }
                }
            } catch (ex: Exception) {
                Log.e(TAG, "onCreate(): Exception -> $ex")
            }
        }
    }


}