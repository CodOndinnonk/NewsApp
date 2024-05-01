package com.ondinnonk.newsapp.repository.local.db

import androidx.room.TypeConverter
import java.util.Date

internal class DateTypeConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}