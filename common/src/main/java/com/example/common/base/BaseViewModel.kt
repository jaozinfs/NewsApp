package com.example.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T, S> : ViewModel() {
    abstract val state: LiveData<S>
    abstract fun handleEvent(event: T)
}