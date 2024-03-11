package com.virgen.peregrina.ui.pilgrimage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.request.CreatePilgrimageRequest
import com.virgen.peregrina.data.request.SignUpRequest
import com.virgen.peregrina.domain.pilgrimage.CreatePilgrimageUseCase
import com.virgen.peregrina.domain.pilgrimage.GetAllPilgrimsUseCase
import com.virgen.peregrina.domain.signup.SignUpWithVirgenPeregrinaUseCase
import com.virgen.peregrina.ui.register.RegisterInputType
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.getCellphone
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PilgrimageViewModel @Inject constructor(
    private val getAllPilgrimsUseCase: GetAllPilgrimsUseCase,
    private val signUpWithVirgenPeregrinaUseCase: SignUpWithVirgenPeregrinaUseCase,
    private val createPilgrimageUseCase: CreatePilgrimageUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    // receiver user data
    private var setEmail: String = EMPTY_STRING
    private var setCountryCode: String = EMPTY_STRING
    private var setName: String = EMPTY_STRING
    private var setLastName: String = EMPTY_STRING
    private var setCellphone: String = EMPTY_STRING
    private var setCountry: String = EMPTY_STRING
    private var setCity: String = EMPTY_STRING
    private var setAddress: String = EMPTY_STRING

    // pilgrimage data
    private var setUserId: Long? = null
    private var setIntention = EMPTY_STRING
    private var setStartDate = EMPTY_STRING
    private var setEndDate = EMPTY_STRING
    private var setReplicaId: Long? = null

    // error live data
    private val _nameErrorMsg = MutableLiveData<String?>()
    val nameErrorMsg: LiveData<String?> get() = _nameErrorMsg

    private val _lastNameErrorMsg = MutableLiveData<String?>()
    val lastNameErrorMsg: LiveData<String?> get() = _lastNameErrorMsg

    private val _emailErrorMsg = MutableLiveData<String?>()
    val emailErrorMsg: LiveData<String?> get() = _emailErrorMsg

    private val _addressErrorMsg = MutableLiveData<String?>()
    val addressErrorMsg: LiveData<String?> get() = _addressErrorMsg

    private val _countryErrorMsg = MutableLiveData<String?>()
    val countryErrorMsg: LiveData<String?> get() = _countryErrorMsg

    private val _cityErrorMsg = MutableLiveData<String?>()
    val cityErrorMsg: LiveData<String?> get() = _cityErrorMsg

    private val _cellphoneErrorMsg = MutableLiveData<String?>()
    val cellphoneErrorMsg: LiveData<String?> get() = _cellphoneErrorMsg

    private val _setUserIdErrorMsg = MutableLiveData<String?>()
    val setUserIdErrorMsg: LiveData<String?> get() = _setUserIdErrorMsg

    private val _setIntentionErrorMsg = MutableLiveData<String?>()
    val setIntentionErrorMsg: LiveData<String?> get() = _setIntentionErrorMsg

    private val _setStartDateErrorMsg = MutableLiveData<String?>()
    val setStartDateErrorMsg: LiveData<String?> get() = _setStartDateErrorMsg

    private val _setEndDateErrorMsg = MutableLiveData<String?>()
    val setEndDateErrorMsg: LiveData<String?> get() = _setEndDateErrorMsg


    // ui data behavior
    private val _pilgrims = MutableLiveData<List<UserModel>>()
    val pilgrims: LiveData<List<UserModel>> get() = _pilgrims

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _onFinishActivity = MutableLiveData<Boolean>()
    val onFinishActivity: LiveData<Boolean> get() = _onFinishActivity

    private val _loading = MutableLiveData<Pair<Boolean, String>>()
    val loading: LiveData<Pair<Boolean, String>> get() = _loading

    companion object {
        private const val TAG = "PilgrimageViewModel"
    }

    fun onCreate(replica_id: Long) {
        try {
            setReplicaId = replica_id
            viewModelScope.launch {
                when (val result = getAllPilgrimsUseCase()) {
                    is BaseResultUseCase.Success -> {
                        val list = result.data ?: emptyList()
                        _pilgrims.value = list
                    }
                    is BaseResultUseCase.Error -> {
                        _error.value = resourceProvider.getStringResource(R.string.error_generic)
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onCreate(): Exception -> $ex")
        }
    }

    fun onStartPilgrimage() {
        try {
            if (isAllFormFilled()) {
                saveReceiverUser()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onStartPilgrimage(): Exception -> $ex")
        }
    }

    private fun saveReceiverUser() {
        try {
            _loading.value = Pair(true, resourceProvider.getStringResource(R.string.label_sending_request))
            val newUser = SignUpRequest(
                uuid = null,
                name = setName,
                last_name = setLastName,
                email = setEmail,
                city = setCity,
                country = setCountry,
                cellphone = getCellphone(setCountryCode, setCellphone),
                address = setAddress,
                replicas = null,
                isPilgrim = false
            )
            viewModelScope.launch {
                when (val result = signUpWithVirgenPeregrinaUseCase(newUser)) {
                    is BaseResultUseCase.Success -> {
                        _loading.value = Pair(false, "")
                        savePilgrimage(result.data!!.id)
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
        } catch (ex: Exception) {
            Log.e(TAG, "saveReceiverUser(): Exception -> $ex")
        }
    }

    private fun savePilgrimage(receiverUserId: Long) {
        try {
            val newPilgrimage = CreatePilgrimageRequest(
                startDate = setStartDate,
                endDate = setEndDate,
                intention = setIntention,
                userId = setUserId!!,
                replicaId = setReplicaId!!,
                receiverId = receiverUserId,
                city = setCity,
                country = setCountry
            )
            viewModelScope.launch {
                when (val result = createPilgrimageUseCase(newPilgrimage)) {
                    is BaseResultUseCase.Success -> {
                        Log.i(TAG, "pilgrimage creada exitosa mente. ${result.data}")
                        _onFinishActivity.value = !(_onFinishActivity.value ?: false)
                    }
                    is BaseResultUseCase.Error -> {
//                        _error.value = result.
                    }
                    is BaseResultUseCase.NullOrEmptyData -> {}
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "savePilgrimage(): Exception -> $ex")
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
                RegisterInputType.EMAIL -> setEmail = valueAux
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

    fun onValueChanged(value: Any?, inputType: PilgrimageInputType) {
        try {
            Log.i(
                TAG, "$METHOD_CALLED onValueChanged() " +
                        "PARAMS: value=$value, inputType=$inputType"
            )
            when (inputType) {
                PilgrimageInputType.INTENTION -> {
                    val valueAux = value.toString()
                    setIntention = valueAux
                }
                PilgrimageInputType.USER_ID -> {
                    val valueAux = value.toString().toLong()
                    setUserId = valueAux
                }
                PilgrimageInputType.START_DATE -> {
                    val valueAux = value.toString()
                    setStartDate = valueAux
                }
                PilgrimageInputType.END_DATE -> {
                    val valueAux = value.toString()
                    setEndDate = valueAux
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    private fun isAllFormFilled(): Boolean = when {
        setName.isEmpty() -> {
            _nameErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        setLastName.isEmpty() -> {
            _lastNameErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        setEmail.isEmpty() -> {
            _emailErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        setCity.isEmpty() -> {
            _cityErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        setCountry.isEmpty() -> {
            _countryErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        setCountryCode.isEmpty() -> {
            false
        }
        setCellphone.isEmpty() -> {
            _cellphoneErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        setAddress.isEmpty() -> {
            _addressErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        setUserId == null -> {
            _setUserIdErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        setIntention.isEmpty() -> {
            _setIntentionErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        setStartDate.isEmpty() -> {
            _setStartDateErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        setEndDate.isEmpty() -> {
            _setEndDateErrorMsg.value =
                resourceProvider
                    .getStringResource(R.string.error_field_required)
            false
        }
        else -> true
    }

}