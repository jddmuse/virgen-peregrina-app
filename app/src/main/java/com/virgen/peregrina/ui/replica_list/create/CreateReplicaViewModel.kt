package com.virgen.peregrina.ui.replica_list.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.request.CreateReplicaRequest
import com.virgen.peregrina.domain.replica.CreateReplicaUseCase
import com.virgen.peregrina.ui.register.EnumReplicaDialogInputType
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateReplicaViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val createReplicaUseCase: CreateReplicaUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private var setCode: String = EMPTY_STRING
    private var setYear: String = EMPTY_STRING
    private var setRepairRequired: Boolean = false
    private var setContainer: Boolean = false
    private var setState: String = EMPTY_STRING

    private val _dispatchSuccessful = MutableLiveData<Pair<Boolean, String>>()
    val dispatchSuccessful: LiveData<Pair<Boolean, String>> get() = _dispatchSuccessful

    private val _loading = MutableLiveData<Pair<Boolean, String>>()
    val loading: LiveData<Pair<Boolean, String>> get() = _loading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _infoMessage = MutableLiveData<String>()
    val infoMessage: LiveData<String> get() = _infoMessage

    companion object {
        private const val TAG = "CreateReplicaViewModel"
    }

    fun onValueChanged(value: Any?, inputType: EnumReplicaDialogInputType) {
        try {
            Log.i(TAG, "METHOD CALLED: onValueChanged() PARAMS: $value, $inputType")
            when (inputType) {
                EnumReplicaDialogInputType.CODE -> {
                    val valueAux = value?.toString() ?: EMPTY_STRING
                    setCode = valueAux
                }
                EnumReplicaDialogInputType.DATE -> {
                    val valueAux = value?.toString() ?: EMPTY_STRING
                    setYear = valueAux
                }
                EnumReplicaDialogInputType.REPAIR_REQUIRED -> {
                    val valueAux = value as Boolean? ?: false
                    setRepairRequired = valueAux
                }
                EnumReplicaDialogInputType.STATE -> {
                    val valueAux = value?.toString()
                    setState = valueAux ?: ""
                }
                EnumReplicaDialogInputType.CONTAINER -> {
                    val valueAux = value as Boolean? ?: false
                    setContainer = valueAux
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    fun dispatch() {
        _loading.value = Pair(true, EMPTY_STRING)
        val userId = preferencesManager.userSessionData?.id ?: return
        val request = CreateReplicaRequest(
            code = setCode,
            year = setYear,
            requireRepair = setRepairRequired,
            ownerId = userId.toString(),
            container = setContainer,
            status = setState
        )
        viewModelScope.launch {
            when(val result = createReplicaUseCase(request)) {
                is BaseResultUseCase.Success -> {
                    _loading.value = Pair(false, EMPTY_STRING)
                    _dispatchSuccessful.value = Pair(true, resourceProvider.getStringResource(R.string.label_replica_created_successfull))
                }
                is BaseResultUseCase.Error -> {
                    _loading.value = Pair(false, EMPTY_STRING)
                    _dispatchSuccessful.value = Pair(false, resourceProvider.getStringResource(R.string.error_generic))
                }
                is BaseResultUseCase.APIError -> {
                    _loading.value = Pair(false, EMPTY_STRING)
                    val message = result.message ?: resourceProvider.getStringResource(R.string.error_generic)
                    _dispatchSuccessful.value = Pair(false, message)
                }
            }
        }
    }

}