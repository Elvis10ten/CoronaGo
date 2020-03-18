package com.coronago

import android.app.Application
import com.coronago.setup.AppInitializer

class CoronaGoApp: Application() {

    lateinit var appInitializer: AppInitializer

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
        Injector.inject(this)
        appInitializer.init()
    }
}