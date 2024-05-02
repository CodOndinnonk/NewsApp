package com.ondinnonk.newsapp.features.list

import android.util.Log
import com.ondinnonk.newsapp.NewsApplication.Companion.LOG_TAG
import com.ondinnonk.newsapp.core.BaseViewModel
import com.ondinnonk.newsapp.features.NewsUiModel
import com.ondinnonk.newsapp.repository.News
import com.ondinnonk.newsapp.repository.Repository
import com.ondinnonk.newsapp.repository.RepositoryImpl
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NewsListViewModel(private val repository: Repository) : BaseViewModel() {

    private val onUpdateListEmit = MutableStateFlow<List<NewsUiModel>>(listOf())
    val onUpdateList: Flow<List<NewsUiModel>> = onUpdateListEmit
    private val onRefreshLoadFinishEmit =
        MutableSharedFlow<Exception?>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val onRefreshLoadFinish: Flow<Exception?> = onRefreshLoadFinishEmit
    private val onOpenDetailsEmit = MutableSharedFlow<News>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val onOpenDetails: Flow<News> = onOpenDetailsEmit

    init {
        launch {
            repository.getNews().map { it.map { NewsUiModel.create(it) } }.also { onUpdateListEmit.emitAll(it) }
        }
        requestFetchNews()
    }

    fun onRefreshList() {
        requestFetchNews()
    }

    fun onOpenDetails(model: NewsUiModel) {
        launch {
            repository.getNews().first().firstOrNull { it.uid == model.uid }?.let {
                onOpenDetailsEmit.tryEmit(it)
            } ?: run { Log.e(LOG_TAG, "Failed to open news details. Can't find item. Model = $model") }
        }
    }

    private fun requestFetchNews() = launch {
        repository.refreshNews()
            .onFailure {
                when (it) {
                    is RepositoryImpl.ToEarlyRefreshException -> {
                        val msg = "To early for fetch"
                        Log.i(LOG_TAG, msg)
                        onRefreshLoadFinishEmit.tryEmit(java.lang.Exception(msg))
                        //also make sense later to show this info to user
                    }
                    else -> {
                        val msg = "Failed to get latest news"
                        Log.e(LOG_TAG, msg, it)
                        onRefreshLoadFinishEmit.tryEmit(java.lang.Exception(msg))
                    }
                }
            }
    }

}