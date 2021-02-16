package com.example.common.domain

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