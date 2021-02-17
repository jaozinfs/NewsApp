package com.example.newsapp.local.domain

import com.example.newsapp.userpreferences.UserPreferencesRepository
import kotlinx.coroutines.flow.map

class RemoveSavedTokenUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend fun execute() =
        userPreferencesRepository.removeToken()

}