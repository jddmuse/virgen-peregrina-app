package com.virgen.peregrina.util.response

sealed class ResponseRepository<out T : Any> {
    class Success<out T : Any>(val data: T?) : ResponseRepository<T>()
    class ApiError(val message: String): ResponseRepository<Nothing>()
    class Error(val exception: Throwable) : ResponseRepository<Nothing>()
}