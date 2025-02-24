package com.virgen.peregrina.util.response

sealed class ResponseRunner<out T : Any> {

    class Success<out T : Any>(
        val data: T?
    ) : ResponseRunner<T>()

    class ApiError(
        val error: String?,
        val message: String?,
    ): ResponseRunner<Nothing>()

    class Error(
        val message: String,
        val exception: Throwable? = null
    ) : ResponseRunner<Nothing>()

    class NoInternetConnection(
        val message: String? = null
    ) : ResponseRunner<Nothing>()

    class NullOrEmptyData(
        val message: String? = null
    ) : ResponseRunner<Nothing>()

}