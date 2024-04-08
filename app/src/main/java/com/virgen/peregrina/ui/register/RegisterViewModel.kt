package com.virgen.peregrina.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.request.SignUpRequest
import com.virgen.peregrina.domain.signup.SignUpWithFirebaseUseCase
import com.virgen.peregrina.domain.signup.SignUpWithVirgenPeregrinaUseCase
import com.virgen.peregrina.ui.login.LoginViewModel
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.getCellphone
import com.virgen.peregrina.util.getExceptionLog
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpWithVirgenPeregrinaUseCase: SignUpWithVirgenPeregrinaUseCase,
    private val signUpWithFirebaseUseCase: SignUpWithFirebaseUseCase,
    private val preferencesManager: PreferencesManager,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    companion object {
        private const val TAG = "RegisterViewModel"
    }

    private var setEmail: String = EMPTY_STRING
    private var setPassword: String = EMPTY_STRING
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

    private val _loading = MutableLiveData<Pair<Boolean, String>>()
    val loading: LiveData<Pair<Boolean, String>> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg


    fun onCreate() {
//        with(preferencesManager) {
//            setEmail = email ?: EMPTY_STRING
//            setUUID = uuid ?: EMPTY_STRING
//        }
    }

    fun onValueChanged(value: Any?, inputType: RegisterInputType) {
        try {
            Log.i(TAG, "METHOD CALLED: onValueChanged() PARAMS: $value, $inputType")
            val valueAux = value?.toString() ?: EMPTY_STRING
            when (inputType) {
                RegisterInputType.NAME -> setName = valueAux
                RegisterInputType.LAST_NAME -> setLastName = valueAux
                RegisterInputType.ADDRESS -> setAddress = valueAux
                RegisterInputType.COUNTRY -> setCountry = valueAux
                RegisterInputType.CITY -> setCity = valueAux
                RegisterInputType.COUNTRY_CODE -> setCountryCode = valueAux
                RegisterInputType.CELLPHONE -> setCellphone = valueAux
                RegisterInputType.EMAIL -> setEmail = valueAux
                RegisterInputType.PASSWORD -> setPassword = valueAux
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    fun onValueChanged(value: Any?, inputType: EnumReplicaDialogInputType) {
        try {
            Log.i(
                TAG, "METHOD CALLED: onValueChanged() " +
                        "PARAMS: $value, $inputType"
            )
            when (inputType) {
                EnumReplicaDialogInputType.CODE -> {
                    val valueAux = value?.toString() ?: EMPTY_STRING
                    setCodeReplica = valueAux
                }
                EnumReplicaDialogInputType.DATE -> {
                    val valueAux = value?.toString() ?: EMPTY_STRING
                    setReceivedDateReplica = valueAux
                }
                EnumReplicaDialogInputType.REPAIR_REQUIRED -> {
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

    private fun onSignUpWithFirebase() {
        try {
            viewModelScope.launch {
                when (val result = signUpWithFirebaseUseCase(setEmail, setPassword)) {
                    is BaseResultUseCase.Success -> {
                        result.data?.uid?.let { uuid ->
                            preferencesManager.email = setEmail
                            preferencesManager.uuid = uuid
                            setUUID = uuid

                            /** Continua con el registro en Virgen Peregrina API */
                            signUpWithVirgenPeregrina()
                        }
                    }
                    is BaseResultUseCase.Error -> {
                        _errorMsg.value = resourceProvider
                            .getStringResource(R.string.error_login)
                    }
                    is BaseResultUseCase.NullOrEmptyData -> {
                        _errorMsg.value = resourceProvider
                            .getStringResource(R.string.error_generic)
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onSignUpWithFirebase() -> Exception: $ex")
        }
    }

    fun onActionButton() {
        try {
            Log.i(TAG, "$METHOD_CALLED onActionButton()")
            _enableButton.value = false
            if (noErrorExists()) {
                onSignUpWithFirebase()
            } else _enableButton.value = true
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged(): Exception -> $ex")
        }
    }

    private fun signUpWithVirgenPeregrina() {
        try {
            _loading.value = Pair(true, resourceProvider.getStringResource(R.string.label_sending_request))
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
                        _loading.value = Pair(false, "")
                        _startMainActivity.value = !(_startMainActivity.value ?: false)
                    }
                    is BaseResultUseCase.Error -> {
                        _loading.value = Pair(false, "")
                        _error.value = resourceProvider.getStringResource(R.string.error_generic)
                    }
                    is BaseResultUseCase.NullOrEmptyData -> {
                        _loading.value = Pair(false, "")
                        _error.value = resourceProvider.getStringResource(R.string.error_generic)
                    }
                }
            }
        } catch (ex:Exception) {
            getExceptionLog(TAG, "signUpWithVirgenPeregrina", ex)
        }
    }

    private fun noErrorExists(): Boolean = when {
//        setUUID.isEmpty() -> {
//            false
//        }
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