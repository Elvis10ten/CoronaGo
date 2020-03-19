package com.coronago.geospatial

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.coronago.HomeActivity
import com.coronago.Injector
import com.coronago.R
import com.coronago.utils.PRIMARY_NOTIFICATION_CHANNEL_ID
import com.coronago.utils.getNotificationManager

private const val NOTIFICATION_ID = 1
private const val KEY_NOTIFICATION_MESSAGE = "KEY_NOTIFICATION_MESSAGE"

class MovementService: Service() {

    private val defaultContentIntent by lazy {
        PendingIntent.getActivity(
            this,
            0,
            HomeActivity.getIntent(this),
            PendingIntent.FLAG_CANCEL_CURRENT
        )
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Injector.inject(this)
        val notification = getBaseNotificationBuilder().build()
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        intent.getStringExtra(KEY_NOTIFICATION_MESSAGE)?.let { showMessage(it) }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showMessage(message: String) {
        val notification = getBaseNotificationBuilder()
            .setContentText(message)
            .build()
        getNotificationManager().notify(NOTIFICATION_ID, notification)
    }

    private fun getBaseNotificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, PRIMARY_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.appName))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(defaultContentIntent)
            .setContentText(getString(R.string.defaultNotificationText))
    }

    // TODO: Communicating via bound services would have been better

    companion object {

        fun notifyFirstChallengePassed(context: Context) {
            Intent(context, MovementService::class.java).apply {
                putExtra(KEY_NOTIFICATION_MESSAGE, context.getString(R.string.alertTitleFirstChallengePassed))
                context.startService(this)
            }
        }

        fun notifyFirstChallengeFailure(context: Context) {
            Intent(context, MovementService::class.java).apply {
                putExtra(KEY_NOTIFICATION_MESSAGE, context.getString(R.string.alertTitleFirstChallengeFailed))
                context.startService(this)
            }
        }

        fun notifyDefault(context: Context) {
            startService(context)
        }

        fun startService(context: Context) {
            Intent(context, MovementService::class.java).apply {
                putExtra(KEY_NOTIFICATION_MESSAGE, context.getString(R.string.defaultNotificationText))
                context.startService(this)
            }
        }

        fun stopService(context: Context) {
            context.stopService(Intent(context, MovementService::class.java))
        }
    }
}