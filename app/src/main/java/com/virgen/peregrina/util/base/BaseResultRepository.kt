package com.virgen.peregrina.util.base

sealed class BaseResultRepository<out T : Any> {
    class Success<out T : Any>(val data: T?) : BaseResultRepository<T>()
    class Error(val exception: Throwable) : BaseResultRepository<Nothing>()
}