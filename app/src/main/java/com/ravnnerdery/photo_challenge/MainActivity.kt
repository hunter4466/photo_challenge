package com.ravnnerdery.photo_challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.core.context.loadKoinModules

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(appModule)
        setContentView(R.layout.activity_main)
    }
}