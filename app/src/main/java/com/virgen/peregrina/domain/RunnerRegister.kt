package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.repository.UserRepository
import com.virgen.peregrina.data.request.CreateUserRequest
import com.virgen.peregrina.domain.helper.RunnerHelper
import com.virgen.peregrina.util.response.ResponseRunner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RunnerRegister @Inject constructor(
    private val repository: UserRepository,
    private val runnerHelper: RunnerHelper
) {

    suspend fun invoke(data: CreateUserRequest): ResponseRunner<UserModel> {
        val response = repository.create(data)
        return runnerHelper.response(response)
    }
}