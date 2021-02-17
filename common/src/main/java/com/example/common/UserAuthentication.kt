package com.example.common

sealed class UserAuthentication {
    object TokenNotAuthenticated: UserAuthentication()
    object UserAuthenticated: UserAuthentication()
}