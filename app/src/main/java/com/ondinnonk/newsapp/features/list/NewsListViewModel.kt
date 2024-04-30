package com.ondinnonk.newsapp.features.list

import android.util.Log
import com.ondinnonk.newsapp.core.BaseViewModel
import com.ondinnonk.newsapp.features.NewsUiModel
import com.ondinnonk.newsapp.repository.News
import com.ondinnonk.newsapp.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewsListViewModel(private val repository: Repository) : BaseViewModel() {

    private val onUpdateListEmit = MutableStateFlow<List<NewsUiModel>>(listOf())
    val onUpdateList: StateFlow<List<NewsUiModel>> = onUpdateListEmit
    private var news: List<News> = mutableListOf()
        private set(value) {
            field = value
            onUpdateListEmit.tryEmit(value.map { NewsUiModel.create(it).getOrThrow() })

        }

    init {
        launch {
            repository.getNews()
                .onSuccess { news = it }
                .onFailure {
                    //TODO TEST Add

                    Log.d("TEST_TAG", "NewsListViewModel: ")
                }
        }
    }


    fun onOpenDetails(model: NewsUiModel) {//TODO TEST

    }

}