package io.maintenancevehicle.utils

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateFunction {

    fun getCurrentDate(): Date {
        return Date()
    }

    fun formatDate(
        date: Date,
        formatType: String
    ): String {
        return try {
            val dateFormat = SimpleDateFormat(formatType, Locale.getDefault())
            dateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun formatDate(
        dateString: String,
        formatType: String
    ): Date {
        return try {
            val dateFormat = SimpleDateFormat(formatType, Locale.getDefault())
            dateFormat.parse(dateString) ?: Date()
        } catch (e: Exception) {
            e.printStackTrace()
            Date()
        }
    }

    fun formatDate(
        dateString: String,
        dateStringFormat: String,
        formatType: String
    ): String {
        return try {
            val dateTimeFormatter =
                DateTimeFormatter.ofPattern(dateStringFormat, Locale.getDefault())
            val dateTime = LocalDateTime.parse(dateString, dateTimeFormatter)
            val formattedDate =
                dateTime.format(DateTimeFormatter.ofPattern(formatType, Locale.getDefault()))
            formattedDate
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun isDateInRange(
        dateToCheck: String,
        format: String,
        startDate: String,
        endDate: String
    ): Boolean {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        val dateToCheckObj: Date?
        val startDateObj: Date?
        val endDateObj: Date?

        try {
            dateToCheckObj = dateFormat.parse(dateToCheck)
            startDateObj = dateFormat.parse(startDate)
            endDateObj = dateFormat.parse(endDate)
        } catch (e: Exception) {
            return false
        }

        return dateToCheckObj in startDateObj..endDateObj
    }

    fun compareDates(
        date1: String,
        date2: String,
        formatType: String = Constants.FORMAT1
    ): Int {
        val format = DateTimeFormatter.ofPattern(formatType)
        val dateTime1 = LocalDateTime.parse(date1, format)
        val dateTime2 = LocalDateTime.parse(date2, format)

        return when {
            dateTime1.isBefore(dateTime2) -> -1
            dateTime1.isAfter(dateTime2) -> 1
            else -> 0
        }
    }

    fun showDatePicker(context: Context, onCompletion: (Date) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context, { _, selectedYear, monthOfYear, dayOfMonth ->
                calendar.set(selectedYear, monthOfYear, dayOfMonth)
                val selectedDate = calendar.time
                onCompletion.invoke(selectedDate)
            }, year, month, day
        )
        datePickerDialog.show()
    }

}