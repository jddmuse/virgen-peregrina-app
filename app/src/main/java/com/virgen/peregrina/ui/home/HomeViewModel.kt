package com.virgen.peregrina.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.pilgrimage.PilgrimageModel
import com.virgen.peregrina.domain.RunnerPilgrimages
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponseRunner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val preferencesManager: PreferencesManager,
    private val runnerPilgrimages: RunnerPilgrimages
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

    private val _userNameTitle = MutableLiveData<String>()
    val userNameTitle: LiveData<String> get() = _userNameTitle


    fun pilgrimages() {
        viewModelScope.launch {
            when (val response = runnerPilgrimages.invoke()) {
                is ResponseRunner.Success -> {
                    val data = response.data?.content ?: listOf()
                    _pilgrimages.value = data
                }
                is ResponseRunner.ApiError -> {
                    _errorMsg.value = response.message
                }
                is ResponseRunner.NoInternetConnection -> {
                    _errorMsg.value = resourceProvider.getStringResource(R.string.error_no_internet_connection)
                }
                else -> {
                    _errorMsg.value = resourceProvider.getStringResource(R.string.error_generic)
                }
            }
        }
    }
}