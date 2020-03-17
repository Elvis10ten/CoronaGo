package com.coronago.utils

import android.os.Build

object AndroidVersion {

    @JvmStatic
    fun isAtLeastMarshmallow() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @JvmStatic
    fun isAtLeastNougat() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    @JvmStatic
    fun isAtLeastOreo() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    @JvmStatic
    fun isAtLeastPie() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    @JvmStatic
    fun isAtLeastTen() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}
