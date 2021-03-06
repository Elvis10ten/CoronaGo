package com.coronago

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.coronago.setup.UserSetup
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {

    lateinit var userSetup: UserSetup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.inject(this)
        setContentView(R.layout.activity_onboarding)
        whySocialDistancingTextView.movementMethod = LinkMovementMethod.getInstance()
        getStartedButton.setOnClickListener {
            userSetup.justOnboarded()
            HomeActivity.start(this)
            finish()
        }
    }

    companion object {

        fun start(context: Context) {
            Intent(context, OnboardingActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}
