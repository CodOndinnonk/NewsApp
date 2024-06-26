package com.ondinnonk.newsapp

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.ondinnonk.newsapp.di.repositoryModule
import com.ondinnonk.newsapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    companion object {
        const val LOG_TAG = "NewsAppTag"
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                )
            )
        }
    }

}

fun Context.isLandscape(): Boolean {
    val orientation = resources.configuration.orientation
    return orientation == Configuration.ORIENTATION_LANDSCAPE
}