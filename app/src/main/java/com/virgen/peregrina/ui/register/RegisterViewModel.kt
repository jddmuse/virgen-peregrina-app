package com.virgen.peregrina.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.request.CreateUserRequest
import com.virgen.peregrina.domain.RunnerRegister
import com.virgen.peregrina.ui.register.enumerator.EnumRegisterInputType
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.response.ResponseRunner
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val runnerRegister: RunnerRegister,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    companion object {
        private const val TAG = "RegisterViewModel"
    }

    private var setEmail: String = EMPTY_STRING
    private var setPassword: String = EMPTY_STRING
    private var setCountryCode: String = EMPTY_STRING
    private var setName: String = EMPTY_STRING
    private var setLastName: String = EMPTY_STRING
    private var setCellphone: String = EMPTY_STRING
    private var setCountry: String = EMPTY_STRING
    private var setCity: String = EMPTY_STRING
    private var setAddress: String = EMPTY_STRING

    /************* Events ****************************/
    private val _registerFinishedEvent = MutableLiveData<Boolean>()
    val registerFinishedEvent: LiveData<Boolean> get() = _registerFinishedEvent

    private val _enableButton = MutableLiveData<Boolean>()
    val enableButton: LiveData<Boolean> get() = _enableButton

    private val _loading = MutableLiveData<Pair<Boolean, String>>()
    val loading: LiveData<Pair<Boolean, String>> get() = _loading

    /************* Errors ****************************/
    private val _errorEditText = MutableLiveData<Pair<EnumRegisterInputType, String?>>()
    val errorEditText: LiveData<Pair<EnumRegisterInputType, String?>> get() = _errorEditText

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg


    fun onValueChanged(value: String, inputType: EnumRegisterInputType) {
        try {
            Log.i(TAG, "METHOD CALLED: onValueChanged() PARAMS: $value, $inputType")
            when (inputType) {
                EnumRegisterInputType.NAME -> {
                    if(value.isEmpty()) {
                        _errorEditText.value = Pair(
                            EnumRegisterInputType.NAME,
                            resourceProvider.getStringResource(R.string.error_field_required)
                        )
                        setName = ""
                        return
                    }
                    setName = value.uppercase()
                }
                EnumRegisterInputType.LAST_NAME -> {
                    if(value.isEmpty()) {
                        _errorEditText.value = Pair(
                            EnumRegisterInputType.LAST_NAME,
                            resourceProvider.getStringResource(R.string.error_field_required)
                        )
                        setLastName = ""
                        return
                    }
                    setLastName = value.uppercase()
                }
                EnumRegisterInputType.ADDRESS -> {
                    if(value.isEmpty()) {
                        _errorEditText.value = Pair(
                            EnumRegisterInputType.ADDRESS,
                            resourceProvider.getStringResource(R.string.error_field_required)
                        )
                        setAddress = ""
                        return
                    }
                    setAddress = value.uppercase()
                }
                EnumRegisterInputType.COUNTRY -> {
                    if(value.isEmpty()) {
                        _errorEditText.value = Pair(
                            EnumRegisterInputType.COUNTRY,
                            resourceProvider.getStringResource(R.string.error_field_required)
                        )
                        setCountry = ""
                        return
                    }
                    setCountry = value.uppercase()
                }
                EnumRegisterInputType.CITY -> {
                    if(value.isEmpty()) {
                        _errorEditText.value = Pair(
                            EnumRegisterInputType.CITY,
                            resourceProvider.getStringResource(R.string.error_field_required)
                        )
                        setCity = ""
                        return
                    }
                    setCity = value.uppercase()
                }
                EnumRegisterInputType.COUNTRY_CODE -> {
                    if(value.isEmpty()) {
                        _errorEditText.value = Pair(
                            EnumRegisterInputType.COUNTRY_CODE,
                            resourceProvider.getStringResource(R.string.error_field_required)
                        )
                        setCountryCode = ""
                        return
                    }
                    setCountryCode = value
                }
                EnumRegisterInputType.CELLPHONE -> {
                    if(value.isEmpty()) {
                        _errorEditText.value = Pair(
                            EnumRegisterInputType.CELLPHONE,
                            resourceProvider.getStringResource(R.string.error_field_required)
                        )
                        setCellphone = ""
                        return
                    }
                    setCellphone = value
                }
                EnumRegisterInputType.EMAIL -> {
                    if(value.isEmpty()) {
                        _errorEditText.value = Pair(
                            EnumRegisterInputType.EMAIL,
                            resourceProvider.getStringResource(R.string.error_field_required)
                        )
                        setEmail = ""
                        return
                    }
                    setEmail = value.uppercase()
                }
                EnumRegisterInputType.PASSWORD -> {
                    if(value.isEmpty()) {
                        _errorEditText.value = Pair(
                            EnumRegisterInputType.PASSWORD,
                            resourceProvider.getStringResource(R.string.error_field_required)
                        )
                        setPassword = ""
                        return
                    }
                    setPassword = value
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    private fun valid(): Boolean {
        return when {
            setName.isEmpty() -> {
                _errorEditText.value = Pair(
                    EnumRegisterInputType.NAME,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            setLastName.isEmpty() -> {
                _errorEditText.value = Pair(
                    EnumRegisterInputType.LAST_NAME,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            setEmail.isEmpty() -> {
                _errorEditText.value = Pair(
                    EnumRegisterInputType.EMAIL,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            setCity.isEmpty() -> {
                _errorEditText.value = Pair(
                    EnumRegisterInputType.CITY,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            setCountry.isEmpty() -> {
                _errorEditText.value = Pair(
                    EnumRegisterInputType.COUNTRY,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            setCountryCode.isEmpty() -> {
                _errorEditText.value = Pair(
                    EnumRegisterInputType.COUNTRY,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            setCellphone.isEmpty() -> {
                _errorEditText.value = Pair(
                    EnumRegisterInputType.CELLPHONE,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            setAddress.isEmpty() -> {
                _errorEditText.value = Pair(
                    EnumRegisterInputType.ADDRESS,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            setPassword.isEmpty() -> {
                _errorEditText.value = Pair(
                    EnumRegisterInputType.PASSWORD,
                    resourceProvider.getStringResource(R.string.error_field_required)
                )
                false
            }
            else -> {
                _errorEditText.value = Pair(EnumRegisterInputType.NAME, null)
                _errorEditText.value = Pair(EnumRegisterInputType.LAST_NAME, null)
                _errorEditText.value = Pair(EnumRegisterInputType.EMAIL, null)
                _errorEditText.value = Pair(EnumRegisterInputType.CITY, null)
                _errorEditText.value = Pair(EnumRegisterInputType.COUNTRY, null)
                _errorEditText.value = Pair(EnumRegisterInputType.COUNTRY_CODE, null)
                _errorEditText.value = Pair(EnumRegisterInputType.CELLPHONE, null)
                _errorEditText.value = Pair(EnumRegisterInputType.ADDRESS, null)
                _errorEditText.value = Pair(EnumRegisterInputType.PASSWORD, null)
                true
            }
        }
    }

    fun register() {
        if(!valid()) return

        _loading.value = Pair(true, "")
        val data = CreateUserRequest(
            name = setName,
            lastName = setLastName,
            address = setAddress,
            country = setCountry,
            city = setCity,
            cellphone = setCellphone,
            email = setEmail,
            pass = setPassword,
            photoUrl = "",
            telephone = ""
        )
        viewModelScope.launch {
            when(val baseResponse = runnerRegister.invoke(data)) {
                is ResponseRunner.Success -> {
                    _loading.value = Pair(false, "")
                    with(preferencesManager) {
                        userId = baseResponse.data?.id ?: -1
                        email = baseResponse.data?.email ?: ""
                        password = baseResponse.data?.pass ?: ""
                    }
                    _registerFinishedEvent.value = true
                }
                is ResponseRunner.ApiError -> {
                    _loading.value = Pair(false, "")
                    _error.value = StringBuilder()
                        .append(baseResponse.message ?: "")
                        .append("\n${baseResponse.error}")
                        .toString()
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