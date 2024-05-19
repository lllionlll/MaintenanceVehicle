package io.maintenancevehicle.views.service.service_add

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.databinding.FragmentServiceAddBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.service.ServiceMaintenanceViewModel
import java.util.Date

@AndroidEntryPoint
class ServiceAddFragment : BaseFragment<FragmentServiceAddBinding>(
    FragmentServiceAddBinding::inflate
) {

    private val serviceMaintenanceViewModel by viewModels<ServiceMaintenanceViewModel>()

    private var uri: Uri? = null

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { galleryUri ->
            if (galleryUri != null) {
                uri = galleryUri
                binding.img.setImageURI(galleryUri)
            }
        }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            ServiceAddRoute.backScreen(this)
        }
        binding.img.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
        binding.btnSave.setOnClickListener {
            val service = Service(
                name = binding.name.text.toString(),
                price = binding.price.text.toString().toInt(),
                createdAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy HH:mm"
                ),
                updatedAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy HH:mm"
                ),
                image = (uri ?: "").toString(),
                description = binding.description.text.toString()
            )
            serviceMaintenanceViewModel.saveServices(
                requireContext(),
                service
            )
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

        serviceMaintenanceViewModel.isAddSuccess.observe(viewLifecycleOwner) {
            if (it) {
                ServiceAddRoute.backScreen(this)
            }
        }
    }

}