package io.maintenancevehicle.views.widget.widget_list

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentWidgetListBinding
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.service.service_maintenance.ServiceMaintenanceRoute
import io.maintenancevehicle.views.widget.WidgetAdapter
import io.maintenancevehicle.views.widget.WidgetViewModel

@AndroidEntryPoint
class WidgetListFragment : BaseFragment<FragmentWidgetListBinding>(
    FragmentWidgetListBinding::inflate
) {
    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            ServiceMaintenanceRoute.backScreen(this)
        }

        binding.widgetAdd.setOnClickListener {
            WidgetListRoute.goToWidgetAdd(this)
        }
    }

    private val widgetViewModel by viewModels<WidgetViewModel>()

    private val widgetAdapter = WidgetAdapter(
        onClickWidgetDetail = { widget ->
            WidgetListRoute.goToWidgetDetail(
                this,
                widgetId = widget.widgetId
            )
        }
    )

    override fun initView() {
        super.initView()
        widgetViewModel.getWidgets()
        binding.rcWidget.adapter = widgetAdapter
        binding.rcWidget.itemAnimator = null
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

        widgetViewModel.widgetList.observe(viewLifecycleOwner) { widgetList ->
            widgetAdapter.setItemList(itemList = widgetList)
        }
    }
}