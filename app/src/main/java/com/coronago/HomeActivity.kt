package com.coronago

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.coronago.geospatial.MovementManager
import com.coronago.geospatial.MovementService
import com.coronago.rewards.ChallengeStatus
import com.coronago.rewards.RewardsManager
import com.coronago.setup.UserSetup
import com.coronago.utils.beGone
import com.coronago.utils.beVisible
import com.google.android.gms.common.api.ResolvableApiException
import kotlinx.android.synthetic.main.activity_home.*

private const val REQUEST_CODE_CHANGE_LOCATION_SETTINGS = 1

class HomeActivity: AppCompatActivity(), UserSetup.Callback {

    lateinit var userSetup: UserSetup
    lateinit var movementManager: MovementManager
    lateinit var rewardsManager: RewardsManager

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

    override fun onSetupComplete() {
        loadingBackground.beGone()
        activeBackground.beVisible()
        movementManager.start()
        MovementService.startService(this)
    }

    override fun onStart() {
        super.onStart()
        pointsText.text = rewardsManager.getPoints().toString()

        rewardsManager.firstChallengeStatusCallback = { firstChallengeStatus ->
            when(firstChallengeStatus) {
                ChallengeStatus.NONE -> { /* Do nothing */ }
                ChallengeStatus.PASSED -> {
                    AlertActivity.start(this, Alert(
                        R.drawable.il_success,
                        R.string.alertTitleFirstChallengePassed,
                        R.string.alertSubtitleFirstChallengePassed,
                        R.raw.sound_success,
                        R.string.alertActionContinue,
                        QuizActivity.getIntent(this)
                    ))
                    finish()
                }
                ChallengeStatus.FAILURE -> {
                    AlertActivity.start(this, Alert(
                        R.drawable.il_failure,
                        R.string.alertTitleFirstChallengeFailed,
                        R.string.alertSubtitleFirstChallengeFailed,
                        R.raw.sound_failure,
                        R.string.alertActionContinue,
                        getIntent(this)
                    ))
                    finish()
                }
            }
        }
        rewardsManager.pointsUpdateCallback = { newPoints ->
            pointsText.text = newPoints.toString()
        }
    }

    override fun onStop() {
        super.onStop()
        rewardsManager.firstChallengeStatusCallback = null
        rewardsManager.pointsUpdateCallback = null
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
            context.startActivity(QuizActivity.getIntent(context))
        }

        fun getIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}
