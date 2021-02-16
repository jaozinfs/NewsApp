package com.example.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.common.domain.GetUserSavedTokenUseCase
import com.example.common.security.TokenManager
import com.example.common.security.UserAuthentication

class UserTokenManager(
    private val getUserTokenSavedUseCase: GetUserSavedTokenUseCase
) : TokenManager {

    override val tokenObservable: LiveData<String?> =
        getUserTokenSavedUseCase.execute().asLiveData()

    override fun checkAuthenticationByToken(token: String?): UserAuthentication =
        UserAuthentication.UserAuthenticated.takeIf { token != null }
            ?: UserAuthentication.TokenNotAuthenticated

    override fun getToken(): String? = tokenObservable.value

}