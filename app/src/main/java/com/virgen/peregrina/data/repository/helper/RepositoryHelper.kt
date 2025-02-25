package com.virgen.peregrina.data.repository.helper

import android.util.Log
import com.example.virgen_peregrina_app.R
import com.google.gson.Gson
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponseRepository
import com.virgen.peregrina.util.response.ResponseService
import retrofit2.Response
import javax.inject.Inject

class RepositoryHelper @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    companion object {
        private const val TAG = "RepositoryHelper"
    }

    fun <T> response(response: Response<ResponseService<T>>): ResponseRepository<T> {
        try {
            return when(response.code()) {
                200 -> {
                    val body = response.body()
                    if(body?.data == null) {
                        val error = resourceProvider.getStringResource(R.string.error_generic)
                        ResponseRepository.ApiError(
                            error = body?.error ?: error,
                            message = body?.message
                        )
                    } else
                        ResponseRepository.Success(body.data)
                }
                400, 404 -> {
                    val errorBody = response.errorBody()?.string()
                    if(errorBody != null) {
                        val responseError = Gson().fromJson(errorBody, ResponseService::class.java)
                        ResponseRepository.ApiError(
                            message = responseError?.message,
                            error = responseError?.error
                        )
                    } else {
                        val error = resourceProvider.getStringResource(R.string.error_generic)
                        ResponseRepository.ApiError(message = error)
                    }
                }
                else -> {
                    val error = resourceProvider.getStringResource(R.string.error_generic)
                    ResponseRepository.ApiError(message = error)
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "login(): Exception -> $ex")
            return ResponseRepository.Error(ex)
        }
    }

}