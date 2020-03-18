package com.coronago.setup

import android.content.Context
import com.coronago.utils.hasLocationPermission

private const val KEY_TIME_ONBOARDED = "KEY_TIME_ONBOARDED"

class UserSetup(
    private val appContext: Context
) {

    private val sp = appContext.getSharedPreferences("USER_STORE", Context.MODE_PRIVATE)

    fun checkSetup(callback: Callback) {
        if(!sp.contains(KEY_TIME_ONBOARDED)) {
            callback.onOnboardingRequired()
        } else if(!appContext.hasLocationPermission()) {
            callback.onLocationPermissionRequired()
        } else {
            callback.onSetupComplete()
        }
    }

    fun justOnboarded() {
        sp.edit()
            .putLong(KEY_TIME_ONBOARDED, System.currentTimeMillis())
            .apply()
    }

    interface Callback {

        fun onOnboardingRequired()

        fun onLocationPermissionRequired()

        fun onSetupComplete()
    }
}