package com.virgen.peregrina.ui.pilgrimage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.request.CreatePilgrimageRequest
import com.virgen.peregrina.domain.RunnerCreatePilgrimage
import com.virgen.peregrina.ui.pilgrimage.util.EnumPilgrimageInputType
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.manager.PreferencesManager
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponseRunner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PilgrimageViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val preferencesManager: PreferencesManager,
    private val runnerCreatePilgrimage: RunnerCreatePilgrimage
) : ViewModel() {

    // pilgrimage data
    private var setUserId: Long = preferencesManager.userId
    private var setIntention = ""
    private var setStartDate: LocalDate? = null
    private var setEndDate: LocalDate? = null
    private var setReplicaId: Long = -1

    // events
    private val _createPilgrimageSuccess = MutableLiveData<Pair<Boolean, String>>()
    val createPilgrimageSuccess: LiveData<Pair<Boolean, String>> get() = _createPilgrimageSuccess

    // error live data
    private val _errorEditText = MutableLiveData<Pair<EnumPilgrimageInputType, String?>>()
    val errorEditText: LiveData<Pair<EnumPilgrimageInputType, String?>> get() = _errorEditText

    // ui behavior
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loading = MutableLiveData<Pair<Boolean, String>>()
    val loading: LiveData<Pair<Boolean, String>> get() = _loading

    companion object {
        private const val TAG = "PilgrimageViewModel"
    }

    fun valueChanged(value: Any, inputType: EnumPilgrimageInputType) {
        try {
            when (inputType) {
                EnumPilgrimageInputType.INTENTION -> {
                    value as String
                    if(value.isEmpty()) {
                        _errorEditText.value = Pair(
                            EnumPilgrimageInputType.INTENTION,
                            resourceProvider.getStringResource(R.string.error_field_required)
                        )
                        setIntention = ""
                        return
                    }
                    setIntention = value
                }
                EnumPilgrimageInputType.USER -> {
                    value as Long
                    setUserId = value
                }
                EnumPilgrimageInputType.REPLICA -> {
                    value as Long
                    setReplicaId = value
                }
                EnumPilgrimageInputType.START_DATE -> {
                    value as LocalDate
                    if(value.isEqual(LocalDate.now()) || value.isBefore(LocalDate.now())) {
                        _errorEditText.value = Pair(
                            EnumPilgrimageInputType.START_DATE,
                            resourceProvider.getStringResource(R.string.pilgrimage_error_date_current)
                        )
                        setStartDate = null
                        return
                    }
                    if(setEndDate != null && (value.isEqual(setEndDate) || value.isAfter(setEndDate))) {
                        _errorEditText.value = Pair(
                            EnumPilgrimageInputType.START_DATE,
                            resourceProvider.getStringResource(R.string.pilgrimage_error_start_date)
                        )
                        setStartDate = null
                        return
                    }
                    _errorEditText.value = Pair(EnumPilgrimageInputType.START_DATE, null)
                    setStartDate = value
                }
                EnumPilgrimageInputType.END_DATE -> {
                    value as LocalDate
                    if(value.isEqual(LocalDate.now()) || value.isBefore(LocalDate.now())) {
                        _errorEditText.value = Pair(
                            EnumPilgrimageInputType.END_DATE,
                            resourceProvider.getStringResource(R.string.pilgrimage_error_date_current)
                        )
                        setEndDate = null
                        return
                    }
                    if(setStartDate != null && (value.isEqual(setStartDate) || value.isBefore(setStartDate))) {
                        _errorEditText.value = Pair(
                            EnumPilgrimageInputType.END_DATE,
                            resourceProvider.getStringResource(R.string.pilgrimage_error_end_date)
                        )
                        setEndDate = null
                        return
                    }
                    _errorEditText.value = Pair(EnumPilgrimageInputType.END_DATE, null)
                    setEndDate = value
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    private fun valid(): Boolean = when {
        setUserId == -1L -> {
            _error.value = resourceProvider.getStringResource(R.string.pilgrimage_error_user_authentication)
            false
        }
        setIntention.isEmpty() -> {
            _errorEditText.value = Pair(
                EnumPilgrimageInputType.INTENTION,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
            false
        }
        setStartDate == null -> {
            _errorEditText.value = Pair(
                EnumPilgrimageInputType.START_DATE,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
            false
        }
        setEndDate == null -> {
            _errorEditText.value = Pair(
                EnumPilgrimageInputType.END_DATE,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
            false
        }
        else -> {
            _errorEditText.value = Pair(EnumPilgrimageInputType.USER, null)
            _errorEditText.value = Pair(EnumPilgrimageInputType.INTENTION, null)
            _errorEditText.value = Pair(EnumPilgrimageInputType.START_DATE, null)
            _errorEditText.value = Pair(EnumPilgrimageInputType.END_DATE, null)
            true
        }
    }

    fun create() {
        if(valid()) {
            val request = CreatePilgrimageRequest(
                replicaId = setReplicaId,
                userId = setUserId,
                startDate = setStartDate!!,
                endDate = setEndDate!!,
                intention = setIntention
            )
            _loading.value = Pair(true, "")
            viewModelScope.launch {
                when(val response = runnerCreatePilgrimage.invoke(request)) {
                    is ResponseRunner.Success -> {
                        _loading.value = Pair(false, "")
                        _createPilgrimageSuccess.value = Pair(true, "")
                    }
                    is ResponseRunner.ApiError -> {
                        _loading.value = Pair(false, "")
                        _error.value = StringBuilder()
                            .append(response.message ?: "")
                            .append("\n${response.error}").toString()
                    }
                    is ResponseRunner.NoInternetConnection -> {
                        _loading.value = Pair(false, "")
                        _error.value = resourceProvider.getStringResource(R.string.error_no_internet_connection)
                    }
                    else -> {
                        _loading.value = Pair(false, "")
                        _error.value = resourceProvider.getStringResource(R.string.error_generic)
                    }
                }
            }
        }
    }

}