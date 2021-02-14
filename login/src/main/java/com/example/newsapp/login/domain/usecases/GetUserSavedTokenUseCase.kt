package com.example.newsapp.login.domain.usecases

import com.example.newsapp.userpreferences.UserPreferencesRepository
import kotlinx.coroutines.flow.map

class GetUserSavedTokenUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    fun execute() =
        userPreferencesRepository.userToken.map {
            it?.token
        }

}