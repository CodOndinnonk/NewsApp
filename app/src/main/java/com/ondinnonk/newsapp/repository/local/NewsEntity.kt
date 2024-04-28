package com.ondinnonk.newsapp.repository.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ondinnonk.newsapp.repository.News
import com.ondinnonk.newsapp.repository.local.NewsEntity.Companion.TABLE_NAME
import com.ondinnonk.newsapp.repository.local.db.RoomDb


@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = [NewsEntity.COLUMN_ID], unique = true)]
)
data class NewsEntity(
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey(autoGenerate = true)
    val id: Long = RoomDb.DEFAULT_ID_VALUE,
    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,
) {
    companion object {
        const val TABLE_NAME = "News"

        const val COLUMN_ID = "Id"
        const val COLUMN_TITLE = "Title"

        fun create(news: News): NewsEntity {
            return NewsEntity(
                id = news.dbId,
                title = news.title,
            )
        }

    }

}