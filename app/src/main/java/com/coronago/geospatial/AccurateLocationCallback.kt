package com.coronago.geospatial

import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import timber.log.Timber

private const val MAX_REQUIRED_ACCURACY_METERS = 10F

abstract class AccurateLocationCallback: LocationCallback() {

    override fun onLocationResult(locationResult: LocationResult?) {
        val newLocation = locationResult?.locations?.lastOrNull() ?: return
        Timber.d("New location received: %s", newLocation)

        if (newLocation.accuracy >  MAX_REQUIRED_ACCURACY_METERS) {
            Timber.i("Ignoring location with weak accuracy")
            return
        }

        if(newLocation.isFromMockProvider) {
            onFakeLocationReceived(newLocation)
        } else {
            onRealLocationReceived(newLocation)
        }
    }

    abstract fun onRealLocationReceived(location: Location)

    abstract fun onFakeLocationReceived(location: Location)
}