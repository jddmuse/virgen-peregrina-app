package com.virgen.peregrina.util.response

sealed class ResponseRunner<out T : Any> {

    class Success<out T : Any>(
        val data: T?
    ) : ResponseRunner<T>()

    class NullOrEmptyData(
        val message: String? = null
    ) : ResponseRunner<Nothing>()

    class NoInternetConnection(
        val message: String? = null
    ): ResponseRunner<Nothing>()

    class Error(
        val exception: Throwable
    ) : ResponseRunner<Nothing>()

    class APIError<out T : Any>(
        val data: T?,
        val error: String?,
        val message: String?
    ) : ResponseRunner<T>()

}