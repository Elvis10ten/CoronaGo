package com.coronago.geospatial

import com.google.android.gms.location.LocationRequest

private const val LOCATION_RATE_INTERVALS_MILLIS = 1_000L
private const val SMALLEST_DISPLACEMENT_METERS = 10F

internal fun getLocationRequest(): LocationRequest {
    return LocationRequest.create().apply {
        interval = LOCATION_RATE_INTERVALS_MILLIS
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        smallestDisplacement = SMALLEST_DISPLACEMENT_METERS
    }
}