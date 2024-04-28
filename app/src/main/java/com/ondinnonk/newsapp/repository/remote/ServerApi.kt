package com.ondinnonk.newsapp.repository.remote

import com.ondinnonk.newsapp.repository.remote.ServerRepository.Companion.API_KEY
import com.ondinnonk.newsapp.repository.remote.newsapi.ResponseContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("top-headlines")
    suspend fun getLatestNews(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<ResponseContainer>

}