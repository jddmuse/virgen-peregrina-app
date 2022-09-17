package com.virgen.peregrina.domain.login

import android.util.Log
import com.virgen.peregrina.data.repository.UserRepository
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.data.response.LoginResponse
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.base.BaseResultUseCase
import javax.inject.Inject
import kotlin.math.log

class LoginWithVirgenPeregrinaUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    companion object {
        private const val TAG = "LoginWithVirgenPeregrinaUseCase"
    }

    suspend operator fun invoke(loginRequest: LoginRequest):
            BaseResultUseCase<LoginResponse> =
        try {
            Log.i(TAG, "$METHOD_CALLED invoke()")
            Log.i(TAG, "PARAMS: $loginRequest")
            when (val result = userRepository.login(loginRequest)) {
                is BaseResultRepository.Success -> BaseResultUseCase.Success(result.data)
                is BaseResultRepository.Error -> BaseResultUseCase.NullOrEmptyData()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "invoke(): Exception -> $ex")
            BaseResultUseCase.Error(ex)
        }




}