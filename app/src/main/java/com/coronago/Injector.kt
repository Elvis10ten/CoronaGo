package com.coronago

import android.content.Context
import com.coronago.user.UserStore
import com.google.gson.Gson

object Injector {

    private lateinit var appContext: Context

    private val gson by lazy { Gson() }

    private val userStore by lazy { UserStore(appContext, gson) }

    fun init(context: Context) {
        appContext = context
    }

    fun inject(homeActivity: HomeActivity) {
        homeActivity.userStore = userStore
    }

    fun inject(onboardingActivity: OnboardingActivity) {
        onboardingActivity.userStore = userStore
    }
}