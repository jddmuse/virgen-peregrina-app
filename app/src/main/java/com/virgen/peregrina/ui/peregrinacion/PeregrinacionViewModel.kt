package com.virgen.peregrina.ui.peregrinacion

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.domain.peregrinacion.GetAvailableReplicasUseCase
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeregrinacionViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getAvailableReplicasUseCase: GetAvailableReplicasUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "PeregrinacionViewModel"
    }

    private val _replicas = MutableLiveData<List<ReplicaModel>>()
    val replicas: LiveData<List<ReplicaModel>> get() = _replicas

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg

    fun onCreate() {
        Log.i(TAG, "$METHOD_CALLED onCreate()")
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
    }

}