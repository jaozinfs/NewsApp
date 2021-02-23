package com.example.newsapp.test.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.mockk.spyk
import kotlin.experimental.ExperimentalTypeInference


@OptIn(ExperimentalTypeInference::class)
fun <T> liveDataBuilder(@BuilderInference scope: LiveDataBuilderScope<T>.() -> Unit): LiveData<T> {
    val mutableLiveData = MutableLiveData<T>()
    scope.invoke(LiveDataBuilderScopeImpl(mutableLiveData))
    return mutableLiveData
}

class LiveDataBuilderScopeImpl<T>(
    private val target: MutableLiveData<T>
) : LiveDataBuilderScope<T> {

    override fun emit(value: T) {
        target.value = value
    }
}

interface LiveDataBuilderScope<T> {
    fun emit(value: T)
}

fun <T> spykKCollector(collect: (T)->Unit) = spyk<suspend (T) -> Unit>(
    {
        collect.invoke(it)
    }
)