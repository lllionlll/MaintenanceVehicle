package io.maintenancevehicle.views.service.service_maintenance

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentServiceMaintenanceBinding
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.customer.customer_management.CustomerManagementAdapter
import io.maintenancevehicle.views.customer.customer_management.CustomerManagementRoute
import io.maintenancevehicle.views.customer.customer_management.CustomerManagementViewModel

@AndroidEntryPoint
class ServiceMaintenanceFragment : BaseFragment<FragmentServiceMaintenanceBinding>(
    FragmentServiceMaintenanceBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            ServiceMaintenanceRoute.backScreen(this)
        }

        binding.serviceAdd.setOnClickListener {
            ServiceMaintenanceRoute.goToServiceAdd(this)
        }
    }

    private val serviceManagementViewModel by viewModels<ServiceMaintenanceViewModel>()

    private val serviceMaintenanceAdapter = ServiceMaintenanceAdapter(
//        onClickCustomerDetail = { customer ->
//            CustomerManagementRoute.goToCustomerDetail(
//                this,
//                customerId = customer.id
//            )
//        }
    )

    override fun initView() {
        super.initView()
        serviceManagementViewModel.getServices()
        binding.rcService.adapter = serviceMaintenanceAdapter
        binding.rcService.itemAnimator = null
    }

    override fun observerData() {
        super.observerData()

        serviceManagementViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        serviceManagementViewModel.customerList.observe(viewLifecycleOwner) { serviceList ->
            if (serviceList.isNotEmpty()) {
                serviceMaintenanceAdapter.setItemList(itemList = serviceList)
            }
        }
    }
}