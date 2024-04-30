package com.ondinnonk.newsapp.repository.remote.newsapi

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NewsRemoteModel(
    @field:SerializedName("source")
    val source: Source?,
    @field:SerializedName("title")
    val title: String?,
    @field:SerializedName("author")
    val author: String?,
    @field:SerializedName("urlToImage")
    val image: String?,
    @field:SerializedName("publishedAt")
    val date: String?,
    @field:SerializedName("content")
    val content: String?,
) {

    companion object {
        private val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }

    fun getNewsDate(): Result<Date> {
        return runCatching {
            if (date.isNullOrBlank()) {
                return Result.failure(Exception("Date parameter is invalid. Value = $date"))
            }

            val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            sdf.parse(date)?.let {
                return Result.success(it)
            } ?: run {
                return Result.failure(Exception("Failed to format date sting. Str = $date"))
            }
        }
    }

    data class Source(
        @field:SerializedName("id")
        val id: String?,
        @field:SerializedName("name")
        val name: String?,
    )
}