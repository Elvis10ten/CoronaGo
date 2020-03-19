package com.coronago

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.coronago.geospatial.MovementService
import com.coronago.setup.UserSetup
import com.google.android.gms.common.api.ResolvableApiException

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

    override fun onSetupComplete() {
        MovementService.startService(this)
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

        fun getIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}
