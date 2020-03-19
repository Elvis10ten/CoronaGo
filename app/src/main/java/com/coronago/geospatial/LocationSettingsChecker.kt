package com.coronago.geospatial

import android.content.Context
import com.coronago.utils.Cancelable
import com.google.android.gms.location.*

class LocationSettingsChecker(
    private val appContext: Context
) {

    fun check(callback: (Result<LocationSettingsResponse>) -> Unit): Cancelable {
        val cancelable = object: Cancelable() {
            override fun onCancelled() {}
        }

        val builder = LocationSettingsRequest.Builder()
            .setAlwaysShow(true)
            .addLocationRequest(getLocationRequest())

        val client = LocationServices.getSettingsClient(appContext)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            if(!cancelable.isCancelled) {
                callback(Result.success(it))
            }
        }

        task.addOnFailureListener {
            if(!cancelable.isCancelled) {
                callback(Result.failure(it))
            }
        }

        return cancelable
    }
}