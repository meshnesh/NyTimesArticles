package com.munene.nyTimePost.helper

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class Utils {

    fun convertSeconds(s: String): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(s.toLong())
        return minutes.toString()
    }
}

abstract class UtilMethods {
    /**
     * This method formats a date string to dd-MM-yyyy h:mm
     *
     * @param timestamp timestamp as string
     * @return String
     */
    fun dateTimeFormatter(timestamp: String?): String {
        return try {
            val dateformat = SimpleDateFormat("yyyy/MM/dd  HH:mm:ss SSSZ", Locale.ENGLISH)
            val d = dateformat.parse(timestamp)
            val date = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(d)
            val time = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(d)
            "$date $time"
        } catch (e: Exception) {
            ""
        }
    }

    fun dateTimeFormatter(timestamp: Int): String {
        return try {
            val minutes = TimeUnit.NANOSECONDS.toMinutes(timestamp.toLong())
            val millis = TimeUnit.MINUTES.convert(timestamp.toLong(), TimeUnit.NANOSECONDS)
            val date = Date(minutes)
            val formatter: DateFormat = SimpleDateFormat("mm:ss", Locale.ENGLISH)
            formatter.format(date)
        } catch (e: Exception) {
            ""
        }
    }
}