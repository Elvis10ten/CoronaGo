package com.coronago.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.coronago.R

const val PRIMARY_NOTIFICATION_CHANNEL_ID = "primary"

object NotificationChannelUtils {

    fun setupChannels(appContext: Context) {
        if (AndroidVersion.isAtLeastOreo()) {
            val name = appContext.getString(R.string.primaryNotificationChannelName)
            val descriptionText = appContext.getString(R.string.primaryNotificationChannelDesc)
            val importance = NotificationManager.IMPORTANCE_HIGH

            NotificationChannel(PRIMARY_NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
                appContext.getNotificationManager().createNotificationChannel(this)
            }
        }
    }
}
