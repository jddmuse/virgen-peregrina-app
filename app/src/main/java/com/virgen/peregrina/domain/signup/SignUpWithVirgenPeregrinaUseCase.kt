package com.virgen.peregrina.domain.signup

import android.util.Log
import com.virgen.peregrina.data.repository.UserRepository
import com.virgen.peregrina.data.request.SignUpRequest
import com.virgen.peregrina.data.response.SignUpResponse
import com.virgen.peregrina.domain.login.LoginWithVirgenPeregrinaUseCase
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultRepository
import com.virgen.peregrina.util.base.BaseResultUseCase
import javax.inject.Inject

class SignUpWithVirgenPeregrinaUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    companion object {
        private const val TAG = "SignUpWithVirgenPeregrina"
    }

    suspend operator fun invoke(signUpRequest: SignUpRequest):
            BaseResultUseCase<SignUpResponse> =
        try {
            Log.i(TAG, "$METHOD_CALLED invoke()")
            Log.i(TAG, "PARAMS: $signUpRequest")
            when (val result = userRepository.signUp(signUpRequest)) {
                is BaseResultRepository.Success -> BaseResultUseCase.Success(result.data)
                is BaseResultRepository.Error -> BaseResultUseCase.NullOrEmptyData()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "invoke() -> Exception: $ex")
            BaseResultUseCase.Error(ex)
        }


}