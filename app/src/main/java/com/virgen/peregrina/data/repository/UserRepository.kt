package com.virgen.peregrina.data.repository

import android.util.Log
import com.google.gson.Gson
import com.virgen.peregrina.data.api.service.VirgenPeregrinaApiClient
import com.virgen.peregrina.data.model.user.UserModel
import com.virgen.peregrina.data.repository.helper.RepositoryHelper
import com.virgen.peregrina.data.request.CreateUserRequest
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.data.response.LoginResponse
import com.virgen.peregrina.util.response.ResponseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: VirgenPeregrinaApiClient,
    private val repositoryHelper: RepositoryHelper
) {

    companion object {
        private const val TAG = "UserRepository"
    }

    suspend fun login(data: LoginRequest): ResponseRepository<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.i(TAG, "login() PARAMS: ${Gson().toJson(data)}")
                val responseApi = apiService.login(data)
                return@withContext repositoryHelper.response(responseApi)
            } catch (ex:Exception) {
                Log.e(TAG, "login(): Exception -> $ex")
                ResponseRepository.Error(ex)
            }
        }
    }

    suspend fun create(data: CreateUserRequest): ResponseRepository<UserModel> {
        return withContext(Dispatchers.IO) {
            try {
                Log.i(TAG, "create() PARAMS: ${Gson().toJson(data)}")
                val responseApi = apiService.createUser(data)
                return@withContext repositoryHelper.response(responseApi)
            } catch (ex:Exception) {
                Log.e(TAG, "create(): Exception -> $ex")
                ResponseRepository.Error(ex)
            }
        }
    }

}