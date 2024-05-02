package com.ondinnonk.newsapp.repository

import android.os.Parcelable
import com.ondinnonk.newsapp.repository.local.NewsEntity
import com.ondinnonk.newsapp.repository.local.db.RoomDb
import com.ondinnonk.newsapp.repository.remote.newsapi.NewsRemoteModel
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class News(
    val dbId: Long = RoomDb.DEFAULT_ID_VALUE,
    val uid: String,
    val title: String,
    val author: String,
    val image: String?,
    val date: Date,
    val content: String,
) : Parcelable {

    companion object {

        fun create(model: NewsEntity): News {
            return News(
                uid = model.uid,
                title = model.title,
                author = model.author,
                image = model.image,
                date = model.date,
                content = model.content
            )
        }

        fun create(model: NewsRemoteModel): Result<News> = runCatching {
            val baseErrorPrefix = "Failed to create ${News::class.simpleName}."
            if (model.title.isNullOrEmpty()) throw Exception("$baseErrorPrefix Title is invalid = ${model.title}. Model = $model")
            if (model.content.isNullOrEmpty()) throw Exception("$baseErrorPrefix Content is invalid = ${model.content}. Model = $model")

            //we expect that all fields are required and we don't fill them with blank values. Can be changed later if requested
            return Result.success(
                News(
                    uid = buildUid(model).getOrThrow(),
                    title = model.title,
                    author = model.author ?: "",
                    image = model.image,
                    date = model.getNewsDate().getOrThrow(),
                    content = model.content
                )
            )
        }

        /**
         * @return unique key for record, since API has no such
         */
        private fun buildUid(model: NewsRemoteModel): Result<String> {
            //some example of generating unique key. Expected to update later
            val out = model.image + model.date + model.title + model.source?.id

            //TODO (ADD) cen be collisions if we have some fields with empty or invalid data. (now all invalid models will be skipped)
            if (out.isBlank()) {
                return Result.failure(Exception("Failed to create UID for news = $this"))
            }

            return Result.success(out)
        }

    }

}