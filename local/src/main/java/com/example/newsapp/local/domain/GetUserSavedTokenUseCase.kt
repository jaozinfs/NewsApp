package com.example.newsapp.local.domain

import com.example.newsapp.userpreferences.UserPreferencesRepository
import kotlinx.coroutines.flow.map
import timber.log.Timber

class GetUserSavedTokenUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    fun execute() =
        userPreferencesRepository.userTokenFlow.map {
            Timber.d("AQUI : ${it?.token}")
            it?.token
        }

}