package com.example.newsapp.register.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.register.R
import com.example.newsapp.register.di.registerModules
import com.example.newsapp.register.presentation.viewmodel.RegisterViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class RegisterActivity : AppCompatActivity(R.layout.activity_register) {
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadModules()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadModules()
    }


    private fun loadModules() {
        loadKoinModules(registerModules)
    }

    private fun unloadModules() {
        unloadKoinModules(registerModules)
    }
}