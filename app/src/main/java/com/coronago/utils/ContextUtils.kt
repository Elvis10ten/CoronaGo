package com.coronago.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val REQUEST_CODE_REQUEST_LOCATION_PERMISSION = 1

fun Context.hasLocationPermission(): Boolean {
    return if(AndroidVersion.isAtLeastTen()) {
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}

fun Activity.requestLocationPermission() {
    val permissions = if(AndroidVersion.isAtLeastTen())
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    else
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_REQUEST_LOCATION_PERMISSION)
}