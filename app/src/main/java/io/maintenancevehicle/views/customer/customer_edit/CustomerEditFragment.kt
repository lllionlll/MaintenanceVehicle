package io.maintenancevehicle.views.customer.customer_edit

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentCustomerEditBinding

@AndroidEntryPoint
class CustomerEditFragment : BaseFragment<FragmentCustomerEditBinding>(
    FragmentCustomerEditBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerEditRoute.backScreen(this)
        }
    }

}