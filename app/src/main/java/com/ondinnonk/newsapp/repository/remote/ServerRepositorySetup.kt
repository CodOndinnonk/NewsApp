package com.ondinnonk.newsapp.repository.remote

import android.content.Context
import com.ondinnonk.newsapp.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServerRepositorySetup(private val context: Context) {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "e35a73966dab46c8977be78f40ad7854"
    }

    private var retrofit: Retrofit
    private var httpClient: OkHttpClient

    init {
        httpClient = buildOkHttpClient()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpClient)
            .build()
    }

    fun create(): ServerApi = retrofit.create(ServerApi::class.java)

    private fun buildOkHttpClient(): OkHttpClient {
        // cache for data(1MB)
        val cacheSize = 10 * 1024
        val cache = Cache(context.cacheDir, cacheSize.toLong())

        val okHttpClient = OkHttpClient.Builder()

        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(logging)
        }

        return okHttpClient.cache(cache).build()
    }

}