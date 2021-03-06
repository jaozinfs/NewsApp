package com.example.newsapp.register.domain.usecases

import com.example.common.base.BaseUseCase
import com.example.newsapp.userpreferences.UserPreferencesRepository

class SaveRegisteredTokenUserCase(
    private val userPreferencesRepository: UserPreferencesRepository
) : BaseUseCase<SaveRegisteredTokenUserCase.SaveUserTokenParams, Unit> {
    data class SaveUserTokenParams(
        val token: String
    )

    override suspend fun execute(params: SaveUserTokenParams) =
        userPreferencesRepository.saveToken(params.token)

}