package com.ondinnonk.newsapp.features.list

import android.util.Log
import com.ondinnonk.newsapp.NewsApplication.Companion.LOG_TAG
import com.ondinnonk.newsapp.core.BaseViewModel
import com.ondinnonk.newsapp.features.NewsUiModel
import com.ondinnonk.newsapp.repository.Repository
import com.ondinnonk.newsapp.repository.RepositoryImpl
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map

class NewsListViewModel(private val repository: Repository) : BaseViewModel() {

    private val onUpdateListEmit = MutableStateFlow<List<NewsUiModel>>(listOf())
    val onUpdateList: Flow<List<NewsUiModel>> = onUpdateListEmit
    private val onRefreshToEarlyEmit = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val onRefreshToEarly: Flow<Unit> = onRefreshToEarlyEmit

    init {
        launch {
            repository.getNews().map { it.map { NewsUiModel.create(it) } }.also { onUpdateListEmit.emitAll(it) }
        }
        requestFetchNews()
    }

    fun onRefreshList() {
        requestFetchNews()
    }

    fun onOpenDetails(model: NewsUiModel) {//TODO TEST

    }

    private fun requestFetchNews() = launch {
        repository.refreshNews()
            .onFailure {
                when (it) {
                    is RepositoryImpl.ToEarlyRefreshException -> {
                        Log.i(LOG_TAG, "To early for fetch")
                        onRefreshToEarlyEmit.tryEmit(Unit)
                        //also make sense later to show this info to user
                    }
                    else -> {
                        Log.e(LOG_TAG, "Failed to get latest news", it)
                    }
                }
            }
    }

}