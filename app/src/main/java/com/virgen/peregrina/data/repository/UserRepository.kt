package com.virgen.peregrina.data.repository

import android.util.Log
import com.google.gson.Gson
import com.virgen.peregrina.data.api.service.VirgenPeregrinaApiClient
import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.request.CreateUserRequest
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.util.response.ResponseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiClient: VirgenPeregrinaApiClient
) {

    companion object {
        private const val TAG = "UserRepository -> "
    }

    suspend fun login(data: LoginRequest): ResponseRepository<UserModel> {
        withContext(Dispatchers.IO) {
            try {
                Log.i(TAG, "login() PARAMS: ${Gson().toJson(data)}")
                val responseApi = apiClient.login(data)
                when(responseApi.code()) {
                    200 -> {
                        val body = responseApi.body()
                    }
                    400 -> {}
                    404 -> {}
                    else -> {}
                }
            } catch (ex:Exception) {
                Log.e(TAG, "login(): Exception -> $ex")
                ResponseRepository.Error(ex)
            }
        }
        return ResponseRepository.Error(Exception())
    }

    suspend fun create(data: CreateUserRequest): ResponseRepository<UserModel> {
        withContext(Dispatchers.IO) {
            try {
                Log.i(TAG, "create() PARAMS: ${Gson().toJson(data)}")
                val responseApi = apiClient.createUser(data)
                when(responseApi.code()) {
                    200 -> {
                        val body = responseApi.body()

                    }
                    400 -> {}
                    404 -> {}
                    else -> {}
                }
            } catch (ex: Exception) {
                Log.e(TAG, "create(): Exception -> $ex")
                ResponseRepository.Error(ex)
            }
        }
        return ResponseRepository.Error(Exception())
    }

//    suspend fun login(loginRequest: LoginRequest): BaseResponseRepository<LoginResponse> =
//        withContext(Dispatchers.IO) {
//            try {
//                Log.i(TAG, "$METHOD_CALLED login()")
//                val result = virgenPeregrinaApiClient.loginWithVirgenPeregrina(loginRequest)
//                BaseResponseRepository.Success(result.data)
//            } catch (ex: Exception) {
//                Log.e(TAG, "login(): Exception -> $ex")
//                BaseResponseRepository.Error(ex)
//            }
//        }

//    suspend fun signUp(singUpRequest: SignUpRequest) = withContext(Dispatchers.IO) {
//        try {
//            Log.i(TAG, "$METHOD_CALLED signUp()")
//            val result = virgenPeregrinaApiClient.signUpWithVirgenPeregrina(singUpRequest)
//            BaseResponseRepository.Success(result.data)
//        } catch (ex: Exception) {
//            Log.e(TAG, "signUp(): Exception -> $ex")
//            BaseResponseRepository.Error(ex)
//        }
//    }
//
//    suspend fun getAllPilgrims() = withContext(Dispatchers.IO) {
//        try {
//            Log.i(TAG, "$METHOD_CALLED getAllPilgrims()")
//            val result = virgenPeregrinaApiClient.getAllPilgrims()
//            BaseResponseRepository.Success(result.data)
//        } catch (ex: Exception) {
//            Log.e(TAG, "getAllPilgrims(): Exception -> $ex")
//            BaseResponseRepository.Error(ex)
//        }
//    }

}