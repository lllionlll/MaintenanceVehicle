package io.maintenancevehicle.views.service.service_detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.databinding.FragmentServiceDetailBinding
import io.maintenancevehicle.utils.ConfirmDialog
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.customer.customer_detail.CustomerDetailFragmentArgs
import io.maintenancevehicle.views.customer.customer_detail.CustomerDetailRoute
import io.maintenancevehicle.views.customer.customer_detail.CustomerDetailViewModel
import io.maintenancevehicle.views.customer.customer_management.CustomerManagementRoute
import io.maintenancevehicle.views.service.ServiceMaintenanceViewModel
import io.maintenancevehicle.views.service.service_edit.ServiceEditRoute

@AndroidEntryPoint
class ServiceDetailFragment : BaseFragment<FragmentServiceDetailBinding>(
    FragmentServiceDetailBinding::inflate
) {

    private val serviceMaintenanceViewModel by viewModels<ServiceMaintenanceViewModel>()
    private val safeVarargs by navArgs<ServiceDetailFragmentArgs>()

    private var service = Service()

    override fun initView() {
        super.initView()

        serviceMaintenanceViewModel.getServiceDetail(
            serviceId = safeVarargs.serviceId
        )
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerManagementRoute.backScreen(this)
        }

        binding.delete.setOnClickListener {
            ConfirmDialog.show(
                requireContext(),
                onConfirm = {
                    serviceMaintenanceViewModel.deleteService(
                        requireContext(),
                        serviceId = service.id
                    )
                }
            )
        }

        binding.customerEdit.setOnClickListener {
            ServiceDetailRoute.goToServiceEdit(this, service.id)
        }

    }

    override fun observerData() {
        super.observerData()

        serviceMaintenanceViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        serviceMaintenanceViewModel.service.observe(viewLifecycleOwner) { service ->
            this.service = service
            binding.name.text = service.name
            Glide.with(requireContext())
                .load(service.image)
                .into(binding.img)
            binding.price.text = service.price.toString()
            binding.time.text = service.time
            binding.createdAt.text = service.createdAt
            binding.updatedAt.text = service.updatedAt
            binding.description.text = service.description
            binding.scrollView.visibility = View.VISIBLE
        }

        serviceMaintenanceViewModel.isDeleteSuccess.observe(viewLifecycleOwner) {
            if (it) {
                CustomerDetailRoute.backScreen(this)
            }
        }
    }

}