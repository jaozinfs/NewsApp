package com.example.newsapp.security

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.common.extensions.asLiveData

object TokenStorageImpl : TokenStorage {

    private val _userToken = MutableLiveData<UserTokenState>()

    val tokenState: LiveData<UserTokenState> = _userToken.asLiveData

    val userToken: String?
        get() {
            return (tokenState.value as? UserTokenState.UserAuthenticated)?.token
        }

    override fun setToken(token: String) {
        _userToken.value = UserTokenState.UserAuthenticated(token)
    }

    override fun deleteToken(): Boolean {
        _userToken.value = UserTokenState.UserNotAuthenticated
        return true
    }

}


