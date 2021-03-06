package com.coronago

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.coronago.geospatial.LocationSettingsChecker
import com.coronago.geospatial.LocationStore
import com.coronago.geospatial.MovementManager
import com.coronago.geospatial.MovementService
import com.coronago.quiz.QuizProvider
import com.coronago.rewards.RewardsManager
import com.coronago.setup.AppInitializer
import com.coronago.setup.UserSetup
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson

object Injector {

    private lateinit var appContext: Context

    private val handler by lazy { Handler() }

    private val gson by lazy { Gson() }

    private val mainLooper by lazy { Looper.getMainLooper() }

    private val appInitializer by lazy { AppInitializer(appContext) }

    private val sharedPreferences by lazy { appContext.getSharedPreferences("primary", Context.MODE_PRIVATE) }

    private val locationSettingsChecker by lazy { LocationSettingsChecker(appContext) }

    private val userSetup by lazy { UserSetup(appContext, sharedPreferences, locationSettingsChecker) }

    private val locationStore by lazy { LocationStore(sharedPreferences) }

    private val quizProvider by lazy { QuizProvider(sharedPreferences) }

    val movementManager by lazy {
        MovementManager(
            appContext,
            handler,
            mainLooper,
            LocationServices.getFusedLocationProviderClient(appContext),
            locationStore,
            rewardsManager
        )
    }

    val rewardsManager by lazy {
        RewardsManager(
            appContext,
            sharedPreferences
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
        homeActivity.movementManager = movementManager
        homeActivity.rewardsManager = rewardsManager
    }

    fun inject(quizActivity: QuizActivity) {
        quizActivity.userSetup = userSetup
        quizActivity.movementManager = movementManager
        quizActivity.rewardsManager = rewardsManager
        quizActivity.quizProvider = quizProvider
    }

    fun inject(onboardingActivity: OnboardingActivity) {
        onboardingActivity.userSetup = userSetup
    }

    fun inject(movementService: MovementService) {

    }
}