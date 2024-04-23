package io.maintenancevehicle.views.service.service_add

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentServiceAddBinding

@AndroidEntryPoint
class ServiceAddFragment : BaseFragment<FragmentServiceAddBinding>(
    FragmentServiceAddBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            ServiceAddRoute.backScreen(this)
        }
    }

}