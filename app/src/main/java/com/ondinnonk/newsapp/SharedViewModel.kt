package com.ondinnonk.newsapp

import com.ondinnonk.newsapp.core.BaseViewModel
import com.ondinnonk.newsapp.features.NewsUiModel
import com.ondinnonk.newsapp.repository.News
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class SharedViewModel() : BaseViewModel() {

    private val openDetailsEmit = MutableStateFlow<NewsUiModel?>(null)
    val openDetails: Flow<NewsUiModel?> = openDetailsEmit
    private val closeDetailsEmit = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val closeDetails: Flow<Unit> = closeDetailsEmit

    fun onOpenDetails(model: News) {
        openDetailsEmit.tryEmit(NewsUiModel.create(model))
    }

    fun onCloseDetails() {
        closeDetailsEmit.tryEmit(Unit)
        openDetailsEmit.tryEmit(null)
    }

}