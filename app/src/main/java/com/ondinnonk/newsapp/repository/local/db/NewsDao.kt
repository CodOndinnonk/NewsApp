package com.ondinnonk.newsapp.repository.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ondinnonk.newsapp.repository.local.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao : BaseDao<NewsEntity> {

    @Transaction
    @Query("SELECT * FROM ${NewsEntity.TABLE_NAME} ")
    fun getAll(): Flow<List<NewsEntity>>

}