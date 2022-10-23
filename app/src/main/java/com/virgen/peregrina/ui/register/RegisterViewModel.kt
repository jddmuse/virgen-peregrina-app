package com.virgen.peregrina.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.request.SignUpRequest
import com.virgen.peregrina.domain.signup.SignUpWithVirgenPeregrinaUseCase
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.getCellphone
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpWithVirgenPeregrinaUseCase: SignUpWithVirgenPeregrinaUseCase,
    private val preferencesManager: PreferencesManager,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    companion object {
        private const val TAG = "RegisterViewModel"
    }

    private var setEmail: String = EMPTY_STRING
    private var setUUID: String = EMPTY_STRING
    private var setCountryCode: String = EMPTY_STRING
    private var setName: String = EMPTY_STRING
    private var setLastName: String = EMPTY_STRING
    private var setCellphone: String = EMPTY_STRING
    private var setCountry: String = EMPTY_STRING
    private var setCity: String = EMPTY_STRING
    private var setAddress: String = EMPTY_STRING

    private val setReplicas = mutableListOf<ReplicaModel>()
    private var setCodeReplica: String = EMPTY_STRING
    private var setReceivedDateReplica: String = EMPTY_STRING
    private var setRepairRequired: Boolean = false

    private val _startMainActivity = MutableLiveData<Boolean>()
    val startMainActivity: LiveData<Boolean> get() = _startMainActivity

    private val _enableButton = MutableLiveData<Boolean>()
    val enableButton: LiveData<Boolean> get() = _enableButton

    private val _nameErrorMsg = MutableLiveData<String?>()
    val nameErrorMsg: LiveData<String?> get() = _nameErrorMsg

    private val _lastNameErrorMsg = MutableLiveData<String?>()
    val lastNameErrorMsg: LiveData<String?> get() = _lastNameErrorMsg

    private val _addressErrorMsg = MutableLiveData<String?>()
    val addressErrorMsg: LiveData<String?> get() = _addressErrorMsg

    private val _countryErrorMsg = MutableLiveData<String?>()
    val countryErrorMsg: LiveData<String?> get() = _countryErrorMsg

    private val _cityErrorMsg = MutableLiveData<String?>()
    val cityErrorMsg: LiveData<String?> get() = _cityErrorMsg

    private val _cellphoneErrorMsg = MutableLiveData<String?>()
    val cellphoneErrorMsg: LiveData<String?> get() = _cellphoneErrorMsg

    private val _codeReplicaErrorMsg = MutableLiveData<String?>()
    val codeReplicaErrorMsg: LiveData<String?> get() = _codeReplicaErrorMsg

    private val _receivedDateErrorMsg = MutableLiveData<String?>()
    val receivedDateErrorMsg: LiveData<String?> get() = _receivedDateErrorMsg

    private val _onCloseDatePickerDialog = MutableLiveData<Boolean>()
    val onCloseDatePickerDialog: LiveData<Boolean> get() = _onCloseDatePickerDialog


    fun onCreate() {
        with(preferencesManager) {
            setEmail = email ?: EMPTY_STRING
            setUUID = uuid ?: EMPTY_STRING
        }
    }

    fun onValueChanged(value: Any?, inputType: RegisterInputType) {
        try {
            Log.i(
                TAG, "METHOD CALLED: onValueChanged() " +
                        "PARAMS: $value, $inputType"
            )
            val valueAux = value?.toString() ?: EMPTY_STRING
            when (inputType) {
                RegisterInputType.NAME -> setName = valueAux
                RegisterInputType.LAST_NAME -> setLastName = valueAux
                RegisterInputType.ADDRESS -> setAddress = valueAux
                RegisterInputType.COUNTRY -> setCountry = valueAux
                RegisterInputType.CITY -> setCity = valueAux
                RegisterInputType.COUNTRY_CODE -> setCountryCode = valueAux
                RegisterInputType.CELLPHONE -> setCellphone = valueAux
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    fun onValueChanged(value: Any?, inputType: ReplicaDialogInputType) {
        try {
            Log.i(
                TAG, "METHOD CALLED: onValueChanged() " +
                        "PARAMS: $value, $inputType"
            )
            when (inputType) {
                ReplicaDialogInputType.CODE -> {
                    val valueAux = value?.toString() ?: EMPTY_STRING
                    setCodeReplica = valueAux
                }
                ReplicaDialogInputType.DATE -> {
                    val valueAux = value?.toString() ?: EMPTY_STRING
                    setReceivedDateReplica = valueAux
                }
                ReplicaDialogInputType.REPAIR_REQUIRED -> {
                    val valueAux = value as Boolean? ?: false
                    setRepairRequired = valueAux
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    fun onReplicaSaved() {
        try {
            if (noReplicaErrorExists()) {
                setReplicas.add(
                    ReplicaModel(
                        code = setCodeReplica,
                        received_date = setReceivedDateReplica,
                        repair_required = setRepairRequired
                    )
                )
                setCodeReplica = EMPTY_STRING
                setReceivedDateReplica = EMPTY_STRING
                setRepairRequired = false
                _onCloseDatePickerDialog.value = !(_onCloseDatePickerDialog.value ?: false)
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onReplicaSaved() -> $ex")
        }
    }

    private fun noReplicaErrorExists(): Boolean = when {
        setCodeReplica.isEmpty() -> {
            _codeReplicaErrorMsg.value =
                resourceProvider.getStringResource(R.string.error_field_required)
            false
        }
        setReceivedDateReplica.isEmpty() -> {
            _receivedDateErrorMsg.value =
                resourceProvider.getStringResource(R.string.error_field_required)
            false
        }
        else -> true
    }

    fun onActionButton() {
        try {
            Log.i(TAG, "$METHOD_CALLED onActionButton()")
            if (noErrorExists()) {
                val signUpRequest = SignUpRequest(
                    uuid = setUUID,
                    name = setName,
                    last_name = setLastName,
                    email = setEmail,
                    city = setCity,
                    country = setCountry,
                    cellphone = getCellphone(setCountryCode, setCellphone),
                    address = setAddress,
                    replicas = setReplicas,
                    isPilgrim = true
                )
                viewModelScope.launch {
                    when (val result = signUpWithVirgenPeregrinaUseCase(signUpRequest)) {
                        is BaseResultUseCase.Success -> {
                            _startMainActivity.value = !(_startMainActivity.value ?: false)
                        }
                        is BaseResultUseCase.Error -> {}
                        is BaseResultUseCase.NullOrEmptyData -> {}
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged(): Exception -> $ex")
        }
    }

    private fun noErrorExists(): Boolean = when {
        setUUID.isEmpty() -> {
            false
        }
        setName.isEmpty() -> {
            _nameErrorMsg.value = resourceProvider.getStringResource(R.string.error_field_required)
            false
        }
        setLastName.isEmpty() -> {
            _lastNameErrorMsg.value =
                resourceProvider.getStringResource(R.string.error_field_required)
            false
        }
        setEmail.isEmpty() -> {
            false
        }
        setCity.isEmpty() -> {
            _cityErrorMsg.value = resourceProvider.getStringResource(R.string.error_field_required)
            false
        }
        setCountry.isEmpty() -> {
            _countryErrorMsg.value =
                resourceProvider.getStringResource(R.string.error_field_required)
            false
        }
        setCountryCode.isEmpty() -> {
            false
        }
        setCellphone.isEmpty() -> {
            _cellphoneErrorMsg.value =
                resourceProvider.getStringResource(R.string.error_field_required)
            false
        }
        setAddress.isEmpty() -> {
            _addressErrorMsg.value =
                resourceProvider.getStringResource(R.string.error_field_required)
            false
        }
        else -> true
    }


}