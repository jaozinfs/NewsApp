package com.example.newsapp.local.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.common.TokenManager
import com.example.common.UserAuthentication
import com.example.newsapp.local.domain.GetUserSavedTokenUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object UserTokenManager : TokenManager, KoinComponent {

    private val getUserTokenSavedUseCase: GetUserSavedTokenUseCase by inject()

    override val tokenObservable: LiveData<String?> =
        getUserTokenSavedUseCase.execute().asLiveData()

    private var currentToken: String? = null

    init {
        updateCurrentToken()
    }

    override fun checkAuthenticationByToken(token: String?): UserAuthentication =
        UserAuthentication.UserAuthenticated.takeIf { token != null && token!= "" }
            ?: UserAuthentication.TokenNotAuthenticated


    override fun getCurrentToken(): String? = currentToken

    private fun updateCurrentToken() = GlobalScope.launch {
        getUserTokenSavedUseCase.execute().collect {
            currentToken = it
        }
    }
}