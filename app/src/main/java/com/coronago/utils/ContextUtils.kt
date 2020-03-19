package com.coronago.utils

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val REQUEST_CODE_REQUEST_LOCATION_PERMISSION = 1

fun Context.hasLocationPermission(): Boolean {
    return if(AndroidVersion.isAtLeastTen()) {
        isPermissionGranted(ACCESS_FINE_LOCATION) && isPermissionGranted(ACCESS_BACKGROUND_LOCATION)
    } else {
        isPermissionGranted(ACCESS_FINE_LOCATION)
    }
}

fun Context.isPermissionGranted(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED

fun Activity.requestLocationPermission() {
    val permissions = if(AndroidVersion.isAtLeastTen())
        arrayOf(ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION)
    else
        arrayOf(ACCESS_FINE_LOCATION)

    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_REQUEST_LOCATION_PERMISSION)
}