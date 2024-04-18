package io.maintenancevehicle.utils

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateFunction {

    fun getCurrentDate(): Date {
        return Date()
    }

    fun formatDate(date: Date, format: String): String {
        return try {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            dateFormat.format(date)
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