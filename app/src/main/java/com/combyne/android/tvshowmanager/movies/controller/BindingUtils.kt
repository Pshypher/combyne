package com.combyne.android.tvshowmanager.movies.controller

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.combyne.android.tvshowmanager.movies.domain.Movie
import java.lang.NumberFormatException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val DATE_PATTERN = "dd MMMM, yyyy"
private const val TAG = "BindingUtils"

@BindingAdapter("date")
fun TextView.parseReleaseDate(movie: Movie) {

    SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault()).apply {
        val date = movie.releaseDate?.let{
            try {
                parse(it)
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }
        }
        date?.let {
            applyLocalizedPattern(DATE_PATTERN)
            text = format(it)
        }
    }
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