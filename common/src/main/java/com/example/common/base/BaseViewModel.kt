package com.example.common.base

import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.exceptions.BadTokenException
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import java.lang.Exception

abstract class BaseViewModel<T, S> : ViewModel(), KoinComponent {
    abstract val state: LiveData<S>
    abstract fun handleEvent(event: T)
    open val removeTokenUseCase: (suspend () -> Unit)? = null

    fun launchAuthenticated(call : suspend () -> Unit) = viewModelScope.launch {
        try {
            call.invoke()
        }catch (error: Exception){
            Timber.e(error)
            if(error is BadTokenException)
                removeTokenUseCase?.invoke()
        }
    }
}