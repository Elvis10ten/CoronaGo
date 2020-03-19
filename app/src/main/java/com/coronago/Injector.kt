package com.coronago

import android.content.Context
import android.os.Handler
import com.coronago.geospatial.LocationSettingsChecker
import com.coronago.geospatial.LocationStore
import com.coronago.geospatial.MovementManager
import com.coronago.setup.AppInitializer
import com.coronago.setup.UserSetup
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson

object Injector {

    private lateinit var appContext: Context

    private val handler by lazy { Handler() }

    private val gson by lazy { Gson() }

    private val appInitializer by lazy { AppInitializer(appContext) }

    private val sharedPreferences by lazy { appContext.getSharedPreferences("primary", Context.MODE_PRIVATE) }

    private val locationSettingsChecker by lazy { LocationSettingsChecker(appContext) }

    private val userSetup by lazy { UserSetup(appContext, sharedPreferences, locationSettingsChecker) }

    private val locationStore by lazy { LocationStore(sharedPreferences) }

    val movementManager by lazy {
        MovementManager(
            appContext,
            handler,
            LocationServices.getFusedLocationProviderClient(appContext),
            locationStore
        )
    }

    fun init(context: Context) {
        appContext = context
    }

    fun inject(app: CoronaGoApp) {
        app.appInitializer = appInitializer
    }

    fun inject(homeActivity: HomeActivity) {
        homeActivity.userSetup = userSetup
    }

    fun inject(onboardingActivity: OnboardingActivity) {
        onboardingActivity.userSetup = userSetup
    }
}