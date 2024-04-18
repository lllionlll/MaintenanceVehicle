package io.maintenancevehicle.views.maintenance_vehicle

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.R
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.VehicleDetail
import io.maintenancevehicle.databinding.FragmentMaintenanceVehicleBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.hideKeyboard
import java.util.UUID
import java.util.concurrent.ExecutorService

@AndroidEntryPoint
class MaintenanceVehicleFragment : BaseFragment<FragmentMaintenanceVehicleBinding>(
    FragmentMaintenanceVehicleBinding::inflate
) {

    private val maintenanceVehicleViewModel by viewModels<MaintenanceVehicleViewModel>()

    private lateinit var analysisExecutor: ExecutorService
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var imageAnalysis: ImageAnalysis

    private var isEdit = false
    private var isScroll = false

    override fun onDestroy() {
        super.onDestroy()
        cameraProvider.unbindAll()
        imageAnalysis.clearAnalyzer()
        analysisExecutor.shutdown()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun handleEvent() {
        super.handleEvent()

        binding.btnBack.setOnClickListener {
            binding.root.hideKeyboard()
            MaintenanceVehicleRoute.backScreen(this)
        }

        binding.btnSave.setOnClickListener {
            binding.root.hideKeyboard()
            maintenanceVehicleViewModel.saveVehicles(
                requireContext(),
                listOf(
                    VehicleDetail(
                        id = UUID.randomUUID().toString(),
                        status = if (binding.status1.isChecked)
                            binding.status1.text.toString()
                        else
                            binding.status2.text.toString(),
                        reason = binding.reason.text.toString().trim(),
                        solution = binding.solution.text.toString().trim(),
                        supervisor = binding.supervisor.text.toString().trim(),
                        createdAt = DateFunction.formatDate(
                            DateFunction.getCurrentDate(),
                            "dd/MM/yyy"
                        ),
                        note = binding.note.text.toString().trim()
                    )
                )
            )
            clearTextField()
            binding.layoutQrScanner.isVisible = true
            binding.btnControl.setImageResource(R.drawable.ic_edit)
            isEdit = false
            binding.root.hideKeyboard()
        }

        binding.btnControl.setOnClickListener {
            binding.root.hideKeyboard()
            isEdit = !isEdit
            if (isEdit) {
                binding.layoutQrScanner.isVisible = false
                binding.btnControl.setImageResource(R.drawable.ic_qr_code)
            } else {
                binding.layoutQrScanner.isVisible = true
                binding.btnControl.setImageResource(R.drawable.ic_edit)
            }
        }

        binding.linearLayout.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {

                MotionEvent.ACTION_MOVE -> {
                    isScroll = true
                }

                MotionEvent.ACTION_UP -> {
                    if (!isScroll) {
                        binding.root.hideKeyboard()
                    }
                    isScroll = false
                }

                else -> {

                }
            }
            false
        }
    }

    private fun clearTextField() {
        binding.vehicleId.setText("")
        binding.status1.isChecked = true
        binding.status2.isChecked = false
        binding.reason.setText("")
        binding.solution.setText("")
        binding.supervisor.setText("")
        binding.note.setText("")
    }

}