package com.example.common

import com.example.common.di.FeaturesModulesManager
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

class KoinModulesManager(
    private val modules: Module
) : FeaturesModulesManager {


    override fun loadModules(): Boolean = try {
        loadKoinModules(modules)
        true
    } catch (error: Exception) {
        false
    }


    override fun unloadModules(): Boolean = try {
        unloadKoinModules(modules)
        true
    } catch (error: Exception) {
        false
    }

}