package com.ondinnonk.newsapp.features

import com.ondinnonk.newsapp.repository.News
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NewsUiModel(
    val uid: String,
    val title: String,
    val author: String,
    val image: String?,
    val date: String,
    val content: String,
) {

    companion object {

        private const val UI_DATE_FORMAT = "dd MMMM yyyy HH:mm"

        fun create(model: News): NewsUiModel {
            val dateStr = formatDateForUI(model.date).getOrDefault("")
            return NewsUiModel(
                uid = model.uid,
                title = model.title,
                author = model.author,
                image = model.image,
                date = dateStr,
                content = model.content
            )
        }

        fun formatDateForUI(date: Date): Result<String> {
            val output = SimpleDateFormat(UI_DATE_FORMAT, Locale.getDefault())
            val formattedTime: String = output.format(date)
            if (formattedTime.isBlank()) {
                return Result.failure(Exception("Created date string is empty, please check. Date = ${date}; FormattedTime = $formattedTime"))
            }
            return Result.success(formattedTime)
        }
    }

}