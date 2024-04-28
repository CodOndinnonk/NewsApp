package com.ondinnonk.newsapp.core

import androidx.lifecycle.ViewModel
import com.ondinnonk.newsapp.utils.Coroutineble
import org.koin.core.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent, Coroutineble {
}