package io.maintenancevehicle.views.customer.customer_management

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentCustomerManagementBinding
import io.maintenancevehicle.utils.LoadingDialog

@AndroidEntryPoint
class CustomerManagementFragment : BaseFragment<FragmentCustomerManagementBinding>(
    FragmentCustomerManagementBinding::inflate
) {

    private val customerManagementViewModel by viewModels<CustomerManagementViewModel>()

    private val customerManagementAdapter = CustomerManagementAdapter(
        onClickCustomerDetail = { customer ->
            CustomerManagementRoute.goToCustomerDetail(
                this,
                customerId = customer.id
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

        binding.addCustomer.setOnClickListener {
            CustomerManagementRoute.goToCustomerAdd(this)
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
            if (customerList.isNotEmpty()) {
                customerManagementAdapter.setItemList(itemList = customerList)
            }
        }
    }

}