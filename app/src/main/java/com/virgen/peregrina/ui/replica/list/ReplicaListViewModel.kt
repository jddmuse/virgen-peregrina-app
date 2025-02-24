package com.virgen.peregrina.ui.replica.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReplicaListViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    companion object {
        private const val TAG = "PeregrinacionViewModel"
    }

    private val _replicas = MutableLiveData<List<ReplicaModel>>()
    val replicas: LiveData<List<ReplicaModel>> get() = _replicas

    private val _yourReplicas = MutableLiveData<List<ReplicaModel>>()
    val yourReplicas: LiveData<List<ReplicaModel>> get() = _yourReplicas

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg

    fun onCreate() {
//        Log.i(TAG, "$METHOD_CALLED onCreate()")
//        try {
//            viewModelScope.launch {
//                when (val result = getAvailableReplicasUseCase()) {
//                    is BaseResponseRunner.Success -> {
//                        _replicas.value = result.data ?: emptyList()
//                    }
//                    is BaseResponseRunner.NullOrEmptyData -> {
//                        _errorMsg.value = resourceProvider
//                            .getStringResource(R.string.error_generic)
//                    }
//                    is BaseResponseRunner.Error -> {
//                        _errorMsg.value = resourceProvider
//                            .getStringResource(R.string.error_generic)
//                    }
//                }
//            }
//            getMyReplicas()
//        } catch (ex: Exception) {
//            getExceptionLog(TAG, "onCreate", ex)
//        }
    }

    private fun getMyReplicas() {
//        try {
//            preferencesManager.userSessionData?.let { sessionData ->
//                if(!sessionData.replicas.isNullOrEmpty()) {
//                    _yourReplicas.value = sessionData.replicas!!
//                }
//            }
//        } catch (ex:Exception) {
//            getExceptionLog(TAG, "getOwnReplicas", ex)
//        }
    }

    fun getMyReplicasFromApi() {
//        try {
//            val userId = preferencesManager.userSessionData?.let { it.id }
//            if(userId != null) {
//                viewModelScope.launch {
//                    when (val result = getAvailableReplicasUseCase.getReplicasByUserFromApi(userId)) {
//                        is BaseResponseRunner.Success -> {
//                            _yourReplicas.value = result.data ?: emptyList()
//                        }
//                        is BaseResponseRunner.NullOrEmptyData -> {
//                            _errorMsg.value = resourceProvider
//                                .getStringResource(R.string.error_generic)
//                        }
//                        is BaseResponseRunner.Error -> {
//                            _errorMsg.value = resourceProvider
//                                .getStringResource(R.string.error_generic)
//                        }
//                        is BaseResponseRunner.APIError -> {
//                            _errorMsg.value = result.message ?: resourceProvider
//                                .getStringResource(R.string.error_generic)
//                        }
//                    }
//                }
//            }
//        } catch (ex:Exception) {
//            getExceptionLog(TAG, "getMyReplicasFromApi", ex)
//        }
    }

}