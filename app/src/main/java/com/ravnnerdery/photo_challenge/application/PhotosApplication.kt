package com.ravnnerdery.photo_challenge.application

import android.app.Application
import com.ravnnerdery.photo_challenge.repository.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PhotosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@PhotosApplication)
            modules(appModule)
        }
    }
}