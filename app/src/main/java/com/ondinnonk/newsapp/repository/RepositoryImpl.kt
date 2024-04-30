package com.ondinnonk.newsapp.repository

import android.util.Log
import com.ondinnonk.newsapp.NewsApplication.Companion.LOG_TAG
import com.ondinnonk.newsapp.repository.remote.ServerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(private val serverApi: ServerApi) : Repository {

    override suspend fun getNews(): Result<List<News>> {
        return getNewsFromServer()
    }

    private suspend fun getNewsFromServer(): Result<List<News>> {
        return kotlin.runCatching {
            val result = withContext(Dispatchers.IO) {
                serverApi.getLatestNews()
            }

            if (result.isSuccessful) {
                result.body()?.let { body ->
                    body.getContent()
                        .onSuccess {
                            val mapped = mutableListOf<News>()
                            it.forEach {
                                News.create(it)
                                    .onSuccess { mapped.add(it) }
                                    .onFailure {
                                        //can be processed differently base on requirements
                                        Log.e(LOG_TAG, "Skip news record.", it)
                                    }
                            }
                            return Result.success(mapped)
                        }
                        .onFailure {
                            val details = "Failed to get news from server."
                            return Result.failure(Exception(details, it))
                        }
                    throw Exception("Never reach point")
                } ?: kotlin.run {
                    val details =
                        "Failed to get news from server. Request success but body is invalid. ${result.message()}; ${result.errorBody()}"
                    return Result.failure(Exception(details))
                }
            } else {
                val details =
                    "Failed to get news from server. Request failed. Code = ${result.code()}; Msg = ${result.message()}; ${result.errorBody()}"
                return Result.failure(Exception(details))
            }
        }
    }


}