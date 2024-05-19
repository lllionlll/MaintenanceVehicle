package io.maintenancevehicle.views.vehicle.vehicle_detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Vehicle
import io.maintenancevehicle.databinding.FragmentVehicleDetailBinding
import io.maintenancevehicle.utils.ConfirmDialog
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.customer.customer_detail.CustomerDetailRoute
import io.maintenancevehicle.views.vehicle.VehicleViewModel

@AndroidEntryPoint
class VehicleDetailFragment : BaseFragment<FragmentVehicleDetailBinding>(
    FragmentVehicleDetailBinding::inflate
) {
    private val vehicleViewModel by viewModels<VehicleViewModel>()
    private val safeVarargs by navArgs<VehicleDetailFragmentArgs>()

    private var vehicle = Vehicle()

    override fun initView() {
        super.initView()

        vehicleViewModel.getVehicleDetail(
            vehicleId = safeVarargs.vehicleId
        )
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            VehicleDetailRoute.backScreen(this)
        }

        binding.delete.setOnClickListener {
            ConfirmDialog.show(
                requireContext(),
                onConfirm = {
                    vehicleViewModel.deleteVehicle(
                        requireContext(),
                        vehicleId = vehicle.vehicleId
                    )
                }
            )
        }

        binding.edit.setOnClickListener {
            VehicleDetailRoute.goToVehicleEdit(this, vehicle.vehicleId)
        }

        binding.img.setOnClickListener {
            CustomerDetailRoute.goToImagePreview(
                this,
                customerId = vehicle.vehicleId
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

        vehicleViewModel.vehicle.observe(viewLifecycleOwner) { vehicle ->
            this.vehicle = vehicle
            binding.name.text = vehicle.name
            binding.brand.text = vehicle.brand
            binding.color.text = vehicle.color
            binding.status.text = vehicle.status
            binding.customerName.text = vehicle.customerName
            binding.vehicleType.text = vehicle.type.toString()
            binding.createdAt.text = vehicle.createdAt
            binding.updatedAt.text = vehicle.updatedAt
            binding.description.text = vehicle.description
            Glide.with(requireContext())
                .load(vehicle.image)
                .into(binding.img)
            binding.scrollView.visibility = View.VISIBLE
        }

        vehicleViewModel.isDeleteSuccess.observe(viewLifecycleOwner) {
            if (it) {
                VehicleDetailRoute.backScreen(this)
            }
        }
    }

}