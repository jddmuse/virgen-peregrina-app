package com.virgen.peregrina.data.api.service

import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.request.CreateUserRequest
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.util.base.BaseResponseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VirgenPeregrinaApiClient {

    @POST("auth/login")
    suspend fun login(
        @Body data: LoginRequest
    ): Response<BaseResponseService<UserModel>>

    @POST("user/create")
    suspend fun createUser(
        @Body data: CreateUserRequest
    ): Response<BaseResponseService<UserModel>>

//    @POST("user/login")
//    suspend fun loginWithVirgenPeregrina(
//        @Body loginRequest: LoginRequest
//    ): BaseResponseApi<LoginResponse>
//
//    @POST("user/create")
//    suspend fun signUpWithVirgenPeregrina(
//        @Body signUpRequest: SignUpRequest
//    ): BaseResponseApi<SignUpResponse>
//
//    @GET("replica/get-all")
//    suspend fun getAllReplicas(
//
//    ): BaseResponseApi<List<ReplicaModel>>
//
//    @GET("user/get-replicas/{userId}")
//    suspend fun getAllReplicasByUser(
//        @Path("userId") userId: Long
//    ): BaseResponseApi<List<ReplicaModel>>
//
//    @POST("replica/create")
//    suspend fun createReplica(
//        @Body request: CreateReplicaRequest
//    ): BaseResponseApi<ReplicaModel>
//
//    @GET("testimony/get-all/{replica_id}")
//    suspend fun getTestimoniesByReplica(
//        @Path("replica_id") replica_id: Long
//    ): BaseResponseApi<List<TestimonyModel>>
//
//    @POST("testimony/create")
//    suspend fun sendTestimony(
//        @Body data: TestimonyModel
//    ): BaseResponseApi<TestimonyModel>
//
//    @GET("user/get-all-pilgrims")
//    suspend fun getAllPilgrims(): BaseResponseApi<List<UserModel>>
//
//    @POST("pilgrimage/create")
//    suspend fun createPilgrimage(
//        @Body data: CreatePilgrimageRequest
//    ): BaseResponseApi<PilgrimageModel>
//
//    @GET("pilgrimage/get-all")
//    suspend fun getAllPilgrimages(): BaseResponseApi<List<GetPilgrimagesResponse>>

}