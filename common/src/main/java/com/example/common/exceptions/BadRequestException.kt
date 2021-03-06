package com.example.common.exceptions

class BadRequestException(
    var code: Int = -1,
    var error: String?,
    cause: Throwable? = null
) :
    Exception(error)