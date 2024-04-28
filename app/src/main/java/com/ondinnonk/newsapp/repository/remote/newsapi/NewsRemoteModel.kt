package com.ondinnonk.newsapp.repository.remote.newsapi

import com.google.gson.annotations.SerializedName

data class NewsRemoteModel(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("title")
    val title: String,
)