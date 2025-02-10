package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.request.CreateUserRequest
import com.virgen.peregrina.util.base.BaseResponseRunner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterRunner {

    suspend fun invoke(data: CreateUserRequest): BaseResponseRunner<UserModel> {
        return withContext(Dispatchers.IO) {
            BaseResponseRunner.NullOrEmptyData()
        }
    }
}