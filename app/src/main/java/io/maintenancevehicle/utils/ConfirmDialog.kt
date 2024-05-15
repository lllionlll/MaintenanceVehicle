package io.maintenancevehicle.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import io.maintenancevehicle.databinding.DialogConfirmBinding

object ConfirmDialog {

    private lateinit var confirmDialog: Dialog

    fun show(
        context: Context,
        onConfirm: () -> Unit = {}
    ) {
        val binding = DialogConfirmBinding.inflate(LayoutInflater.from(context))
        binding.yes.setOnClickListener {
            onConfirm.invoke()
            hide()
        }
        binding.no.setOnClickListener {
            hide()
        }
        confirmDialog = Dialog(context)
        confirmDialog.setContentView(binding.root)
        confirmDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        confirmDialog.show()
    }

    private fun hide() {
        if (confirmDialog.isShowing) {
            confirmDialog.dismiss()
        }
    }

}