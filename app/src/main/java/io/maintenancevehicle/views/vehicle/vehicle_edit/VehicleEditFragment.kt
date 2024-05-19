package io.maintenancevehicle.views.vehicle.vehicle_edit

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.data.model.Vehicle
import io.maintenancevehicle.databinding.FragmentVehicleEditBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.customer.customer_edit.CustomerEditRoute
import io.maintenancevehicle.views.service.ServiceMaintenanceViewModel
import io.maintenancevehicle.views.service.service_edit.ServiceEditFragmentArgs
import io.maintenancevehicle.views.service.service_edit.ServiceEditRoute
import io.maintenancevehicle.views.vehicle.VehicleViewModel
import io.maintenancevehicle.views.vehicle.vehicle_add.VehicleAddRoute
import io.maintenancevehicle.views.widget.widget_add.WidgetAddRoute
import java.util.Date

@AndroidEntryPoint
class VehicleEditFragment : BaseFragment<FragmentVehicleEditBinding>(
    FragmentVehicleEditBinding::inflate
) {

    private val vehicleViewModel by viewModels<VehicleViewModel>()
    private val safeVarargs by navArgs<VehicleEditFragmentArgs>()

    private var vehicle = Vehicle()

    private var uri: Uri? = null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { galleryUri ->
        if (galleryUri != null) {
            uri = galleryUri
            binding.img.setImageURI(galleryUri)
        }
    }

    override fun initView() {
        super.initView()

        vehicleViewModel.getVehicleDetail(
            vehicleId = safeVarargs.vehicleId
        )
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerEditRoute.backScreen(this)
        }

        binding.img.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.btnSave.setOnClickListener {
            val vehicle = Vehicle(
                vehicleId = this.vehicle.vehicleId,
                image = (uri ?: this.vehicle.image).toString(),
//                price = binding.price.text.toString().toInt(),
                name = binding.name.text.toString(),
                createdAt = this.vehicle.createdAt,
                updatedAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy HH:mm"
                )
            )
            vehicleViewModel.editVehicles(
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

        vehicleViewModel.isEditSuccess.observe(viewLifecycleOwner) {
            if (it) {
                VehicleAddRoute.backScreen(this)
            }
        }

        vehicleViewModel.vehicle.observe(viewLifecycleOwner) { vehicle ->
            this.vehicle = vehicle
            binding.name.setText(vehicle.name ?: "")
//            binding.price.setText(service.price.toString() ?: "")
            Glide.with(binding.img.context)
                .load(vehicle.image)
                .into(binding.img)
        }
    }


}