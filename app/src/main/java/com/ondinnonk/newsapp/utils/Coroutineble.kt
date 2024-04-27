package com.ondinnonk.newsapp.utils

import android.util.Log
import com.ondinnonk.newsapp.NewsApplication.Companion.LOG_TAG
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

interface Coroutineble {

    private val commonExceptionHandler: CoroutineExceptionHandler
        get() = CoroutineExceptionHandler { _, throwable ->
            GlobalScope.launch {
                Log.e(LOG_TAG, "Uncaught exception", throwable)
            }
        }

    private val scope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Default + SupervisorJob() + commonExceptionHandler)

    fun launch(dispatcher: CoroutineDispatcher = Dispatchers.Default, action: suspend () -> Unit): Job {
        return scope.launch(dispatcher) { action() }
    }

    fun cancelScope() {
        scope.cancel()
    }

}