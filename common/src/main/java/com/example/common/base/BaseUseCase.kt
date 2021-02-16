package com.example.common.base

interface BaseUseCase<Params, S> {
    suspend fun execute(params: Params): S
}

suspend fun <T: Any?, S> BaseUseCase<T,S>.execute(): S{
    return execute(null as T)
}