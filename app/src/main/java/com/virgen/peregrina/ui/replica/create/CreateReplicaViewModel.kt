package com.virgen.peregrina.ui.replica.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.request.CreateReplicaRequest
import com.virgen.peregrina.domain.RunnerCreateReplica
import com.virgen.peregrina.ui.replica.EnumReplicaInputType
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponseRunner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CreateReplicaViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val preferencesManager: PreferencesManager,
    private val runnerCreateReplica: RunnerCreateReplica
) : ViewModel() {

    private var setCode: String = ""
    private var setYear: LocalDate? = null

    private val _createReplicaSuccess = MutableLiveData<Pair<Boolean, String>>()
    val createReplicaSuccess: LiveData<Pair<Boolean, String>> get() = _createReplicaSuccess

    private val _errorEditText = MutableLiveData<Pair<EnumReplicaInputType, String?>>()
    val errorEditText: LiveData<Pair<EnumReplicaInputType, String?>> get() = _errorEditText

    private val _loading = MutableLiveData<Pair<Boolean, String>>()
    val loading: LiveData<Pair<Boolean, String>> get() = _loading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _infoMessage = MutableLiveData<String>()
    val infoMessage: LiveData<String> get() = _infoMessage

    companion object {
        private const val TAG = "CreateReplicaViewModel"
    }

    fun valueChanged(value: Any, inputType: EnumReplicaInputType) {
        try {
            Log.i(TAG, "METHOD CALLED: onValueChanged() PARAMS: $value, $inputType")
            when (inputType) {
                EnumReplicaInputType.CODE -> {
                    value as String
                    setCode = value.uppercase()
                }
                EnumReplicaInputType.BIRTHDATE -> {
                    setYear = value as LocalDate // if(value.toInt() in 1980..Calendar.getInstance().get(Calendar.YEAR)) value.toInt() else -1
                }
                else -> {}
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    fun create() {
        if (!valid()) return
        val request = CreateReplicaRequest(setCode, setYear!!, preferencesManager.userId)
        viewModelScope.launch {
            _loading.value = Pair(true, "")
            when (val response = runnerCreateReplica.invoke(request)) {
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
                    _errorMessage.value =
                        resourceProvider.getStringResource(R.string.error_no_internet_connection)
                }
                else -> {
                    _loading.value = Pair(false, "")
                    _errorMessage.value = resourceProvider.getStringResource(R.string.error_generic)
                }
            }
        }
    }

    private fun valid(): Boolean {
        return when {
            setCode.isEmpty() -> {
                _errorEditText.value = Pair(
                    EnumReplicaInputType.CODE,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            setYear == null -> {
                _errorEditText.value = Pair(
                    EnumReplicaInputType.BIRTHDATE,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            preferencesManager.userId == -1L -> {
                _errorEditText.value = Pair(
                    EnumReplicaInputType.AUTH,
                    resourceProvider.getStringResource(R.string.pilgrimage_error_user_authentication)
                )
                false
            }
            else -> {
                _errorEditText.value = Pair(EnumReplicaInputType.BIRTHDATE, null)
                _errorEditText.value = Pair(EnumReplicaInputType.CODE, null)
                _errorEditText.value = Pair(EnumReplicaInputType.AUTH, null)
                true
            }
        }
    }

}