package com.virgen.peregrina.data.api.service

import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.model.UserModel
import com.virgen.peregrina.data.request.CreateReplicaRequest
import com.virgen.peregrina.data.request.CreateUserRequest
import com.virgen.peregrina.data.request.LoginRequest
import com.virgen.peregrina.util.response.ResponsePage
import com.virgen.peregrina.util.response.ResponseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VirgenPeregrinaApiClient {

    /** User ************************************************/
    /********************************************************/

    @POST("auth/login")
    suspend fun login(
        @Body data: LoginRequest
    ): Response<ResponseService<UserModel>>

    @POST("user/create")
    suspend fun createUser(
        @Body data: CreateUserRequest
    ): Response<ResponseService<ResponsePage<UserModel>>>

    /** Replica *********************************************/
    /********************************************************/

    @POST("replica/create")
    suspend fun createReplica(
        @Body data: CreateReplicaRequest
    ): Response<ResponseService<ReplicaModel>>

    @GET("replica/list")
    suspend fun listReplicas(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String //= "nombre,asc"
    ): Response<ResponseService<List<ReplicaModel>>>

    /** Pilgrimage ******************************************/
    /********************************************************/

    @POST("pilgrimage/create")
    suspend fun createPilgrimage(
        @Body data: CreateReplicaRequest
    ): Response<ResponseService<ReplicaModel>>

    @GET("pilgrimage/list")
    suspend fun listPilgrimages(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String //= "nombre,asc"
    ): Response<ResponseService<List<PilgrimageModel>>>


}