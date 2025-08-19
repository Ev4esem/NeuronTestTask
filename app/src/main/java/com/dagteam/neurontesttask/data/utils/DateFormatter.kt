package com.dagteam.neurontesttask.data.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateFormatter {
    
    fun formatDate(isoDate: String): String {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val dateTime = LocalDateTime.parse(isoDate, inputFormatter)
                dateTime.format(outputFormatter)
            } else {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val date: Date = inputFormat.parse(isoDate) ?: return isoDate
                outputFormat.format(date)
            }
        } catch (e: Exception) {
            isoDate
        }
    }
}