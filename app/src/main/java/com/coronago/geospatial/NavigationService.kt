package com.coronago.geospatial

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NavigationService: Service() {

    override fun onBind(intent: Intent?): IBinder? = null

}