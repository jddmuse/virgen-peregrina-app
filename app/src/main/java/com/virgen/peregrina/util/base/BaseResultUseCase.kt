package com.virgen.peregrina.util.base

sealed class BaseResultUseCase<out T: Any> {
    class Success<out T: Any>(val data: T?): BaseResultUseCase<T>()
    class NullOrEmptyData(): BaseResultUseCase<Nothing>()
    class NoInternetConnection(): BaseResultUseCase<Nothing>()
    class InternetDBStatusError(val status: Int): BaseResultUseCase<Nothing>()
    class Error(val exception: Throwable): BaseResultUseCase<Nothing>()
    class APIError<out T: Any>(val data: T?): BaseResultUseCase<T>()
}