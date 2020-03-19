package com.coronago.rewards

import android.content.Context
import android.content.SharedPreferences

private const val KEY_POINTS = "KEY_POINTS"
private const val TOP_UP_POINTS_AMOUNT = 10

// Here, another manager
class RewardsManager(
    private val appContext: Context,
    private val sp: SharedPreferences
) {

    fun onFirstChallengeCompleted() {

    }

    fun onFirstChallengeFailed() {

    }

    fun onSecondChallengeCompleted() {
        topUpPoints()
    }

    fun onSecondChallengeFailed() {

    }

    private fun topUpPoints() {
        val currentPoint = getPoints()
        sp.edit()
            .putInt(KEY_POINTS, currentPoint + TOP_UP_POINTS_AMOUNT)
            .apply()
    }

    fun getPoints(): Int {
        return sp.getInt(KEY_POINTS, 0)
    }
}