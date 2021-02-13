package com.example.newsapp.network.exceptions

class BadRequestException(
    var code: Int = -1,
    var error: String?,
    cause: Throwable? = null
) :
    Exception(error)