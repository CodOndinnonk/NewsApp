package com.ondinnonk.newsapp.repository.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ondinnonk.newsapp.repository.News
import com.ondinnonk.newsapp.repository.local.NewsEntity.Companion.TABLE_NAME
import com.ondinnonk.newsapp.repository.local.db.RoomDb
import java.util.Date


@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = [NewsEntity.COLUMN_ID], unique = true), Index(value = [NewsEntity.COLUMN_UID], unique = true)]
)
data class NewsEntity(
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey(autoGenerate = true)
    val id: Long = RoomDb.DEFAULT_ID_VALUE,
    @ColumnInfo(name = COLUMN_UID)
    val uid: String,
    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = COLUMN_AUTHOR)
    val author: String,
    @ColumnInfo(name = COLUMN_IMAGE)
    val image: String?,
    @ColumnInfo(name = COLUMN_DATE)
    val date: Date,
    @ColumnInfo(name = COLUMN_CONTENT)
    val content: String,
) {
    companion object {
        const val TABLE_NAME = "News"

        const val COLUMN_ID = "Id"
        const val COLUMN_UID = "Uid"
        const val COLUMN_TITLE = "Title"
        const val COLUMN_AUTHOR = "Author"
        const val COLUMN_IMAGE = "Image"
        const val COLUMN_DATE = "Date"
        const val COLUMN_CONTENT = "Content"

        fun create(news: News): NewsEntity {
            return NewsEntity(
                id = news.dbId,
                uid = news.uid,
                title = news.title,
                author = news.author,
                image = news.image,
                date = news.date,
                content = news.content,
            )
        }

    }

}