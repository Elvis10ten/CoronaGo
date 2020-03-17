package com.coronago.user

import android.content.Context
import com.google.gson.Gson

private const val KEY_HAS_ONBOARDED = "KEY_HAS_ONBOARDED"
private const val KEY_USER_MODEL = "KEY_USER_MODEL"

class UserStore(
    private val appContext: Context,
    private val gson: Gson
) {

    private val sp = appContext.getSharedPreferences("USER_STORE", Context.MODE_PRIVATE)
    private var signedInUserModel: UserModel? = null

    fun checkState(callback: Callback) {
        if(!sp.getBoolean(KEY_HAS_ONBOARDED, false)) {
            callback.onOnboardingRequired()
        } else if(getUserModel() == null) {
            callback.onSignInRequired()
        } else {
            callback.onSignedIn()
        }
    }

    fun justOnboarded() {
        sp.edit().putBoolean(KEY_HAS_ONBOARDED, true).apply()
    }

    fun justSignedIn(userModel: UserModel) {
        sp.edit().putString(KEY_USER_MODEL, gson.toJson(userModel)).apply()
    }

    fun getUserModel(): UserModel? {
        if(signedInUserModel != null) return signedInUserModel
        signedInUserModel = gson.fromJson(sp.getString(KEY_USER_MODEL, null), UserModel::class.java)
        return signedInUserModel
    }

    interface Callback {

        fun onOnboardingRequired()

        fun onSignInRequired()

        fun onSignedIn()
    }
}