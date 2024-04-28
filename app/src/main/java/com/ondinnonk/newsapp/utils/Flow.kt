package com.ondinnonk.newsapp.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.observe(lifecycleOwner: LifecycleOwner, action: (T) -> Unit): LifecycleObserver {
    var job: Job? = null
    return LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                job = source.lifecycleScope.launch {
                    this@observe.collect { action(it) }
                }
            }
            Lifecycle.Event.ON_STOP -> {
                job?.cancel()
                job = null
            }
            else -> Unit
        }
    }.also(lifecycleOwner.lifecycle::addObserver)
}