package com.coronago.geospatial

import android.location.Location

data class Position(
    val latitude: Double,
    val longitude: Double,
    val timeUtc: Long
)

fun Location.toPosition() = Position(latitude, longitude, time)