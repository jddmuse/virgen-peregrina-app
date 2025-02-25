package com.virgen.peregrina.domain.helper

import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponseRepository
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerHelper @Inject constructor(
    private val resourceProvider: ResourceProvider
){

    fun <T> response(response: ResponseRepository<T>): ResponseRunner<T> {
        try {
            return when(response) {
                is ResponseRepository.Success -> {
                    ResponseRunner.Success(response.data)
                }
                is ResponseRepository.ApiError -> {
                    ResponseRunner.ApiError(response.error, response.message)
                }
                is ResponseRepository.Error -> {
                    val error = resourceProvider.getStringResource(R.string.error_generic)
                    ResponseRunner.Error(error, response.exception)
                }
            }
        } catch (ex:Exception) {
            val error = resourceProvider.getStringResource(R.string.error_generic)
            return ResponseRunner.Error(error, ex)
        }
    }
}