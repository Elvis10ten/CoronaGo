package com.coronago.geospatial

import android.content.SharedPreferences

class LocationStore(
    private val sp: SharedPreferences
) {

    fun insert(tracks: List<Track>) {
        // TODO: Do we need to save locations? Except we want to trace contacts at some point.
    }
}