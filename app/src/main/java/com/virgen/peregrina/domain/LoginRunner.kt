package com.virgen.peregrina.domain

import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.util.base.BaseResponseRunner
import javax.inject.Inject

class LoginRunner @Inject constructor() {

    suspend fun invoke(data: LoginRequest): BaseResponseRunner<UserModel> {
        return BaseResponseRunner.NullOrEmptyData()
    }
}