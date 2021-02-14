package com.example.newsapp.register.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.extensions.asLiveData
import com.example.newsapp.register.domain.usecases.GetUserSavedTokenUseCase
import com.example.newsapp.register.domain.usecases.SaveRegisteredTokenUserCase
import com.example.newsapp.register.domain.usecases.UserRegisterUseCase
import com.example.newsapp.register.presentation.event.RegisterEvents
import com.example.newsapp.register.presentation.state.RegisterState
import kotlinx.coroutines.launch
import timber.log.Timber

class RegisterViewModel(
    private val userRegisterUseCase: UserRegisterUseCase,
    private val savedTokenUseCase: SaveRegisteredTokenUserCase,
    private val getUserSavedTokenUseCase: GetUserSavedTokenUseCase
) : ViewModel() {
    private val TAG = this@RegisterViewModel::class.simpleName

    private val _registerState = MutableLiveData<RegisterState>()
    val registerState = _registerState.asLiveData

    val userToken = getUserSavedTokenUseCase.execute().asLiveData()

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
                savedTokenUseCase.execute(SaveRegisteredTokenUserCase.SaveUserTokenParams(token))
                Timber.tag(TAG).d(token)
                RegisterState.RegisterSuccessfully
            } catch (error: Exception) {
                Timber.tag(TAG).e(error)
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