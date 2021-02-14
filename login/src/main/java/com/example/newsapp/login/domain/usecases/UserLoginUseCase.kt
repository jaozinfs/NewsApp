package com.example.newsapp.login.domain.usecases

import com.example.newsapp.login.domain.repository.Token
import com.example.newsapp.login.domain.repository.UserRepository
import com.example.common.base.BaseUseCase

class UserLoginUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<UserLoginUseCase.UserLoginParams, Token> {
    data class UserLoginParams(
        val email: String,
        val password: String
    )

    override suspend fun execute(params: UserLoginParams): Token =
        userRepository.login(params.email, params.password)

}