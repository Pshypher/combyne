package com.combyne.android.tvshowmanager.movies.controller

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.combyne.android.tvshowmanager.movies.domain.Movie
import com.combyne.android.tvshowmanager.utils.parse

private const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val DATE_PATTERN = "dd MMMM, yyyy"

@BindingAdapter("date")
fun TextView.parseReleaseDate(movie: Movie) {
    text = parse(DATE_TIME_PATTERN, DATE_PATTERN, movie.releaseDate)
}

@BindingAdapter("season")
fun TextView.parseSeason(movie: Movie) {
    text = try {
        movie.seasons?.toDouble()?.toInt()?.toString() ?: "0"
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        "0"
    }
}