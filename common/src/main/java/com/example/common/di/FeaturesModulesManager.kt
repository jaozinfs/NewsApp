package com.example.common.di

interface FeaturesModulesManager {
    fun loadModules(): Boolean
    fun unloadModules(): Boolean
}