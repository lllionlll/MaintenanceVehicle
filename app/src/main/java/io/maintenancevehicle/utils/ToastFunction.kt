package io.maintenancevehicle.utils

import android.content.Context
import android.widget.Toast

object ToastFunction {

    fun showMessage(context: Context, message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}