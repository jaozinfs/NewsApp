package com.example.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main){
    override fun onPause() {
        finish()
        super.onPause()
    }
}