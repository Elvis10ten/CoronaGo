package com.coronago

import android.app.Application
import timber.log.Timber

class CoronaGoApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initLogs()
        Injector.init(this)
    }

    private fun initLogs() {
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}