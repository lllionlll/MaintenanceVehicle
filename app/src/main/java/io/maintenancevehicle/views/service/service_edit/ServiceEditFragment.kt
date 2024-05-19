package io.maintenancevehicle.views.service.service_edit

import android.R
import android.net.Uri
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.databinding.FragmentServiceEditBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.customer.customer_detail.CustomerDetailFragmentArgs
import io.maintenancevehicle.views.customer.customer_edit.CustomerEditRoute
import io.maintenancevehicle.views.customer.customer_edit.CustomerEditViewModel
import io.maintenancevehicle.views.service.ServiceMaintenanceViewModel
import java.util.Date

@AndroidEntryPoint
class ServiceEditFragment : BaseFragment<FragmentServiceEditBinding>(
    FragmentServiceEditBinding::inflate
) {

    private val serviceViewModel by viewModels<ServiceMaintenanceViewModel>()
    private val safeVarargs by navArgs<ServiceEditFragmentArgs>()

    private var service = Service()

    private var uri: Uri? = null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { galleryUri ->
        if (galleryUri != null) {
            uri = galleryUri
            binding.img.setImageURI(galleryUri)
        }
    }

    override fun initView() {
        super.initView()

        serviceViewModel.getServiceDetail(
            serviceId = safeVarargs.serviceId
        )
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerEditRoute.backScreen(this)
        }

        binding.img.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.btnSave.setOnClickListener {
            val service = Service(
                id = this.service.id,
                image = (uri ?: this.service.image).toString(),
                price = binding.price.text.toString().toInt(),
                name = binding.name.text.toString(),
                createdAt = this.service.createdAt,
                updatedAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy HH:mm"
                )
            )
            serviceViewModel.editServices(
                requireContext(),
                service
            )
        }

    }

    override fun observerData() {
        super.observerData()

        serviceViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        serviceViewModel.isEditSuccess.observe(viewLifecycleOwner) {
            if (it) {
                ServiceEditRoute.backScreen(this)
            }
        }

        serviceViewModel.service.observe(viewLifecycleOwner) { service ->
            this.service = service
            binding.name.setText(service.name ?: "")
            binding.price.setText(service.price.toString() ?: "")
            Glide.with(binding.img.context)
                .load(service.image)
                .into(binding.img)
        }
    }

}