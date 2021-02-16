package com.example.common.security

sealed class UserAuthentication {
    object TokenNotAuthenticated: UserAuthentication()
    object UserAuthenticated: UserAuthentication()
}