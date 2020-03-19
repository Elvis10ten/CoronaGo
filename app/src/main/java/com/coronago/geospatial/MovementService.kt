package com.coronago.geospatial

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.coronago.Injector

class MovementService: Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Injector.inject(this)
    }
}