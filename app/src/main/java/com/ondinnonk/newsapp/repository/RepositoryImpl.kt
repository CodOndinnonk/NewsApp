package com.ondinnonk.newsapp.repository

import android.text.format.DateUtils
import android.util.Log
import com.ondinnonk.newsapp.NewsApplication.Companion.LOG_TAG
import com.ondinnonk.newsapp.repository.local.RepositoryLocal
import com.ondinnonk.newsapp.repository.remote.RepositoryRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RepositoryImpl(
    private val remote: RepositoryRemote,
    private val local: RepositoryLocal,
    private val dataStore: DataStore
) : Repository {

    companion object {
        private const val FETCH_TIME_INTERVAL = 10 * DateUtils.MINUTE_IN_MILLIS
    }

    override suspend fun getNews(): Flow<List<News>> {
        return local.getAllNews()
    }

    override suspend fun refreshNews(): Result<Unit> = suspendCoroutine { cont ->
        launch {
            if (isAllowedToFetch()) {
                remote.fetchNewsFromServer().onSuccess { fetchedNews ->
                    //save news to DB
                    fetchedNews.forEach {
                        if (local.saveNews(it) == 0L) {
                            Log.e(LOG_TAG, "Failed to save record to DB. Model = $it")
                        }
                    }
                    dataStore.saveFetchTime(Date().time)
                    cont.resume(Result.success(Unit))
                }
                    .onFailure {
                        cont.resume(Result.failure(Exception("Failed to fetch new fresh news.", it)))
                    }
            } else {
                Log.i(LOG_TAG, "Fetch not allowed yet")
                cont.resume(Result.failure(ToEarlyRefreshException()))
            }
        }
    }

    /**
     * Check if we can fetch data agan, based on time of last update
     */
    private suspend fun isAllowedToFetch(): Boolean {
        dataStore.lastFetchTime.first().let { lastUpdate ->
            return Date().time - lastUpdate >= FETCH_TIME_INTERVAL
        }
    }


    class ToEarlyRefreshException() : Exception()

}