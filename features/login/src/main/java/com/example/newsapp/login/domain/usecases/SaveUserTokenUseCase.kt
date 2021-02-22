package com.example.newsapp.login.domain.usecases

import com.example.common.base.BaseUseCase
import com.example.newsapp.userpreferences.UserPreferencesRepository

class SaveUserTokenUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) : BaseUseCase<SaveUserTokenUseCase.SaveUserTokenParams, Unit> {
    data class SaveUserTokenParams(
        val token: String
    )

    override suspend fun execute(params: SaveUserTokenParams) =
        userPreferencesRepository.saveToken(params.token)

}