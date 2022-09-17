package com.virgen.peregrina.data.repository

import android.util.Log
import com.virgen.peregrina.data.api.api_client.VirgenPeregrinaApiClient
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.data.request.SignUpRequest
import com.virgen.peregrina.data.response.LoginResponse
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.base.BaseResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val virgenPeregrinaApiClient: VirgenPeregrinaApiClient
) {

    companion object {
        private const val TAG = "UserRepository"
    }

    suspend fun login(loginRequest: LoginRequest): BaseResultRepository<LoginResponse> =
        withContext(Dispatchers.IO) {
            try {
                Log.i(TAG, "$METHOD_CALLED login()")
                val result = virgenPeregrinaApiClient.loginWithVirgenPeregrina(loginRequest)
                BaseResultRepository.Success(result.data)
            } catch (ex: Exception) {
                Log.e(TAG, "login(): Exception -> $ex")
                BaseResultRepository.Error(ex)
            }
        }

    suspend fun signUp(singUpRequest: SignUpRequest) =
        withContext(Dispatchers.IO) {
            try {
                Log.i(TAG, "$METHOD_CALLED signUp()")
                val result = virgenPeregrinaApiClient.signUpWithVirgenPeregrina(singUpRequest)
                BaseResultRepository.Success(result.data)
            } catch (ex: Exception) {
                Log.e(TAG, "signUp(): Exception -> $ex")
                BaseResultRepository.Error(ex)
            }
        }

}