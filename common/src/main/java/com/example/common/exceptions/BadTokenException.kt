package com.example.common.exceptions

class BadTokenException(
    var code: Int = -1,
    var error: String?,
    cause: Throwable? = null
) :
    Exception(error)