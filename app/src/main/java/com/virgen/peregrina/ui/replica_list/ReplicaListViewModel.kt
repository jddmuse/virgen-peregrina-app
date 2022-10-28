package com.virgen.peregrina.ui.replica_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.google.gson.Gson
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.response.LoginResponse
import com.virgen.peregrina.domain.pilgrimage.GetAvailableReplicasUseCase
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.getExceptionLog
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.GlobalProvider
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReplicaListViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getAvailableReplicasUseCase: GetAvailableReplicasUseCase,
    private val preferencesManager: PreferencesManager,
    private val globalProvider: GlobalProvider
) : ViewModel() {

    companion object {
        private const val TAG = "PeregrinacionViewModel"
    }

    private val _userData = MutableLiveData<LoginResponse>()
    val userData: LiveData<LoginResponse> get() = _userData

    private val _replicas = MutableLiveData<List<ReplicaModel>>()
    val replicas: LiveData<List<ReplicaModel>> get() = _replicas

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg

    fun onCreate() {
        Log.i(TAG, "$METHOD_CALLED onCreate()")
        try {
            _userData.value = globalProvider.userData
            viewModelScope.launch {
                when (val result = getAvailableReplicasUseCase()) {
                    is BaseResultUseCase.Success -> {
                        _replicas.value = result.data ?: emptyList()
                    }
                    is BaseResultUseCase.NullOrEmptyData -> {
                        _errorMsg.value = resourceProvider
                            .getStringResource(R.string.error_generic)
                    }
                    is BaseResultUseCase.Error -> {
                        _errorMsg.value = resourceProvider
                            .getStringResource(R.string.error_generic)
                    }
                }
            }
        } catch (ex: Exception) {
            getExceptionLog(TAG, "onCreate", ex)
        }
    }

}