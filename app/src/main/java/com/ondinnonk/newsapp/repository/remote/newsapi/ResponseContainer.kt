package com.ondinnonk.newsapp.repository.remote.newsapi

import com.google.gson.annotations.SerializedName

data class ResponseContainer(
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("totalResults")
    val totalResults: Long,
    @field:SerializedName("articles")
    val articles: List<NewsRemoteModel>,
) {

    fun getContent(): Result<List<NewsRemoteModel>> {
        if (articles.isEmpty()) {
            return Result.failure(Exception("Failed to get news. Status = $status"))
        }

        return Result.success(articles)
    }

}