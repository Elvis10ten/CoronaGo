package com.coronago

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    companion object {

        fun start(context: Context) {
            Intent(context, SignInActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}
