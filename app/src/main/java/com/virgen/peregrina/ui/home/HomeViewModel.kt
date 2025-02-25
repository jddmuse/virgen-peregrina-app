package com.virgen.peregrina.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val preferencesManager: PreferencesManager
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

    }
}