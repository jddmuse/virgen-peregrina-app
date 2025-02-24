package com.virgen.peregrina.util.response

class ResponseService<T>(
    val data: T? = null,
    val message: String? = null,
    val error: String? = null,
    val success: Boolean = false
)