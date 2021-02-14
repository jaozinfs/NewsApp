package com.example.newsapp.login.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.common.di.FeaturesModulesManager
import com.example.newsapp.login.R
import com.example.newsapp.login.di.loginModules
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class LoginActivity : AppCompatActivity(R.layout.activity_login){
   // private val featuresModulesManager: FeaturesModulesManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadModules()
    }


    override fun onDestroy() {
        unLoadModules()
        super.onDestroy()
    }

    private fun loadModules(){
       // featuresModulesManager.loadModules()
        loadKoinModules(loginModules)
    }

    private fun unLoadModules(){
        //featuresModulesManager.unloadModules()
        unloadKoinModules(loginModules)
    }

}