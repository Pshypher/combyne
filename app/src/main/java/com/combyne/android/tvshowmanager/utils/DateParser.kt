package com.combyne.android.tvshowmanager.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateParser {

    private const val DEFAULT_DATE = "1 January 1970"

    fun parse(from: String, to: String, arg: String?): String {
        SimpleDateFormat(from, Locale.getDefault()).apply {
            val date = arg?.let{
                try {
                    parse(it)
                } catch (e: ParseException) {
                    e.printStackTrace()
                    null
                }
            }

            return date?.let {
                applyLocalizedPattern(to)
                format(it)
            } ?: DEFAULT_DATE
        }
    }
}