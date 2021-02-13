package com.example.newsapp.register.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.extensions.asLiveData
import com.example.newsapp.register.domain.usecases.UserRegisterUseCase
import com.example.newsapp.register.presentation.event.RegisterEvents
import com.example.newsapp.register.presentation.state.RegisterState
import com.example.newsapp.security.TokenStorage
import com.example.newsapp.security.UserTokenState
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterViewModel(
    private val userRegisterUseCase: UserRegisterUseCase,
    private val tokenStorage: TokenStorage
) : ViewModel() {
    private val _registerState = MutableLiveData<RegisterState>()
    val registerState = _registerState.asLiveData


    private fun register(email: String, password: String, name: String, birthDate: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            _registerState.value = try {
               val token = userRegisterUseCase.execute(
                    UserRegisterUseCase.UserRegisterUseCaseParams(
                        email,
                        password,
                        name,
                        birthDate
                    )
                )
                tokenStorage.setToken(token)
                RegisterState.RegisterSuccessfully
            } catch (error: Exception) {
                Log.d("Teste", "Error")
                RegisterState.RegisterError
            }
        }
    }


    fun handleEvent(event: RegisterEvents) = when (event) {
        is RegisterEvents.Register -> {
            register(event.email, event.password, event.name, event.birthDate)
        }
        else -> {

        }
    }
}