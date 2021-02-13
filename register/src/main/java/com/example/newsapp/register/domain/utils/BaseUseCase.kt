package com.example.newsapp.register.domain.utils

import android.text.PrecomputedText

interface BaseUseCase<Params, S> {
    suspend fun execute(params: Params): S
}