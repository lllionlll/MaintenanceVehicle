package io.maintenancevehicle.views.history.maintenance_vehicle

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.History
import io.maintenancevehicle.databinding.FragmentMaintenanceVehicleBinding
import io.maintenancevehicle.utils.Constants
import io.maintenancevehicle.utils.DateFunction

@AndroidEntryPoint
class MaintenanceVehicleFragment : BaseFragment<FragmentMaintenanceVehicleBinding>(
    FragmentMaintenanceVehicleBinding::inflate
) {

    private val maintenanceVehicleViewModel by viewModels<MaintenanceVehicleViewModel>()

    override fun initData() {
        super.initData()
        MaintenanceVehicleRoute.goToQrCodeScanner(this)
    }

    override fun initView() {
        super.initView()
        setFragmentResultListener(Constants.VEHICLE_ID) { _, result ->
            val vehicleId = result.getString(Constants.VEHICLE_ID)
            if (vehicleId.isNullOrEmpty()) {
                MaintenanceVehicleRoute.backScreen(this)
            } else {
                binding.vehicleId.setText(vehicleId)
            }
        }
    }

    override fun handleEvent() {
        super.handleEvent()

        binding.btnBack.setOnClickListener {
            MaintenanceVehicleRoute.backScreen(this)
        }

        binding.btnControl.setOnClickListener {
            MaintenanceVehicleRoute.goToQrCodeScanner(this)
        }

        binding.btnSave.setOnClickListener {
            maintenanceVehicleViewModel.saveVehicles(
                requireContext(),
                listOf(
                    History(
                        vehicleId = binding.vehicleId.text.toString(),
                        status = if (binding.status1.isChecked)
                            binding.status1.text.toString()
                        else
                            binding.status2.text.toString(),
                        reason = binding.reason.text.toString().trim(),
                        solution = binding.solution.text.toString().trim(),
                        supervisor = binding.supervisor.text.toString().trim(),
                        createdAt = DateFunction.formatDate(
                            date = DateFunction.getCurrentDate(),
                            formatType = Constants.FORMAT1
                        ),
                        note = binding.note.text.toString().trim()
                    )
                )
            )
            clearTextField()
            MaintenanceVehicleRoute.goToQrCodeScanner(this)
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