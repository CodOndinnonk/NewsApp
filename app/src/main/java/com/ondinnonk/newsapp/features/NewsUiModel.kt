package com.ondinnonk.newsapp.features

import com.ondinnonk.newsapp.repository.News

data class NewsUiModel(val title: String) {

    companion object {

        fun create(model: News): NewsUiModel {
            return NewsUiModel(title = model.title)
        }
    }

}