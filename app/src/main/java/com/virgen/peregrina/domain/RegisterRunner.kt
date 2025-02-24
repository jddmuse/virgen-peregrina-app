package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.request.CreateUserRequest
import com.virgen.peregrina.util.response.ResponseRunner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRunner @Inject constructor() {

    suspend fun invoke(data: CreateUserRequest): ResponseRunner<UserModel> {
        return withContext(Dispatchers.IO) {
            ResponseRunner.NullOrEmptyData()
        }
    }
}