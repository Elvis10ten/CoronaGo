package com.coronago.geospatial

import android.content.Context
import android.location.Location
import android.os.Handler
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import timber.log.Timber

private const val PERIOD_LENGTH_MILLIS = 1_000L * 60 * 30

// Because it manages
class MovementManager(
    private val appContext: Context,
    private val handler: Handler,
    private val locationClient: FusedLocationProviderClient
) {

    private var lastLocation: Location? = null
    private val trackSink = mutableListOf<Track>()

    private val locationCallback =  object: AccurateLocationCallback() {

        override fun onRealLocationReceived(location: Location) {
            lastLocation?.let { trackSink.add(Track.create(it, location)) }
            lastLocation = location
        }

        override fun onFakeLocationReceived(location: Location) {
            Timber.w("Cheaters get stitches!")
            handler.removeCallbacks(periodRunnable)
        }
    }

    private val periodRunnable = object: Runnable {

        override fun run() {
            handler.postDelayed(this, PERIOD_LENGTH_MILLIS)
        }
    }

    fun start() {
        Timber.i("Starting your manager")
        handler.postDelayed(periodRunnable, PERIOD_LENGTH_MILLIS)
    }

    fun stop() {
        Timber.i("Stopping your manager")
        handler.removeCallbacks(periodRunnable)
    }
}