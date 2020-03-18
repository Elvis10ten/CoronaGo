package com.coronago.utils

import androidx.annotation.MainThread

abstract class Cancelable {

    var isCancelled: Boolean = false
        private set

    @MainThread
    fun cancel() {
        isCancelled = true
        onCancelled()
    }

    abstract fun onCancelled()
}