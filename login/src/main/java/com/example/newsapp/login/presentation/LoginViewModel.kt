package com.example.newsapp.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.newsapp.login.domain.usecases.GetUserSavedTokenUseCase
import com.example.newsapp.login.domain.usecases.SaveUserTokenUseCase
import com.example.newsapp.login.domain.usecases.UserLoginUseCase
import com.example.newsapp.login.presentation.event.LoginEvents
import com.example.newsapp.login.presentation.state.LoginState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userLoginUseCase: UserLoginUseCase,
    private val saveUserTokenUseCase: SaveUserTokenUseCase,
    private val userTokenUseCase: GetUserSavedTokenUseCase
) : BaseViewModel<LoginEvents, LoginState>() {
    private val _state = MutableLiveData<LoginState>()

    val token = userTokenUseCase.execute().asLiveData()

    override val state: LiveData<LoginState>
        get() = _state

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            with(_state) {
                value = LoginState.Loading
                value = try {
                    val token =
                        userLoginUseCase.execute(UserLoginUseCase.UserLoginParams(email, password))
                    saveUserTokenUseCase.execute(SaveUserTokenUseCase.SaveUserTokenParams(token))
                    LoginState.LoginSuccessfully
                } catch (error: Exception) {
                    LoginState.LoginError
                }
            }

        }
    }

    override fun handleEvent(event: LoginEvents) = when (event) {
        is LoginEvents.Login -> {
            login(event.email, event.password)
        }
    }

}