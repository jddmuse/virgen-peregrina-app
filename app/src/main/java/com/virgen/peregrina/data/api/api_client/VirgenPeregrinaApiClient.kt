package com.virgen.peregrina.data.api.api_client

import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.request.CreatePilgrimageRequest
import com.virgen.peregrina.data.request.CreateReplicaRequest
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.data.request.SignUpRequest
import com.virgen.peregrina.data.response.GetPilgrimagesResponse
import com.virgen.peregrina.data.response.LoginResponse
import com.virgen.peregrina.data.response.SignUpResponse
import com.virgen.peregrina.util.base.BaseResponseApi
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("replica/get-replicas/{userId}")
    suspend fun getAllReplicasByUser(
        @Path("userId") userId: Long
    ): BaseResponseApi<List<ReplicaModel>>

    @POST("replica/create")
    suspend fun createReplica(
        @Body request: CreateReplicaRequest
    ): BaseResponseApi<ReplicaModel>

    @GET("testimony/get-all/{replica_id}")
    suspend fun getTestimoniesByReplica(
        @Path("replica_id") replica_id: Long
    ): BaseResponseApi<List<TestimonyModel>>

    @POST("testimony/create")
    suspend fun sendTestimony(
        @Body data: TestimonyModel
    ): BaseResponseApi<TestimonyModel>

    @GET("user/get-all-pilgrims")
    suspend fun getAllPilgrims(): BaseResponseApi<List<UserModel>>

    @POST("pilgrimage/create")
    suspend fun createPilgrimage(
        @Body data: CreatePilgrimageRequest
    ): BaseResponseApi<PilgrimageModel>

    @GET("pilgrimage/get-all")
    suspend fun getAllPilgrimages(): BaseResponseApi<List<GetPilgrimagesResponse>>

}