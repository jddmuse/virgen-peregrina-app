package com.virgen.peregrina.util.base

sealed class BaseResponseRepository<out T : Any> {
    class Success<out T : Any>(val data: T?) : BaseResponseRepository<T>()
    class ApiError(val message: String): BaseResponseRepository<Nothing>()
    class Error(val exception: Throwable) : BaseResponseRepository<Nothing>()
}