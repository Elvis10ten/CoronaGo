package com.coronago

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coronago.user.UserStore

class HomeActivity: AppCompatActivity(), UserStore.Callback {

    lateinit var userStore: UserStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.inject(this)
        setContentView(R.layout.activity_home)
        userStore.checkState(this)
    }

    override fun onOnboardingRequired() {
        OnboardingActivity.start(this)
        finish()
    }

    override fun onSignInRequired() {
        SignInActivity.start(this)
        finish()
    }

    override fun onSignedIn() {
        // Initialize home
    }

    companion object {

        fun start(context: Context) {
            Intent(context, HomeActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}
