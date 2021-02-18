package com.example.newsapp.home.presentation.viewmodel

import kotlinx.coroutines.flow.Flow


interface ReloadNewsManager {
    val reloadNewsFlow: Flow<ReloadTypes>
    fun setUpdatePolicy(timerSeconds: Int)
    fun setReloadType(reloadTypes: ReloadTypes)
}

sealed class ReloadTypes {
    object HighLightsNews : ReloadTypes()
    object News : ReloadTypes()
    object AllNews : ReloadTypes()
}