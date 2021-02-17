package com.example.common.base

import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import java.lang.Exception

abstract class BaseViewModel<T, S> : ViewModel(), KoinComponent {
    abstract val state: LiveData<S>
    abstract fun handleEvent(event: T)
    open val removeTokenUseCase: (suspend () -> Unit)? = null

    fun launchAuthenticated() = viewModelScope.launch {
        try {

        }catch (error: Exception){
            Timber.e(error)
            if(error is WindowManager.BadTokenException)
                removeTokenUseCase?.invoke()
        }
    }
}