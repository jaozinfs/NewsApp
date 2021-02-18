package com.example.common.extensions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext


fun flipModuleFlow(startPosition: Int, count: Int, delayMillis: Long = 2_000) = flow {
    var s = startPosition % count
    do {
        emit(s)
        delay(delayMillis)
        s = (s + 1) % count
    } while (coroutineContext.isActive)
}