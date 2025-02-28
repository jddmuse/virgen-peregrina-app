package com.virgen.peregrina.ui.replica.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.replica.ReplicaModel
import com.virgen.peregrina.domain.RunnerReplicas
import com.virgen.peregrina.ui.replica.EnumReplicaInputType
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponseRunner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReplicaListViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val preferencesManager: PreferencesManager,
    private val runnerReplicas: RunnerReplicas
) : ViewModel() {

    companion object {
        private const val TAG = "PeregrinacionViewModel"
    }

    private val _replicas = MutableLiveData<List<ReplicaModel>>()
    val replicas: LiveData<List<ReplicaModel>> get() = _replicas

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg

    /** Variables **/
    private var page = 0

    fun replicas(origin: EnumReplicaInputType = EnumReplicaInputType.API) {
        viewModelScope.launch {
            when (val response = runnerReplicas.invoke(page)) {
                is ResponseRunner.Success -> {
                    page++
                    val newData = response.data?.content ?: listOf()
                    _replicas.value = newData
                }
                is ResponseRunner.ApiError -> {
                    _errorMsg.value = response.message
                }
                is ResponseRunner.Error -> {
                    _errorMsg.value = resourceProvider.getStringResource(R.string.error_generic)
                }
                is ResponseRunner.NoInternetConnection -> {
                    _errorMsg.value = resourceProvider.getStringResource(R.string.error_no_internet_connection)
                }
                is ResponseRunner.NullOrEmptyData -> {
                    _errorMsg.value = resourceProvider.getStringResource(R.string.error_generic)
                }
            }
        }
    }

}