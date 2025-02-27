package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.user.UserModel
import com.virgen.peregrina.data.repository.UserRepository
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.domain.helper.RunnerHelper
import com.virgen.peregrina.util.provider.NetworkProvider
import com.virgen.peregrina.util.provider.ResourceProvider
import com.virgen.peregrina.util.response.ResponseRunner
import javax.inject.Inject

class RunnerLogin @Inject constructor(
    private val userRepository: UserRepository,
    private val networkProvider: NetworkProvider,
    private val resourceProvider: ResourceProvider,
    private val runnerHelper: RunnerHelper
) {

    suspend fun invoke(data: LoginRequest): ResponseRunner<UserModel> {
        if(!networkProvider.isConnected())
            return ResponseRunner.NoInternetConnection()
        val response = userRepository.login(data)
        return runnerHelper.response(response)
    }
}