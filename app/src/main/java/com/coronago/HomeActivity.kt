package com.coronago

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import com.coronago.setup.UserSetup
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import timber.log.Timber

private const val REQUEST_CODE_CHANGE_LOCATION_SETTINGS = 1

class HomeActivity: AppCompatActivity(), UserSetup.Callback {

    lateinit var userSetup: UserSetup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.inject(this)
        setContentView(R.layout.activity_home)
        userSetup.checkSetup(this)
    }

    override fun onOnboardingRequired() {
        OnboardingActivity.start(this)
        finish()
    }

    override fun onLocationPermissionRequired() {
        PermissionActivity.start(this)
        finish()
    }

    override fun onLocationSettingsCheckRequired(exception: ResolvableApiException) {
        exception.startResolutionForResult(this, REQUEST_CODE_CHANGE_LOCATION_SETTINGS)
    }

    override fun onLocationSettingsCheckFailed() {
        Toast.makeText(this, R.string.homeLocationSettingsCheckFailed, Toast.LENGTH_LONG).show()
        // TODO: What do we do do do, doo doo, doooo, dooooooo, sing along with me.
    }

    var broadcastReceiver: BroadcastReceiver? = null
    var pendingIntent: PendingIntent? = null
    var locationCallback: LocationCallback? = null

    override fun onSetupComplete() {
        val transitions = mutableListOf<ActivityTransition>()

        transitions += ActivityTransition.Builder()
            .setActivityType(DetectedActivity.STILL)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
            .build()

        transitions += ActivityTransition.Builder()
            .setActivityType(DetectedActivity.STILL)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
            .build()

        transitions += ActivityTransition.Builder()
            .setActivityType(DetectedActivity.WALKING)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
            .build()

        transitions += ActivityTransition.Builder()
            .setActivityType(DetectedActivity.WALKING)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
            .build()

        val request = ActivityTransitionRequest(transitions)

        pendingIntent = PendingIntent.getBroadcast(this, 2, Intent(packageName + ".LOVE"), 0)
        broadcastReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Timber.d("Onre: " + intent)
                if (ActivityTransitionResult.hasResult(intent)) {
                    val result = ActivityTransitionResult.extractResult(intent)!!
                    for (event in result.transitionEvents) {
                        Timber.e("Event: $event")
                    }
                }
            }
        }
        registerReceiver(broadcastReceiver, IntentFilter(packageName + ".LOVE"))

        val task = ActivityRecognition.getClient(this)
            .requestActivityTransitionUpdates(request, pendingIntent)

        task.addOnSuccessListener {
            Timber.d("Started")
            // Handle success
        }

        task.addOnFailureListener { e: Exception ->
            Timber.e(e, "Error")
            // Handle error
        }

        var lastLocation: Location? = null
        locationCallback =  object: LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                Timber.d("Size: " + locationResult.locations.size)
                if(locationResult.locations.isEmpty()) return
                val location = locationResult.locations.first() ?: return

                lastLocation?.let {
                    Timber.d("Distance: " + location.distanceTo(it))
                }
                lastLocation = location
                Timber.d("Location: " + location.accuracy)
            }
        }

        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(
            getLocationRequest(), locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 2_000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            smallestDisplacement = 10f
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
        pendingIntent?.let {
            ActivityRecognition.getClient(this).removeActivityTransitionUpdates(it)
        }

        locationCallback?.let {
            LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE_CHANGE_LOCATION_SETTINGS) {
            userSetup.checkSetup(this)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {

        fun start(context: Context) {
            Intent(context, HomeActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}
