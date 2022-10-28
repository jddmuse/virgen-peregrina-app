package com.virgen.peregrina.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.google.gson.Gson
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.data.response.LoginResponse
import com.virgen.peregrina.domain.pilgrimage.GetAllPilgrimagesUseCase
import com.virgen.peregrina.domain.pilgrimage.SendTestimonyUseCase
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.util.getCurrentDate
import com.virgen.peregrina.util.getExceptionLog
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.GlobalProvider
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPilgrimagesUseCase: GetAllPilgrimagesUseCase,
    private val resourceProvider: ResourceProvider,
    private val preferencesManager: PreferencesManager,
    private val globalProvider: GlobalProvider,
    private val sendTestimonyUseCase: SendTestimonyUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _userData = MutableLiveData<LoginResponse?>()
    val userData: LiveData<LoginResponse?> get() = _userData

    private val _pilgrimages = MutableLiveData<List<PilgrimageModel>>()
    val pilgrimages: LiveData<List<PilgrimageModel>> get() = _pilgrimages

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg

    private val _infoMsg = MutableLiveData<String?>()
    val infoMsg: LiveData<String?> get() = _infoMsg

    fun onCreate() {
        try {
            _userData.value = globalProvider.userData
            viewModelScope.launch {
                when (val result = getAllPilgrimagesUseCase()) {
                    is BaseResultUseCase.Error -> {
                        _errorMsg.value =
                            resourceProvider
                                .getStringResource(R.string.error_generic)
                    }
                    is BaseResultUseCase.Success -> {
                        _pilgrimages.value = result.data ?: listOf()
                    }
                    is BaseResultUseCase.NullOrEmptyData -> {}
                }
            }
        } catch (ex: Exception) {
            getExceptionLog(TAG, "onCreate", ex)
        }
    }

    fun onSendTestimony(
        testimonyMsg: String,
        replica_id: Long,
        user_id: Long,
        pilgrimage_id: Long
    ) {
        try {
            viewModelScope.launch {
                val data = TestimonyModel(
                    date = getCurrentDate(),
                    user_id = user_id,
                    replica_id = replica_id,
                    value = testimonyMsg,
                    pilgrimage_id =pilgrimage_id
                )
                when (val result = sendTestimonyUseCase(data)) {
                    is BaseResultUseCase.Success -> {
                        _infoMsg.value =
                            resourceProvider
                                .getStringResource(R.string.info_testimony_send_successfully)
                    }
                    is BaseResultUseCase.Error -> {}
                    is BaseResultUseCase.NoInternetConnection -> {
                        _errorMsg.value =
                            resourceProvider
                                .getStringResource(R.string.error_no_internet_connection)
                    }
                    is BaseResultUseCase.NullOrEmptyData -> {}
                }
            }
        } catch (ex: Exception) {
            getExceptionLog(TAG, "onSendTestimonyUseCase", ex)
        }
    }
}