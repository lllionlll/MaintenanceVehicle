package io.maintenancevehicle.views.widget.widget_edit

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.data.model.Widget
import io.maintenancevehicle.databinding.FragmentWidgetEditBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.customer.customer_edit.CustomerEditRoute
import io.maintenancevehicle.views.service.ServiceMaintenanceViewModel
import io.maintenancevehicle.views.service.service_edit.ServiceEditFragmentArgs
import io.maintenancevehicle.views.service.service_edit.ServiceEditRoute
import io.maintenancevehicle.views.widget.WidgetViewModel
import java.util.Date

@AndroidEntryPoint
class WidgetEditFragment : BaseFragment<FragmentWidgetEditBinding>(
    FragmentWidgetEditBinding::inflate
) {

    private val widgetViewModel by viewModels<WidgetViewModel>()
    private val safeVarargs by navArgs<WidgetEditFragmentArgs>()

    private var widget = Widget()

    private var uri: Uri? = null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { galleryUri ->
        if (galleryUri != null) {
            uri = galleryUri
            binding.img.setImageURI(galleryUri)
        }
    }

    override fun initView() {
        super.initView()

        widgetViewModel.getWidgetDetail(
            widgetId = safeVarargs.widgetId
        )
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            WidgetEditRoute.backScreen(this)
        }

        binding.img.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.btnSave.setOnClickListener {
            val widget = Widget(
                widgetId = this.widget.widgetId,
                image = (uri ?: this.widget.image).toString(),
//                price = binding.p.text.toString().toInt(),
                name = binding.name.text.toString(),
                createdAt = this.widget.createdAt,
                updatedAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy HH:mm"
                )
            )
            widgetViewModel.editWidgets(
                requireContext(),
                widget
            )
        }

    }

    override fun observerData() {
        super.observerData()

        widgetViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        widgetViewModel.isEditSuccess.observe(viewLifecycleOwner) {
            if (it) {
                WidgetEditRoute.backScreen(this)
            }
        }

        widgetViewModel.widget.observe(viewLifecycleOwner) { widget ->
            this.widget = widget
            binding.name.setText(widget.name ?: "")
//            binding.price.setText(service.price.toString() ?: "")
            Glide.with(binding.img.context)
                .load(widget.image)
                .into(binding.img)
        }
    }

}