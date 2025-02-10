package com.virgen.peregrina.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.request.CreateUserRequest
import com.virgen.peregrina.domain.RegisterRunner
import com.virgen.peregrina.ui.register.enumerator.EnumRegisterInputType
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.base.BaseResponseRunner
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val registerRunner: RegisterRunner
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
    private val _formValidatedEvent = MutableLiveData<Boolean>()
    val formValidatedEvent: LiveData<Boolean> get() = _formValidatedEvent

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


    fun onValueChanged(value: Any?, inputType: EnumRegisterInputType) {
        try {
            Log.i(TAG, "METHOD CALLED: onValueChanged() PARAMS: $value, $inputType")
            val valueAux = value as String? ?: EMPTY_STRING
            if (valueAux.isEmpty())
                _errorEditText.value = Pair(inputType, resourceProvider.getStringResource(R.string.error_field_required))
            else
                _errorEditText.value = Pair(inputType, null)
            when (inputType) {
                EnumRegisterInputType.NAME -> setName = valueAux
                EnumRegisterInputType.LAST_NAME -> setLastName = valueAux
                EnumRegisterInputType.ADDRESS -> setAddress = valueAux
                EnumRegisterInputType.COUNTRY -> setCountry = valueAux.uppercase()
                EnumRegisterInputType.CITY -> setCity = valueAux.uppercase()
                EnumRegisterInputType.COUNTRY_CODE -> setCountryCode = valueAux
                EnumRegisterInputType.CELLPHONE -> setCellphone = valueAux
                EnumRegisterInputType.EMAIL -> setEmail = valueAux
                EnumRegisterInputType.PASSWORD -> setPassword = valueAux
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    fun validateForm() {
        if (setName.isEmpty())
            _errorEditText.value = Pair(
                EnumRegisterInputType.NAME,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
        else
            _errorEditText.value = Pair(EnumRegisterInputType.NAME, null)

        if (setLastName.isEmpty())
            _errorEditText.value = Pair(
                EnumRegisterInputType.LAST_NAME,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
        else
            _errorEditText.value = Pair(EnumRegisterInputType.LAST_NAME, null)

        if (setEmail.isEmpty())
            _errorEditText.value = Pair(
                EnumRegisterInputType.EMAIL,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
        else
            _errorEditText.value = Pair(EnumRegisterInputType.EMAIL, null)

        if (setCity.isEmpty())
            _errorEditText.value = Pair(
                EnumRegisterInputType.CITY,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
        else
            _errorEditText.value = Pair(EnumRegisterInputType.CITY, null)

        if (setCountry.isEmpty())
            _errorEditText.value = Pair(
                EnumRegisterInputType.COUNTRY,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
        else
            _errorEditText.value = Pair(EnumRegisterInputType.COUNTRY, null)

        if (setCountryCode.isEmpty())
            _errorEditText.value = Pair(
                EnumRegisterInputType.COUNTRY,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
        else
            _errorEditText.value = Pair(EnumRegisterInputType.COUNTRY, null)

        if (setCellphone.isEmpty())
            _errorEditText.value = Pair(
                EnumRegisterInputType.CELLPHONE,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
        else
            _errorEditText.value = Pair(EnumRegisterInputType.CELLPHONE, null)

        if (setAddress.isEmpty())
            _errorEditText.value = Pair(
                EnumRegisterInputType.ADDRESS,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
        else
            _errorEditText.value = Pair(EnumRegisterInputType.ADDRESS, null)

        if (setPassword.isEmpty())
            _errorEditText.value = Pair(
                EnumRegisterInputType.PASSWORD,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
        else
            _errorEditText.value = Pair(EnumRegisterInputType.PASSWORD, null)

        if(setName.isNotEmpty()
            && setLastName.isNotEmpty()
            && setEmail.isNotEmpty()
            && setCity.isNotEmpty()
            && setCountry.isNotEmpty()
            && setCountryCode.isNotEmpty()
            && setCellphone.isNotEmpty()
            && setAddress.isNotEmpty()
            && setPassword.isNotEmpty()) {
            _formValidatedEvent.value = true
        }
    }

    fun register() {
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
            when(val baseResponse = registerRunner.invoke(data)) {
                is BaseResponseRunner.Success -> {
                    _loading.value = Pair(false, "")
                    _registerFinishedEvent.value = true
                }
                is BaseResponseRunner.APIError -> {
                    _loading.value = Pair(false, "")
                    _error.value = StringBuilder()
                        .append(baseResponse.message ?: "")
                        .append("\n${baseResponse.error}")
                        .toString()
                }
                is BaseResponseRunner.Error -> {
                    _loading.value = Pair(false, "")
                    _error.value = resourceProvider.getStringResource(R.string.error_generic)
                }
                is BaseResponseRunner.NoInternetConnection -> {
                    _loading.value = Pair(false, "")
                    _error.value = resourceProvider.getStringResource(R.string.error_no_internet_connection)
                }
                is BaseResponseRunner.NullOrEmptyData -> {
                    _loading.value = Pair(false, "")
                    _error.value = resourceProvider.getStringResource(R.string.error_generic)
                }
            }
        }
    }

}