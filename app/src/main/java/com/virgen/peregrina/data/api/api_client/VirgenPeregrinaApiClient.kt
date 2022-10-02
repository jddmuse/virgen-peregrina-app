package com.virgen.peregrina.data.api.api_client

import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.data.request.SignUpRequest
import com.virgen.peregrina.data.response.LoginResponse
import com.virgen.peregrina.data.response.SignUpResponse
import com.virgen.peregrina.util.base.BaseResponseApi
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VirgenPeregrinaApiClient {

    @POST("user/login")
    suspend fun loginWithVirgenPeregrina(
        @Body loginRequest: LoginRequest
    ): BaseResponseApi<LoginResponse>


    @POST("user/create")
    suspend fun signUpWithVirgenPeregrina(
        @Body signUpRequest: SignUpRequest
    ): BaseResponseApi<SignUpResponse>

    @GET("replica/get-all")
    suspend fun getAllReplicas(

    ): BaseResponseApi<List<ReplicaModel>>

}