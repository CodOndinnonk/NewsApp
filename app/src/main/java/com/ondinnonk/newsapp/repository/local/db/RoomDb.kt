package com.ondinnonk.newsapp.repository.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ondinnonk.newsapp.repository.local.NewsEntity
import com.ondinnonk.newsapp.repository.local.db.RoomDb.Companion.DB_VERSION

@Database(
    entities = [
        NewsEntity::class,
    ], version = DB_VERSION, exportSchema = false
)

abstract class RoomDb : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "news_room_db"

        const val DEFAULT_ID_VALUE = 0L
    }
}