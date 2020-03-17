package com.coronago.user

import android.content.Context
import com.coronago.utils.hasLocationPermission
import com.google.gson.Gson

private const val KEY_TIME_ONBOARDED = "KEY_TIME_ONBOARDED"

class UserStore(
    private val appContext: Context
) {

    private val sp = appContext.getSharedPreferences("USER_STORE", Context.MODE_PRIVATE)

    fun ensurePreConditions(callback: Callback) {
        if(sp.getLong(KEY_TIME_ONBOARDED, 0) == 0L) {
            callback.onOnboardingRequired()
        } else if(!appContext.hasLocationPermission()) {
            callback.onLocationPermissionRequired()
        } else {
            callback.onReady()
        }
    }

    fun justOnboarded() {
        sp.edit().putLong(KEY_TIME_ONBOARDED, System.currentTimeMillis()).apply()
    }

    interface Callback {

        fun onOnboardingRequired()

        fun onLocationPermissionRequired()

        fun onReady()
    }
}