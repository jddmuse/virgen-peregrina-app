package com.virgen.peregrina.domain.signup

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.virgen.peregrina.util.base.BaseResultUseCase
import com.virgen.peregrina.data.api.firebase.AuthService
import com.virgen.peregrina.util.METHOD_CALLED
import javax.inject.Inject

class SignUpWithFirebaseUseCase @Inject constructor(
    private val authService: AuthService
) {

    companion object {
        private const val TAG = "SignUpWithFirebaseUseCase"
    }

    suspend operator fun invoke(email: String, password: String) = try {
        Log.i(TAG, "$METHOD_CALLED invoke()")
        val result: AuthResult = authService.signUp(email, password)
        if (result.user != null) {
            BaseResultUseCase.Success(result.user)
        } else {
            BaseResultUseCase.NullOrEmptyData()
        }
    } catch (ex: Exception) {
        Log.e(TAG, "invoke() -> Exception: $ex")
        BaseResultUseCase.Error(ex)
    }



}