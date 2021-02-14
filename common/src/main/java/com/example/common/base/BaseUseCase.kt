package com.example.common.base

interface BaseUseCase<Params, S> {
    suspend fun execute(params: Params): S
}