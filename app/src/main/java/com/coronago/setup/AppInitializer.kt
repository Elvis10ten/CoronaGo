package com.coronago.setup

import android.content.Context
import com.coronago.BuildConfig
import com.coronago.utils.NotificationChannelUtils
import timber.log.Timber

class AppInitializer(
    private val appContext: Context
) {

    fun init() {
        initLogs()
        NotificationChannelUtils.setupChannels(appContext)
    }

    private fun initLogs() {
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}