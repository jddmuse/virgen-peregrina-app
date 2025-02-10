package com.virgen.peregrina.util.base

class BaseResponseService<T>(
    val data: T? = null,
    val message: String? = null,
    val error: String? = null
)