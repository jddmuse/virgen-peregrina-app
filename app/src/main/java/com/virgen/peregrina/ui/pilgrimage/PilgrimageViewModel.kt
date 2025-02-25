package com.virgen.peregrina.ui.pilgrimage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.ui.pilgrimage.util.EnumPilgrimageInputType
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PilgrimageViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    // pilgrimage data
    private var setUserId: Long = -1
    private var setIntention = EMPTY_STRING
    private var setStartDate = EMPTY_STRING
    private var setEndDate = EMPTY_STRING
    private var setReplicaId: Long = -1

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
                    setIntention = value
                }
                EnumPilgrimageInputType.USER_ID -> {
                    value as Long
                    setUserId = value
                }
                EnumPilgrimageInputType.START_DATE -> {
                    value as String
                    setStartDate = value
                }
                EnumPilgrimageInputType.END_DATE -> {
                    value as String
                    setEndDate = value
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "onValueChanged() -> $ex")
        }
    }

    private fun valid(): Boolean = when {
        setUserId == -1L -> {
            _errorEditText.value = Pair(
                EnumPilgrimageInputType.USER_ID,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
            false
        }
        setIntention.isEmpty() -> {
            _errorEditText.value = Pair(
                EnumPilgrimageInputType.INTENTION,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
            false
        }
        setStartDate.isEmpty() -> {
            _errorEditText.value = Pair(
                EnumPilgrimageInputType.START_DATE,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
            false
        }
        setEndDate.isEmpty() -> {
            _errorEditText.value = Pair(
                EnumPilgrimageInputType.END_DATE,
                resourceProvider.getStringResource(R.string.error_field_required)
            )
            false
        }
        else -> {
            _errorEditText.value = Pair(EnumPilgrimageInputType.USER_ID, null)
            _errorEditText.value = Pair(EnumPilgrimageInputType.INTENTION, null)
            _errorEditText.value = Pair(EnumPilgrimageInputType.START_DATE, null)
            _errorEditText.value = Pair(EnumPilgrimageInputType.END_DATE, null)
            true
        }
    }

    fun create() {

    }

}