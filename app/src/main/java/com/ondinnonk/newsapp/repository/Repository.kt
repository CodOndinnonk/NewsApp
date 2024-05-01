package com.ondinnonk.newsapp.repository

import com.ondinnonk.newsapp.utils.Coroutineble
import kotlinx.coroutines.flow.Flow

interface Repository : Coroutineble {

    suspend fun getNews(): Flow<List<News>>
    suspend fun refreshNews(): Result<Unit>

}