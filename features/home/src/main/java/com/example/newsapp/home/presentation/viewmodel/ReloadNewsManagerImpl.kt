package com.example.newsapp.home.presentation.viewmodel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import timber.log.Timber
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@OptIn(ExperimentalTime::class)
class ReloadNewsManagerImpl : ReloadNewsManager {
    private var DELAY_TIMER = 30.seconds
    private var policyReload: ReloadTypes = ReloadTypes.AllNews
    private val reloadNewsSharedFlow = MutableSharedFlow<ReloadTypes>()

    override val reloadNewsFlow: SharedFlow<ReloadTypes>
        get() = reloadNewsSharedFlow

    override fun setUpdatePolicy(timerSeconds: Int) {
        DELAY_TIMER = timerSeconds.seconds
    }

    override fun setReloadType(reloadTypes: ReloadTypes) {
        policyReload = reloadTypes
    }

    init {
        initReload()
    }


    //Global scope aqui não é perigoso pois quem escutar tera seu proprio escopo,
    //um exemplo bom seria viewmodelScope.
    private fun initReload() = GlobalScope.launch {
        do {
            delay(DELAY_TIMER)
            reloadNewsSharedFlow.emit(policyReload)
        }while (coroutineContext.isActive)
    }


}