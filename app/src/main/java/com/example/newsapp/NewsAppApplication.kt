package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.commonModules
import com.example.newsapp.local.di.localModules
import com.example.newsapp.network.di.networkModules
import com.example.newsapp.userpreferences.di.userPreferencesModule
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class NewsAppApplication : Application() {
    private val splitInstallManager: SplitInstallManager by inject()

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
        setupModules()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@NewsAppApplication)
            modules(commonModules, userPreferencesModule, networkModules, localModules)
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun setupModules() {
        SplitInstallRequest
            .newBuilder()
            .addModule(getString(R.string.module_login))
            .addModule(getString(R.string.module_register))
            .addModule(getString(R.string.module_home))
            .build().apply {
                splitInstallManager.startInstall(this)
            }
    }
}