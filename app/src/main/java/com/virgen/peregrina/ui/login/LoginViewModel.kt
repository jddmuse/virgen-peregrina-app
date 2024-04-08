package com.virgen.peregrina.ui.login

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.data.response.toSessionData
import com.virgen.peregrina.domain.login.LoginWithFirebaseUseCase
import com.virgen.peregrina.domain.login.LoginWithVirgenPeregrinaUseCase
import com.virgen.peregrina.domain.signup.SignUpWithFirebaseUseCase
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.GlobalProvider
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithFirebaseUseCase: LoginWithFirebaseUseCase,
    private val loginWithVirgenPeregrinaUseCase: LoginWithVirgenPeregrinaUseCase,
    private val resourceProvider: ResourceProvider,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    private var setEmail: String = EMPTY_STRING
    private var setPassword: String = EMPTY_STRING
    private var setUUID: String = EMPTY_STRING
    private var setRememberData: Boolean = false

    private val _startRegisterActivity = MutableLiveData<Boolean>()
    val startRegisterActivity: LiveData<Boolean> get() = _startRegisterActivity

    private val _enableButton = MutableLiveData<Boolean>()
    val enableButton: LiveData<Boolean> get() = _enableButton

    private val _emailErrorMsg = MutableLiveData<String?>()
    val emailErrorMsg: LiveData<String?> get() = _emailErrorMsg

    private val _passwordErrorMsg = MutableLiveData<String?>()
    val passwordErrorMsg: LiveData<String?> get() = _passwordErrorMsg

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg

    private val _loginWithFirebase = MutableLiveData<Boolean>()
    val loginWithFirebase: LiveData<Boolean> get() = _loginWithFirebase

    private val _loginWithVirgenPeregrina = MutableLiveData<Boolean>()
    val loginWithVirgenPeregrina: LiveData<Boolean> get() = _loginWithVirgenPeregrina

    private val _loading = MutableLiveData<Pair<Boolean, String>>()
    val loading: LiveData<Pair<Boolean, String>> get() = _loading

    fun onCreate(callback: (String, String) -> Unit) {
        with(preferencesManager) {
            setEmail = email ?: EMPTY_STRING
            setPassword = password ?: EMPTY_STRING
        }
        callback(setEmail, setPassword)
    }

    //
    fun onValueChanged(value: Editable?, inputType: LoginInputType) {
        try {
            Log.i(TAG, "METHOD CALLED: onValueChanged() PARAMS: $value, $inputType")
            val valueAux = value?.toString() ?: EMPTY_STRING
            when (inputType) {
                LoginInputType.PASSWORD -> {
                    setPassword = valueAux
                    if (valueAux.isNotEmpty())
                        _passwordErrorMsg.value = null
                    else
                        _passwordErrorMsg.value = resourceProvider
                            .getStringResource(R.string.error_field_required)
                }
                LoginInputType.EMAIL -> {
                    setEmail = valueAux
                    if (valueAux.isNotEmpty())
                        _emailErrorMsg.value = null
                    else
                        _emailErrorMsg.value = resourceProvider
                            .getStringResource(R.string.error_field_required)
                }
                LoginInputType.REMEMBER -> {
                    setRememberData = !setRememberData
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    fun onLoginWithFirebase() {
        try {
            Log.i(TAG, "$METHOD_CALLED onLoginWithFirebase()")
            if (noErrorExists()) {
                _loading.value = Pair(true, resourceProvider.getStringResource(R.string.label_sending_request))
                _enableButton.value = false
                savePreferencesData()
                viewModelScope.launch {
                    when (val result = loginWithFirebaseUseCase(setEmail, setPassword)) {
                        is BaseResultUseCase.Success -> {
                            _loading.value = Pair(false, "")
                            result.data?.uid?.let { uid ->
                                setUUID = uid
                                loginWithVirgenPeregrina()
                            }
                        }
                        is BaseResultUseCase.NullOrEmptyData -> {
                            _loading.value = Pair(false, "")
                            _errorMsg.value = resourceProvider.getStringResource(R.string.error_generic)
                            _enableButton.value = true
                        }
                        is BaseResultUseCase.Error -> {
                            _loading.value = Pair(false, "")
                            _errorMsg.value = resourceProvider.getStringResource(R.string.error_generic)
                            _enableButton.value = true
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onLoginWithFirebase() -> Exception: $ex")
        }
    }

    private fun noErrorExists(): Boolean {
        return _emailErrorMsg.value == null && _passwordErrorMsg.value == null
    }

    private fun savePreferencesData() {
        try {
            if (setRememberData) {
                with(preferencesManager) {
                    email = setEmail
                    password = setPassword
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onSignUpWithFirebase() -> Exception: $ex")
        }
    }

    fun loginWithVirgenPeregrina() {
        try {
            _enableButton.value = false
            viewModelScope.launch {
                when (val result = loginWithVirgenPeregrinaUseCase(LoginRequest(setUUID, setEmail))) {
                    is BaseResultUseCase.Success -> {
                        if (result.data != null) {
                            preferencesManager.userSessionData = result.data.toSessionData()
                            _loginWithVirgenPeregrina.value = true
                        } else {
                            _loginWithVirgenPeregrina.value = false
                        }
                    }
                    is BaseResultUseCase.NullOrEmptyData -> {
                        _errorMsg.value = resourceProvider.getStringResource(R.string.error_generic)
                        _enableButton.value = true
                    }
                    is BaseResultUseCase.Error -> {
                        _errorMsg.value = resourceProvider.getStringResource(R.string.error_generic)
                        _enableButton.value = true
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "loginWithVirgenPeregrina() -> Exception: $ex")
        }
    }
}