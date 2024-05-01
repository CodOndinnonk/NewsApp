package com.ondinnonk.newsapp.repository.local

import com.ondinnonk.newsapp.repository.News
import com.ondinnonk.newsapp.repository.local.db.NewsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryLocal(private val dao: NewsDao) {

    suspend fun getAllNews(): Flow<List<News>> {
        return dao.getAll().map { it.map { News.create(it) } }
    }

    suspend fun saveNews(news: News): Long {
        return dao.insert(NewsEntity.create(news))
    }

}