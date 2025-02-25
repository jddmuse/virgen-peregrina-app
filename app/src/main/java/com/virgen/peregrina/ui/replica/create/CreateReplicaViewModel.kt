package com.virgen.peregrina.ui.replica.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.request.CreateReplicaRequest
import com.virgen.peregrina.domain.RunnerCreateReplica
import com.virgen.peregrina.ui.register.enumerator.EnumReplicaDialogInputType
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponseRunner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CreateReplicaViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val preferencesManager: PreferencesManager,
    private val runnerCreateReplica: RunnerCreateReplica
) : ViewModel() {

    private var setCode: String = EMPTY_STRING
    private var setYear: Int = -1

    private val _createReplicaSuccess = MutableLiveData<Pair<Boolean, String>>()
    val createReplicaSuccess: LiveData<Pair<Boolean, String>> get() = _createReplicaSuccess

    private val _loading = MutableLiveData<Pair<Boolean, String>>()
    val loading: LiveData<Pair<Boolean, String>> get() = _loading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _infoMessage = MutableLiveData<String>()
    val infoMessage: LiveData<String> get() = _infoMessage

    companion object {
        private const val TAG = "CreateReplicaViewModel"
    }

    fun valueChanged(value: String, inputType: EnumReplicaDialogInputType) {
        try {
            Log.i(TAG, "METHOD CALLED: onValueChanged() PARAMS: $value, $inputType")
            when (inputType) {
                EnumReplicaDialogInputType.CODE -> {
                    setCode = value.uppercase()
                }
                EnumReplicaDialogInputType.DATE -> {
                    setYear = if(value.toInt() in 1980..Calendar.getInstance().get(Calendar.YEAR)) value.toInt() else -1
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    fun create() {
        if(isValid()) {
            val request = CreateReplicaRequest(setCode, setYear.toString(), preferencesManager.userId)
            viewModelScope.launch {
                _loading.value = Pair(true, "")
                when(val response = runnerCreateReplica.invoke(request)) {
                    is ResponseRunner.Success -> {
                        _loading.value = Pair(false, "")
                        _createReplicaSuccess.value = Pair(true, "")
                    }
                    is ResponseRunner.ApiError -> {
                        _loading.value = Pair(false, "")
                        _errorMessage.value = StringBuilder()
                            .append(response.message ?: "")
                            .append("\n${response.error}").toString()
                    }
                    is ResponseRunner.NoInternetConnection -> {
                        _loading.value = Pair(false, "")
                        _errorMessage.value = resourceProvider.getStringResource(R.string.error_no_internet_connection)
                    }
                    else -> {
                        _loading.value = Pair(false, "")
                        _errorMessage.value = resourceProvider.getStringResource(R.string.error_generic)
                    }
                }
            }
        }
    }

    private fun isValid(): Boolean {
        return setCode.isNotEmpty() && setYear != -1 && preferencesManager.userId != -1L
    }

}