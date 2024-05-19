package io.maintenancevehicle.utils

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import io.maintenancevehicle.databinding.DialogExportExcelOptionBinding

object ExportExcelOptionDialog {

    private lateinit var uri: Uri

    private lateinit var exportExcelOptionDialog: Dialog
    private lateinit var binding: DialogExportExcelOptionBinding

    fun setUri(uri: Uri) {
        this.uri = uri
        binding.txtSourcePath.text = uri.path
    }

    fun show(
        context: Context,
        selectDirectory: () -> Unit,
        exportExcel: (Uri, String) -> Unit
    ) {
        binding = DialogExportExcelOptionBinding.inflate(LayoutInflater.from(context))
        binding.btnChoosePath.setOnClickListener {
            selectDirectory.invoke()
        }
        binding.txtSourcePath.setOnClickListener {
            selectDirectory.invoke()
        }
        binding.btnSave.setOnClickListener {
            if (binding.txtSourcePath.text.toString().isEmpty() || binding.fileName.text.trim()
                    .toString().isEmpty()
            ) {
                Toast.makeText(
                    context,
                    "Vui lòng chọn đường dẫn và nhập tên file!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                exportExcel(uri, binding.fileName.text.trim().toString())
                hide()
            }
        }
        binding.cancel.setOnClickListener {
            hide()
        }
        exportExcelOptionDialog = Dialog(context)
        exportExcelOptionDialog.setContentView(binding.root)
        exportExcelOptionDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        exportExcelOptionDialog.show()
    }

    private fun hide() {
        if (exportExcelOptionDialog.isShowing) {
            exportExcelOptionDialog.dismiss()
        }
    }

}