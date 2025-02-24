package com.virgen.peregrina.ui.replica.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.domain.RunnerReplicas
import com.virgen.peregrina.ui.replica.EnumReplicaInputType
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
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

    fun replicas(origin: EnumReplicaInputType = EnumReplicaInputType.API) {
//        viewModelScope.launch {
//            val response = runnerReplicas.invoke()
//        }
    }

}