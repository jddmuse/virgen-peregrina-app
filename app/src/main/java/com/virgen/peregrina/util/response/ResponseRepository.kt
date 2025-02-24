package com.virgen.peregrina.util.response

sealed class ResponseRepository<out T> {

    class Success<out T>(
        val data: T?
    ) : ResponseRepository<T>()

    class ApiError(
        val error: String? = null,
        val message: String? = null
    ) : ResponseRepository<Nothing>()

    class Error(
        val exception: Throwable
    ) : ResponseRepository<Nothing>()

}