package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.commonModules
import com.example.newsapp.network.di.networkModules
import com.example.newsapp.userpreferences.di.userPreferencesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class NewsAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@NewsAppApplication)
            modules(commonModules, userPreferencesModule, networkModules)
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}