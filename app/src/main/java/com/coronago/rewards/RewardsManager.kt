package com.coronago.rewards

import android.content.Context
import android.content.SharedPreferences
import com.coronago.geospatial.MovementService

private const val KEY_POINTS = "KEY_POINTS"
private const val TOP_UP_POINTS_AMOUNT = 10
private const val DEDUCT_POINTS_AMOUNT = 5

// Hey look, another another manager
class RewardsManager(
    private val appContext: Context,
    private val sp: SharedPreferences
) {

    private var firstChallengeStatus = ChallengeStatus.NONE

    var pointsUpdateCallback: ((Int) -> Unit)? = null
    var firstChallengeStatusCallback: ((ChallengeStatus) -> Unit)? = null
        set(value) {
            field = value
            notifyFirstChallengeCallback()
        }

    fun onFirstChallengePassed() {
        firstChallengeStatus = ChallengeStatus.PASSED
        MovementService.notifyFirstChallengePassed(appContext)
        notifyFirstChallengeCallback()
    }

    fun onFirstChallengeFailed() {
        firstChallengeStatus = ChallengeStatus.FAILURE
        MovementService.notifyFirstChallengeFailure(appContext)
        notifyFirstChallengeCallback()
    }

    private fun notifyFirstChallengeCallback() {
        firstChallengeStatusCallback?.invoke(firstChallengeStatus)
    }

    fun onSecondChallengeStarted() {
        firstChallengeStatus = ChallengeStatus.NONE
    }

    fun onSecondChallengePassed() {
        topUpPoints()
    }

    fun onSecondChallengeFailed() {
        deductPoints()
    }

    private fun topUpPoints() {
        val currentPoint = getPoints()
        sp.edit()
            .putInt(KEY_POINTS, currentPoint + TOP_UP_POINTS_AMOUNT)
            .apply()
        pointsUpdateCallback?.invoke(getPoints())
    }

    private fun deductPoints() {
        val currentPoint = getPoints()
        var newPoint = currentPoint - DEDUCT_POINTS_AMOUNT
        if(newPoint < 0) {
            newPoint = 0
        }

        sp.edit()
            .putInt(KEY_POINTS, newPoint)
            .apply()
        pointsUpdateCallback?.invoke(getPoints())
    }

    fun getPoints(): Int {
        return sp.getInt(KEY_POINTS, 0)
    }
}