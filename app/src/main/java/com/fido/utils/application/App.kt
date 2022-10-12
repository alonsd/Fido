package com.fido.utils.application

import android.app.Application
import android.content.Context
import com.fido.service_locator.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        private var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(networkModule, repositoryModule, viewModelModule, remoteDataSourceModule)
        }
    }
}