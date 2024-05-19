package io.maintenancevehicle.views.vehicle.vehicle_list

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentVehicleListBinding
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.service.service_maintenance.ServiceMaintenanceRoute
import io.maintenancevehicle.views.vehicle.VehicleAdapter
import io.maintenancevehicle.views.vehicle.VehicleViewModel

@AndroidEntryPoint
class VehicleListFragment : BaseFragment<FragmentVehicleListBinding>(
    FragmentVehicleListBinding::inflate
) {
    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            VehicleListRoute.backScreen(this)
        }

        binding.add.setOnClickListener {
            VehicleListRoute.goToVehicleAdd(this)
        }
    }

    private val vehicleViewModel by viewModels<VehicleViewModel>()

    private val vehicleAdapter = VehicleAdapter(
        onClickVehicleDetail = { vehicle ->
            VehicleListRoute.goToVehicleDetail(
                this,
                vehicleId = vehicle.vehicleId
            )
        }
    )

    override fun initView() {
        super.initView()
        vehicleViewModel.getVehicle()
        binding.rcVehicle.adapter = vehicleAdapter
        binding.rcVehicle.itemAnimator = null
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

        vehicleViewModel.vehicleList.observe(viewLifecycleOwner) { vehicleList ->
            vehicleAdapter.setItemList(itemList = vehicleList)
        }
    }
}