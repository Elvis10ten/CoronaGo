package com.coronago

import android.content.Context
import com.coronago.setup.AppInitializer
import com.coronago.setup.UserSetup
import com.google.gson.Gson

object Injector {

    private lateinit var appContext: Context

    private val gson by lazy { Gson() }

    private val appInitializer by lazy { AppInitializer(appContext) }

    private val userStore by lazy { UserSetup(appContext) }

    fun init(context: Context) {
        appContext = context
    }

    fun inject(app: CoronaGoApp) {
        app.appInitializer = appInitializer
    }

    fun inject(homeActivity: HomeActivity) {
        homeActivity.userSetup = userStore
    }

    fun inject(onboardingActivity: OnboardingActivity) {
        onboardingActivity.userSetup = userStore
    }
}