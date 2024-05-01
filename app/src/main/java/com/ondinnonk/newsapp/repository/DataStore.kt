package com.ondinnonk.newsapp.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStore(private val context: Context) {

    companion object {
        private const val DATA_STORE_NAME = "news_data_store"
        private val LAST_FETCH_TIME = longPreferencesKey("last_fetch_time")
    }

    val lastFetchTime: Flow<Long>
        get() = data.map { preferences ->
            preferences[LAST_FETCH_TIME] ?: 0L
        }

    suspend fun saveFetchTime(time: Long) {
        context.dataStore.edit { it[LAST_FETCH_TIME] = time }
    }

    private val data: Flow<Preferences>
        get() = context.dataStore
            .data
            .catch { exception ->
                exception.printStackTrace()
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_NAME
    )

}