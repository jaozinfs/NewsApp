package com.example.newsapp.register.domain.usecases

import com.example.newsapp.register.domain.repository.Token
import com.example.newsapp.register.domain.repository.UserRepository
import com.example.newsapp.register.domain.utils.BaseUseCase

class UserRegisterUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<UserRegisterUseCase.UserRegisterUseCaseParams, Token> {
    data class UserRegisterUseCaseParams(
        val email: String,
        val password: String,
        val name: String,
        val birthDate: String
    )

    override suspend fun execute(params: UserRegisterUseCaseParams): Token =
        userRepository.register(params.email, params.password, params.name, params.birthDate)

}