package com.coronago.geospatial

import android.location.Location

data class Track(
    val position1: Position,
    val position2: Position,
    val distance: Float
) {

    companion object {

        fun create(location1: Location, location2: Location): Track {
            return Track(
                location1.toPosition(),
                location2.toPosition(),
                location1.distanceTo(location2)
            )
        }
    }
}