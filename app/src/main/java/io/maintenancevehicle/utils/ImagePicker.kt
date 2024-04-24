package io.maintenancevehicle.utils

import android.Manifest
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

object ImagePicker {

    fun pickImageFromActivity(
        activity: AppCompatActivity,
        isMultiple: Boolean = false,
        callback: (List<Uri>?) -> Unit
    ) {
        activity.apply {
            val pickImages = registerForActivityResult(
                ActivityResultContracts.GetMultipleContents()
            ) { uris ->
                callback(uris)
            }
            val pickImage = registerForActivityResult(
                ActivityResultContracts.GetContent()
            ) { uri ->
                if (uri == null) {
                    callback(null)
                } else {
                    callback(listOf(uri))
                }
            }
            registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
            ) { isGranted: Boolean ->
                if (isGranted) {
                    if (isMultiple) {
                        pickImages.launch("image/*")
                    } else {
                        pickImage.launch("image/*")
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                    if (isMultiple) {
                        pickImages.launch("image/*")
                    } else {
                        pickImage.launch("image/*")
                    }
                }
            }.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

}