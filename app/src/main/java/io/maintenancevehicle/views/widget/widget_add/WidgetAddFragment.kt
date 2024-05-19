package io.maintenancevehicle.views.widget.widget_add

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Widget
import io.maintenancevehicle.databinding.FragmentWidgetAddBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.widget.WidgetViewModel
import java.util.Date

@AndroidEntryPoint
class WidgetAddFragment : BaseFragment<FragmentWidgetAddBinding>(
    FragmentWidgetAddBinding::inflate
) {

    private val widgetViewModel by viewModels<WidgetViewModel>()

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
            WidgetAddRoute.backScreen(this)
        }
        binding.img.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
        binding.btnSave.setOnClickListener {
            val widget = Widget(
                name = binding.name.text.toString(),
                createdAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy HH:mm"
                ),
                updatedAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy HH:mm"
                ),
                image = (uri ?: "").toString()
            )
            widgetViewModel.saveWidgets(
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

        widgetViewModel.isAddSuccess.observe(viewLifecycleOwner) {
            if (it) {
                WidgetAddRoute.backScreen(this)
            }
        }
    }

}