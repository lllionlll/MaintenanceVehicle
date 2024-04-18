package io.maintenancevehicle.views.customer.customer_add

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.databinding.FragmentCustomerAddBinding
import io.maintenancevehicle.databinding.FragmentCustomerManagementBinding
import io.maintenancevehicle.views.customer.customer_management.CustomerManagementViewModel

@AndroidEntryPoint
class CustomerAddFragment : BaseFragment<FragmentCustomerAddBinding>(
    FragmentCustomerAddBinding::inflate
) {

    private val customerAddViewModel by viewModels<CustomerAddViewModel>()

    override fun initView() {
        super.initView()

    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerAddRoute.backScreen(this)
        }
    }
}