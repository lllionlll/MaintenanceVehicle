package io.maintenancevehicle.views.maintenance_vehicle_view.maintenance_vehicle

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.VehicleDetail
import io.maintenancevehicle.databinding.FragmentMaintenanceVehicleBinding
import io.maintenancevehicle.utils.DateFunction
import java.util.UUID

@AndroidEntryPoint
class MaintenanceVehicleFragment : BaseFragment<FragmentMaintenanceVehicleBinding>(
    FragmentMaintenanceVehicleBinding::inflate
) {

    private val maintenanceVehicleViewModel by viewModels<MaintenanceVehicleViewModel>()

    override fun handleEvent() {
        super.handleEvent()

        binding.btnBack.setOnClickListener {
            MaintenanceVehicleRoute.backScreen(this)
        }

        binding.btnSave.setOnClickListener {
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