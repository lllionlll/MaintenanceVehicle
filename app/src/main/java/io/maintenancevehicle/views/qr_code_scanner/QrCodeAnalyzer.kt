package io.maintenancevehicle.views.qr_code_scanner

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

internal class QrCodeAnalyzer(
    private val barcodeFormats: Int,
    private val onSuccess: ((Barcode) -> Unit),
    private val onFailure: ((Exception) -> Unit),
) : ImageAnalysis.Analyzer {

    private val barcodeScanner by lazy {
        val optionsBuilder = BarcodeScannerOptions.Builder().setBarcodeFormats(barcodeFormats)
        BarcodeScanning.getClient(optionsBuilder.build())
    }

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        if (imageProxy.image == null) return
        barcodeScanner.let { scanner ->
            scanner.process(imageProxy.toInputImage())
                .addOnSuccessListener { codes ->
                    codes.firstNotNullOfOrNull { it }?.let { onSuccess(it) }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
                .addOnFailureListener {
                    onFailure(it)
                }
        }
    }

    @ExperimentalGetImage
    private fun ImageProxy.toInputImage() =
        InputImage.fromMediaImage(image!!, imageInfo.rotationDegrees)
}