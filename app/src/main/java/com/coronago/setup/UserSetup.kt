package com.coronago.setup

import android.content.Context
import com.coronago.geospatial.LocationSettingsChecker
import com.coronago.utils.hasLocationPermission
import com.google.android.gms.common.api.ResolvableApiException

private const val KEY_TIME_ONBOARDED = "KEY_TIME_ONBOARDED"

class UserSetup(
    private val appContext: Context,
    private val locationSettingsChecker: LocationSettingsChecker
) {

    private val sp = appContext.getSharedPreferences("USER_STORE", Context.MODE_PRIVATE)

    fun checkSetup(callback: Callback) {
        if(!sp.contains(KEY_TIME_ONBOARDED)) {
            callback.onOnboardingRequired()
        } else if(!appContext.hasLocationPermission()) {
            callback.onLocationPermissionRequired()
        } else {
            checkLocationSettings(callback)
        }
    }

    private fun checkLocationSettings(callback: Callback) {
        locationSettingsChecker.check { result ->
            result.fold(
                { callback.onSetupComplete() },
                { exception ->
                    if(exception is ResolvableApiException) {
                        callback.onLocationPermissionRequired()
                    } else {
                        callback.onLocationSettingsCheckFailed()
                    }
                }
            )
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

        fun onLocationSettingsCheckRequired(exception: ResolvableApiException)

        fun onLocationSettingsCheckFailed()

        fun onSetupComplete()
    }
}