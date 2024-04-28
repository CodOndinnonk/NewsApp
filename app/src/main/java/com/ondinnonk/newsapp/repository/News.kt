package com.ondinnonk.newsapp.repository

import com.ondinnonk.newsapp.repository.local.db.RoomDb
import com.ondinnonk.newsapp.repository.remote.newsapi.NewsRemoteModel

data class News(val dbId: Long = RoomDb.DEFAULT_ID_VALUE, val title: String) {

    companion object {

        fun create(model: NewsRemoteModel): News {
            return News(dbId = model.id, title = model.title)//TODO TEST
        }

    }

}