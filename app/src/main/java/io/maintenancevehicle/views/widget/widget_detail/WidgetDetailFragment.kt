package io.maintenancevehicle.views.widget.widget_detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Widget
import io.maintenancevehicle.databinding.FragmentWidgetDetailBinding
import io.maintenancevehicle.utils.ConfirmDialog
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.customer.customer_detail.CustomerDetailRoute
import io.maintenancevehicle.views.vehicle.vehicle_detail.VehicleDetailFragmentArgs
import io.maintenancevehicle.views.vehicle.vehicle_detail.VehicleDetailRoute
import io.maintenancevehicle.views.widget.WidgetViewModel

@AndroidEntryPoint
class WidgetDetailFragment : BaseFragment<FragmentWidgetDetailBinding>(
    FragmentWidgetDetailBinding::inflate
) {

    private val widgetViewModel by viewModels<WidgetViewModel>()
    private val safeVarargs by navArgs<WidgetDetailFragmentArgs>()

    private var widget = Widget()

    override fun initView() {
        super.initView()

        widgetViewModel.getWidgetDetail(
            widgetId = safeVarargs.widgetId
        )
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            WidgetDetailRoute.backScreen(this)
        }

        binding.delete.setOnClickListener {
            ConfirmDialog.show(
                requireContext(),
                onConfirm = {
                    widgetViewModel.deleteWidget(
                        requireContext(),
                        widgetId = widget.widgetId
                    )
                }
            )
        }

        binding.edit.setOnClickListener {
            WidgetDetailRoute.goToWidgetEdit(this, widget.widgetId)
        }

        binding.img.setOnClickListener {
//            WidgetDetailRoute.(
//                this,
//                customerId = widget.widgetId
//            )
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

        widgetViewModel.widget.observe(viewLifecycleOwner) { widget ->
            this.widget = widget
            binding.name.text = widget.name
            binding.brand.text = widget.brand
            binding.color.text = widget.color
            binding.from.text = widget.from
            binding.price.text = widget.price?.toString()
            binding.createdAt.text = widget.createdAt
            binding.updatedAt.text = widget.updatedAt
            Glide.with(requireContext())
                .load(widget.image)
                .into(binding.img)
            binding.scrollView.visibility = View.VISIBLE
        }

        widgetViewModel.isDeleteSuccess.observe(viewLifecycleOwner) {
            if (it) {
                WidgetDetailRoute.backScreen(this)
            }
        }
    }

}