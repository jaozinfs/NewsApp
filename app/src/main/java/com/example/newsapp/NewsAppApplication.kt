package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.commonModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@NewsAppApplication)
            modules(commonModules)
        }
    }
}