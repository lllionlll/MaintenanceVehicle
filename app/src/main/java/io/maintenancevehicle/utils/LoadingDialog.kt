package io.maintenancevehicle.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import io.maintenancevehicle.databinding.ItemLoadingBinding

object LoadingDialog {

    private const val LOADING_TIME_OUT = 5000L

    private lateinit var loadingDialog: Dialog
    private lateinit var binding: ViewBinding

    fun showLoading(
        context: Context,
        cancelOnTouchOutSide: Boolean = false,
        cancelable: Boolean = false
    ) {
        binding = ItemLoadingBinding.inflate(LayoutInflater.from(context))
        loadingDialog = Dialog(context).apply {
            setContentView(binding.root)
            setCancelable(cancelable)
            setCanceledOnTouchOutside(cancelOnTouchOutSide)
            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                setDimAmount(0f)
            }
        }
        if (!loadingDialog.isShowing) {
            loadingDialog.show()
            Handler(Looper.getMainLooper()).postDelayed({
                if (loadingDialog.isShowing) {
                    loadingDialog.dismiss()
                }
            }, LOADING_TIME_OUT)
        }
    }

    fun hide() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }


}
