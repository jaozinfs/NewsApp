package com.example.newsapp.home.presentation

import android.os.Bundle
import com.example.common.base.BaseAuthActivity
import com.example.newsapp.home.R
import com.example.newsapp.home.di.homeModules
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class 
HomeActivity : BaseAuthActivity(R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(homeModules)
    }

    override fun onStop() {
        unloadKoinModules(homeModules)
        super.onStop()
    }
}