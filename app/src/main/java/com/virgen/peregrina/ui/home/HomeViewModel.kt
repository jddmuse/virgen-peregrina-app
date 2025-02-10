package com.virgen.peregrina.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.data.model.toModel
import com.virgen.peregrina.domain.pilgrimage.GetAllPilgrimagesUseCase
import com.virgen.peregrina.domain.SendTestimonyUseCase
import com.virgen.peregrina.util.base.BaseResponseRunner
import com.virgen.peregrina.util.getCurrentDate
import com.virgen.peregrina.util.getExceptionLog
import com.virgen.peregrina.util.manager.PreferencesManager
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

    private val _pilgrimages = MutableLiveData<List<PilgrimageModel>>()
    val pilgrimages: LiveData<List<PilgrimageModel>> get() = _pilgrimages

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg

    private val _infoMsg = MutableLiveData<String?>()
    val infoMsg: LiveData<String?> get() = _infoMsg

    private val _askForReturningReplicaAndTestimonyEvent = MutableLiveData<PilgrimageModel>()
    val askForReturningReplicaAndTestimonyEvent: LiveData<PilgrimageModel> get() = _askForReturningReplicaAndTestimonyEvent

    private val _userNameTitle = MutableLiveData<String>()
    val userNameTitle: LiveData<String> get() = _userNameTitle

    fun onCreate() {
        try {
            viewModelScope.launch {
                when (val result = getAllPilgrimagesUseCase()) {
                    is BaseResponseRunner.Error -> {
                        _errorMsg.value = resourceProvider.getStringResource(R.string.error_generic)
                    }
                    is BaseResponseRunner.Success -> {
                        _pilgrimages.value = result.data?.map { it.toModel() } ?: listOf()
                    }
                    is BaseResponseRunner.NullOrEmptyData -> {}
                }
            }
            preferencesManager.userSessionData?.name?.let { _userNameTitle.value = it }
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
                    is BaseResponseRunner.Success -> {
                        _infoMsg.value =
                            resourceProvider
                                .getStringResource(R.string.info_testimony_send_successfully)
                    }
                    is BaseResponseRunner.Error -> {}
                    is BaseResponseRunner.NoInternetConnection -> {
                        _errorMsg.value =
                            resourceProvider
                                .getStringResource(R.string.error_no_internet_connection)
                    }
                    is BaseResponseRunner.NullOrEmptyData -> {}
                }
            }
        } catch (ex: Exception) {
            getExceptionLog(TAG, "onSendTestimonyUseCase", ex)
        }
    }

    fun askForTestimony() {
        preferencesManager.userSessionData?.replicas?.forEach { replica ->
            replica.pilgrimages.forEach { pilgrimage ->
                _askForReturningReplicaAndTestimonyEvent.value = pilgrimage
            }
        }
    }

}