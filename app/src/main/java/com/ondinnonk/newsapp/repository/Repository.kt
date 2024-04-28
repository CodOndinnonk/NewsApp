package com.ondinnonk.newsapp.repository

interface Repository {

    suspend fun getNews(): Result<List<News>>
}