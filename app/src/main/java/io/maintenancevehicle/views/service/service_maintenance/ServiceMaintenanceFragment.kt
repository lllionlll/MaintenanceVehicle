package io.maintenancevehicle.views.service.service_maintenance

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentServiceMaintenanceBinding
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.service.ServiceMaintenanceAdapter
import io.maintenancevehicle.views.service.ServiceMaintenanceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        binding.btnFilter.setOnClickListener {
            filter()
        }

        binding.btnReset.setOnClickListener {
            resetAll()
            serviceMaintenanceAdapter.setItemList(
                itemList = serviceManagementViewModel.serviceList.value ?: mutableListOf()
            )
        }

        binding.btnControl.setOnClickListener {
            binding.layoutControl.isVisible = !binding.layoutControl.isVisible
        }

        binding.btnSearch.setOnClickListener {
            search()
        }
    }

    private fun filter() {
        lifecycleScope.launch {
            serviceManagementViewModel.isLoading.value = true
            withContext(Dispatchers.IO) {
                val vehicleDetailList =
                    serviceManagementViewModel.serviceList.value ?: mutableListOf()
                val vehicleDetailListSearch = vehicleDetailList.filter { service ->
                    val stringFilter = listOf(
                        service.name ?: ""
                    )
                    stringFilter.forEach { string ->
                        if (string.isNotEmpty() && string.lowercase()
                                .contains(binding.txtSearch.text.toString().lowercase())
                        ) {
                            return@filter true
                        }
                    }
                    false
                }.toMutableList()
                val vehicleDetailListFilter = vehicleDetailListSearch.filter { vehicleDetail ->
                    val x = binding.dateStart.text.toString().toInt()
                    val y = binding.dateEnd.text.toString().toInt()
                    vehicleDetail.price in x..y
                }.toMutableList()
                withContext(Dispatchers.Main) {
                    serviceMaintenanceAdapter.setItemList(vehicleDetailListFilter)
                    serviceManagementViewModel.isLoading.value = false
                }
            }
        }
    }

    private fun resetAll() {
        binding.txtSearch.setText("")
        if (binding.txtSearch.text.isEmpty()) {
            binding.dateStart.setText("")
            binding.dateEnd.setText("")
        }
    }

    private fun search() {
        lifecycleScope.launch {
            serviceManagementViewModel.isLoading.value = true
            withContext(Dispatchers.IO) {
                val vehicleDetailList =
                    serviceManagementViewModel.serviceList.value ?: mutableListOf()
                val vehicleDetailListSearch = vehicleDetailList.filter { service ->
                    val stringFilter = listOf(
                        service.name ?: "",
                    )
                    stringFilter.forEach { string ->
                        if (string.isNotEmpty() && string.lowercase()
                                .contains(binding.txtSearch.text.toString().lowercase())
                        ) {
                            return@filter true
                        }
                    }
                    false
                }.toMutableList()
                withContext(Dispatchers.Main) {
                    serviceMaintenanceAdapter.setItemList(vehicleDetailListSearch)
                    serviceManagementViewModel.isLoading.value = false
                }
            }
        }
    }

    private val serviceManagementViewModel by viewModels<ServiceMaintenanceViewModel>()

    private val serviceMaintenanceAdapter = ServiceMaintenanceAdapter(
        onClickServiceDetail = { service ->
            ServiceMaintenanceRoute.goToServiceDetail(
                this,
                serviceId = service.id
            )
        }
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

        serviceManagementViewModel.serviceList.observe(viewLifecycleOwner) { serviceList ->
            serviceMaintenanceAdapter.setItemList(itemList = serviceList)
        }
    }
}