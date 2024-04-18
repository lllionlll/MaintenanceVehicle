package io.maintenancevehicle.views.customer.customer_add

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentCustomerManagementBinding

@AndroidEntryPoint
class CustomerAddFragment : BaseFragment<FragmentCustomerManagementBinding>(
    FragmentCustomerManagementBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerAddRoute.backScreen(this)
        }
    }
}