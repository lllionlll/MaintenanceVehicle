package io.maintenancevehicle.views.customer.customer_management

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentCustomerManagementBinding
import io.maintenancevehicle.utils.LoadingDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CustomerManagementFragment : BaseFragment<FragmentCustomerManagementBinding>(
    FragmentCustomerManagementBinding::inflate
) {

    private val customerManagementViewModel by viewModels<CustomerManagementViewModel>()

    private val customerManagementAdapter = CustomerManagementAdapter(
        onClickCustomerDetail = { customer ->
            CustomerManagementRoute.goToCustomerDetail(
                this,
                customerId = customer.customerId
            )
        }
    )

    override fun initView() {
        super.initView()
        customerManagementViewModel.getCustomers()
        binding.rcCustomer.adapter = customerManagementAdapter
        binding.rcCustomer.itemAnimator = null
    }



    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerManagementRoute.backScreen(this)
        }

        binding.btnReset.setOnClickListener {
            resetAll()
            customerManagementAdapter.setItemList(
                itemList = customerManagementViewModel.customerList.value ?: mutableListOf()
            )
        }

        binding.addCustomer.setOnClickListener {
            CustomerManagementRoute.goToCustomerAdd(this)
        }

        binding.btnControl.setOnClickListener {
            binding.layoutControl.isVisible = !binding.layoutControl.isVisible
        }

        binding.btnSearch.setOnClickListener {
            search()
        }
    }

    private fun resetAll() {
        binding.txtSearch.setText("")
    }

    private fun search() {
        lifecycleScope.launch {
            customerManagementViewModel.isLoading.value = true
            withContext(Dispatchers.IO) {
                val vehicleDetailList =
                    customerManagementViewModel.customerList.value ?: mutableListOf()
                val vehicleDetailListSearch = vehicleDetailList.filter { customer ->
                    val stringFilter = listOf(
                        customer.customerId,
                        customer.name ?: "",
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
                    customerManagementAdapter.setItemList(vehicleDetailListSearch)
                    customerManagementViewModel.isLoading.value = false
                }
            }
        }
    }

    override fun observerData() {
        super.observerData()

        customerManagementViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        customerManagementViewModel.customerList.observe(viewLifecycleOwner) { customerList ->
            customerManagementAdapter.setItemList(itemList = customerList)
        }
    }

}