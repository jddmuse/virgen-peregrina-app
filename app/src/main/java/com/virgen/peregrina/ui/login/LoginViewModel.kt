package com.virgen.peregrina.ui.login

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.domain.RunnerLogin
import com.virgen.peregrina.ui.login.enumerator.EnumLoginInputType
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.response.ResponseRunner
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val preferencesManager: PreferencesManager,
    private val runnerLogin: RunnerLogin
) : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    private var setEmail: String = EMPTY_STRING
    private var setPassword: String = EMPTY_STRING
    private var setRememberData: Boolean = false

    /************* Events ****************************/
    private val _loginSuccessEvent = MutableLiveData<Boolean>()
    val loginSuccessEvent: LiveData<Boolean> get() = _loginSuccessEvent

    private val _formValidatedEvent = MutableLiveData<Boolean>()
    val formValidatedEvent: LiveData<Boolean> get() = _formValidatedEvent

    private val _loading = MutableLiveData<Pair<Boolean, String>>()
    val loading: LiveData<Pair<Boolean, String>> get() = _loading

    /************* Errors ****************************/
    private val _errorEditText = MutableLiveData<Pair<EnumLoginInputType, String?>>()
    val errorEditText: LiveData<Pair<EnumLoginInputType, String?>> get() = _errorEditText

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun onCreate(callback: (String, String) -> Unit) {
        with(preferencesManager) {
            setEmail = email ?: EMPTY_STRING
            setPassword = password ?: EMPTY_STRING
        }
        callback(setEmail, setPassword)
//        callback("juliana@gmail.com", "123")
    }

    fun onValueChanged(value: Editable?, inputType: EnumLoginInputType) {
        try {
            Log.i(TAG, "METHOD CALLED: onValueChanged() PARAMS: $value, $inputType")
            val valueAux = value?.toString() ?: EMPTY_STRING
            when (inputType) {
                EnumLoginInputType.PASSWORD -> {
                    if(valueAux.isEmpty()) {
                        setPassword = ""
                        _errorEditText.value = Pair(inputType, resourceProvider.getStringResource(R.string.error_field_required))
                    } else {
                        setPassword = valueAux
                        _errorEditText.value = Pair(inputType, null)
                    }
                }
                EnumLoginInputType.EMAIL -> {
                    if(valueAux.isEmpty()) {
                        setEmail = ""
                        _errorEditText.value = Pair(inputType, resourceProvider.getStringResource(R.string.error_field_required))
                    } else {
                        setEmail = valueAux
                        _errorEditText.value = Pair(inputType, null)
                    }
                }
                EnumLoginInputType.REMEMBER -> {
                    setRememberData = !setRememberData
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    fun validateForm() {
        if(setEmail.isEmpty())
            _errorEditText.value = Pair(EnumLoginInputType.EMAIL, resourceProvider.getStringResource(R.string.error_field_required))
        else
            _errorEditText.value = Pair(EnumLoginInputType.EMAIL, null)

        if(setPassword.isEmpty())
            _errorEditText.value = Pair(EnumLoginInputType.PASSWORD, resourceProvider.getStringResource(R.string.error_field_required))
        else
            _errorEditText.value = Pair(EnumLoginInputType.PASSWORD, null)

        if(setEmail.isNotEmpty() && setPassword.isNotEmpty()) {
            _formValidatedEvent.value = true
        }
    }

    fun login() {
        _loading.value = Pair(true, "")
        val data = LoginRequest(
            email = setEmail,
            pass = setPassword
        )
        viewModelScope.launch {
            when(val response = runnerLogin.invoke(data)) {
                is ResponseRunner.Success -> {
                    _loading.value = Pair(false, "")
                    with(preferencesManager) {
                        userId = response.data?.userId ?: -1
                        email = response.data?.userEmail ?: ""
                        password = response.data?.userEmail ?: ""
                    }
                    _loginSuccessEvent.value = true
                }
                is ResponseRunner.ApiError -> {
                    _loading.value = Pair(false, "")
                    _error.value = StringBuilder().append(response.message ?: "").toString()  // .append("\n${response.error}").toString()
                }
                is ResponseRunner.Error -> {
                    _loading.value = Pair(false, "")
                    _error.value = resourceProvider.getStringResource(R.string.error_generic)
                }
                is ResponseRunner.NoInternetConnection -> {
                    _loading.value = Pair(false, "")
                    _error.value = resourceProvider.getStringResource(R.string.error_no_internet_connection)
                }
                is ResponseRunner.NullOrEmptyData -> {
                    _loading.value = Pair(false, "")
                    _error.value = resourceProvider.getStringResource(R.string.error_generic)

                }
            }
        }
    }
}