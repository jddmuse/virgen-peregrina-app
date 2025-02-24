package com.virgen.peregrina.domain

import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.repository.UserRepository
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.util.provider.NetworkProvider
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponseRepository
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerLogin @Inject constructor(
    private val userRepository: UserRepository,
    private val networkProvider: NetworkProvider,
    private val resourceProvider: ResourceProvider
) {

    suspend fun invoke(data: LoginRequest): ResponseRunner<UserModel> {
        if(!networkProvider.isConnected())
            return ResponseRunner.NoInternetConnection()

        return when(val response = userRepository.login(data)) {
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
    }
}