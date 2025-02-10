package com.virgen.peregrina.util.base

sealed class BaseResponseRunner<out T : Any> {

    class Success<out T : Any>(
        val data: T?
    ) : BaseResponseRunner<T>()

    class NullOrEmptyData(
        val message: String? = null
    ) : BaseResponseRunner<Nothing>()

    class NoInternetConnection(
        val message: String? = null
    ): BaseResponseRunner<Nothing>()

    class Error(
        val exception: Throwable
    ) : BaseResponseRunner<Nothing>()

    class APIError<out T : Any>(
        val data: T?,
        val error: String?,
        val message: String?
    ) : BaseResponseRunner<T>()

}