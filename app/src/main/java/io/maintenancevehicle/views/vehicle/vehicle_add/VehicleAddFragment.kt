package io.maintenancevehicle.views.vehicle.vehicle_add

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Vehicle
import io.maintenancevehicle.databinding.FragmentVehicleAddBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.vehicle.VehicleViewModel
import io.maintenancevehicle.views.widget.widget_add.WidgetAddRoute
import java.util.Date

@AndroidEntryPoint
class VehicleAddFragment : BaseFragment<FragmentVehicleAddBinding>(
    FragmentVehicleAddBinding::inflate
) {

    private val vehicleViewModel by viewModels<VehicleViewModel>()

    private var uri: Uri? = null

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { galleryUri ->
            if (galleryUri != null) {
                uri = galleryUri
                binding.img.setImageURI(galleryUri)
            }
        }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            WidgetAddRoute.backScreen(this)
        }
        binding.img.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
        binding.btnSave.setOnClickListener {
            val vehicle = Vehicle(
                name = binding.name.text.toString(),
                createdAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy HH:mm"
                ),
                updatedAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy HH:mm"
                ),
                image = (uri ?: "").toString(),
                description = binding.description.text.toString()
            )
            vehicleViewModel.saveVehicles(
                requireContext(),
                vehicle
            )
        }
    }

    override fun observerData() {
        super.observerData()
        vehicleViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        vehicleViewModel.isAddSuccess.observe(viewLifecycleOwner) {
            if (it) {
                VehicleAddRoute.backScreen(this)
            }
        }
    }

}