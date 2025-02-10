package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.util.base.BaseResponseRunner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRunner {

    suspend fun invoke(data: LoginRequest): BaseResponseRunner<UserModel> {
        return BaseResponseRunner.NullOrEmptyData()
    }
}