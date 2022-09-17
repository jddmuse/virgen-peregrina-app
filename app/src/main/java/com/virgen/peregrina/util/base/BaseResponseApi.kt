package com.virgen.peregrina.util.base

data class BaseResponseApi<T>(
    val data: T? = null,
    val message: String? = null,
    val error: String? = null,
)