package com.coronago.geospatial

import android.content.Context
import android.location.Location
import android.os.Handler
import android.os.Looper
import com.coronago.rewards.RewardsManager
import com.google.android.gms.location.FusedLocationProviderClient
import timber.log.Timber

private const val PERIOD_LENGTH_MILLIS = 1_000L * 60 * 30
private const val MAX_ALLOWED_TOTAL_DISTANCE_METERS = 50.0

// Because it manages
class MovementManager(
    private val appContext: Context,
    private val handler: Handler,
    private val mainLooper: Looper,
    private val locationClient: FusedLocationProviderClient,
    private val locationStore: LocationStore,
    private val rewardsManager: RewardsManager
) {

    private var isStarted = false
    private var lastLocation: Location? = null
    private val trackSink = mutableListOf<Track>()

    private val locationCallback =  object: AccurateLocationCallback() {

        override fun onRealLocationReceived(location: Location) {
            lastLocation?.let { trackSink.add(Track.create(it, location)) }
            lastLocation = location
        }

        override fun onFakeLocationReceived(location: Location) {
            Timber.w("Cheaters get stitches!")
            cancel()
        }
    }

    private val periodRunnable = object: Runnable {

        override fun run() {
            val totalDistance = trackSink.sumByDouble { it.distance.toDouble() }
            if(totalDistance <= MAX_ALLOWED_TOTAL_DISTANCE_METERS) {
                rewardsManager.onFirstChallengeCompleted()
            } else {
                rewardsManager.onFirstChallengeFailed()
            }

            locationStore.insert(trackSink)
            trackSink.clear()
            handler.postDelayed(this, PERIOD_LENGTH_MILLIS)
        }
    }

    // Start/Stop should be idempotent

    fun start() {
        Timber.i("Starting your manager")
        if(!isStarted) {
            isStarted = true
            locationClient.requestLocationUpdates(
                getLocationRequest(),
                locationCallback,
                mainLooper
            )
            handler.postDelayed(periodRunnable, PERIOD_LENGTH_MILLIS)
        }
    }

    fun stop() {
        Timber.i("Stopping your manager")
        if(isStarted) {
            cancel()
        }
    }

    private fun cancel() {
        isStarted = false
        locationClient.removeLocationUpdates(locationCallback)
        handler.removeCallbacks(periodRunnable)
    }
}