package io.maintenancevehicle.views.qr_code_scanner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.ScaleGestureDetector
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.mlkit.vision.barcode.common.Barcode
import io.maintenancevehicle.R
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentQrCodeScannerBinding
import io.maintenancevehicle.utils.Constants
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.roundToInt

class QrCodeScannerFragment : BaseFragment<FragmentQrCodeScannerBinding>(
    FragmentQrCodeScannerBinding::inflate
) {

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var camera: Camera? = null

    private var scale = 1.0f

    private lateinit var analysisExecutor: ExecutorService
    private var barcodeFormats = Barcode.FORMAT_ALL_FORMATS
    private var hapticFeedback = true
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var preview: Preview
    private lateinit var imageAnalysis: ImageAnalysis

    private var isTorch = false

    private val safeVarargs by navArgs<QrCodeScannerFragmentArgs>()

    override fun onDestroy() {
        super.onDestroy()
        cameraProvider.unbindAll()
        imageAnalysis.clearAnalyzer()
        analysisExecutor.shutdown()
    }

    override fun onResume() {
        super.onResume()
        camera?.cameraControl?.setZoomRatio(scale)
    }

    override fun initView() {
        super.initView()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            insets.getInsets(WindowInsetsCompat.Type.systemBars())
                .let { v.setPadding(it.left, it.top, it.right, it.bottom) }
            WindowInsetsCompat.CONSUMED
        }
        scale = 1.0f

        scaleGestureDetector = ScaleGestureDetector(
            requireContext(),
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    val scaleTemp = scale + (detector.scaleFactor - 1.0f) * 1.5f
                    val maxZoom = camera?.cameraInfo?.zoomState?.value?.maxZoomRatio ?: 1.0f
                    if (scaleTemp in 1.0f..maxZoom) {
                        scale += (detector.scaleFactor - 1.0f) * 1.5f
                        camera?.cameraControl?.setZoomRatio(scale)
                        binding.txtScale.text = "${((scale * 10).roundToInt() / 10.0f)}x"
                    }
                    return true
                }
            })


        analysisExecutor = Executors.newSingleThreadExecutor()
        startCamera()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun handleEvent() {
        super.handleEvent()

        binding.btnBack.setOnClickListener {
            val isBackToMenu = safeVarargs.isBackToMenu
            if (isBackToMenu) {
                QrCodeScannerRoute.backToMenu(this)
            } else {
                QrCodeScannerRoute.backScreen(this)
            }
        }

        binding.previewView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }

        binding.scale1x.setOnClickListener {
            scale = 1.0f
            binding.txtScale.text = "1.0x"
            camera?.cameraControl?.setZoomRatio(1.0f)
        }

        binding.btnFlash.setOnClickListener {
            if (isTorch) {
                binding.btnFlash.setImageResource(R.drawable.ic_flash_on)
                camera?.cameraControl?.enableTorch(false)
            } else {
                binding.btnFlash.setImageResource(R.drawable.ic_flash_off)
                camera?.cameraControl?.enableTorch(true)
            }
            isTorch = !isTorch
        }

    }

    private fun startCamera() {
        try {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
            cameraProviderFuture.addListener({
                cameraProvider = cameraProviderFuture.get()
                preview = Preview.Builder().build().apply {
                    setSurfaceProvider(binding.previewView.surfaceProvider)
                }
                imageAnalysis = ImageAnalysis.Builder()
                    .build()
                    .apply {
                        setAnalyzer(
                            analysisExecutor,
                            QrCodeAnalyzer(
                                barcodeFormats = barcodeFormats,
                                onSuccess = { qrcode ->
                                    binding.layoutQrScanner.isVisible = false
                                    lifecycleScope.launch {
                                        cameraProvider.unbindAll()
                                        imageAnalysis.clearAnalyzer()
                                        if (hapticFeedback) {
                                            val flags =
                                                HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING
                                            binding.root.performHapticFeedback(
                                                HapticFeedbackConstants.KEYBOARD_TAP,
                                                flags
                                            )
                                        }
                                        val bundle = Bundle()
                                        bundle.putString(
                                            Constants.VEHICLE_ID,
                                            qrcode.rawValue.toString()
                                        )
                                        setFragmentResult(
                                            Constants.VEHICLE_ID,
                                            bundle
                                        )
                                        QrCodeScannerRoute.backScreen(this@QrCodeScannerFragment)
                                    }
                                },
                                onFailure = {

                                }
                            )
                        )
                    }
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalysis
                )
            }, ContextCompat.getMainExecutor(requireContext()))
        } catch (e: Exception) {
            e.message
        }
    }

}