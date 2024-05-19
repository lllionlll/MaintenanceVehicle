package io.maintenancevehicle.views.history.history_maintenance

import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentHistoryMaintenanceBinding
import io.maintenancevehicle.utils.ConfirmDialog
import io.maintenancevehicle.utils.Constants
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.ExportExcelOptionDialog
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.history.maintenance_vehicle.MaintenanceVehicleAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HistoryMaintenanceFragment : BaseFragment<FragmentHistoryMaintenanceBinding>(
    FragmentHistoryMaintenanceBinding::inflate
) {

    private val historyMaintenanceViewModel by viewModels<HistoryMaintenanceViewModel>()
    private var maintenanceVehicleAdapter = MaintenanceVehicleAdapter(
        isDelete = true,
        openSelected = {
            binding.layoutAppbar2.visibility = View.VISIBLE
            binding.layoutAppbar1.visibility = View.INVISIBLE
        },
        updateSelected = { selectedCount ->
            binding.txtSelected.text = "Đã chọn ($selectedCount)"
        }
    )

    private val selectDirectoryLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
            uri?.let {
                ExportExcelOptionDialog.setUri(uri)
            }
        }

    override fun initView() {
        super.initView()
        historyMaintenanceViewModel.getVehicles()
        binding.rcVehicleDetail.adapter = maintenanceVehicleAdapter
        binding.rcVehicleDetail.itemAnimator = null
        setFragmentResultListener(Constants.VEHICLE_ID) { _, result ->
            val vehicleId = result.getString(Constants.VEHICLE_ID)
            binding.txtSearch.setText(vehicleId ?: "")
        }
    }

    override fun onBackPressed(): Boolean {
        if (binding.layoutAppbar2.visibility == View.VISIBLE) {
            maintenanceVehicleAdapter.hideSelected()
            binding.layoutAppbar1.visibility = View.VISIBLE
            binding.layoutAppbar2.visibility = View.INVISIBLE
        } else {
            return false
        }
        return true
    }

    override fun handleEvent() {
        super.handleEvent()

        binding.btnBack.setOnClickListener {
            HistoryMaintenanceRoute.backScreen(this)
        }

        binding.btnClose.setOnClickListener {
            maintenanceVehicleAdapter.hideSelected()
            binding.layoutAppbar1.visibility = View.VISIBLE
            binding.layoutAppbar2.visibility = View.INVISIBLE
        }

        binding.btnControl.setOnClickListener {
            binding.layoutControl.isVisible = !binding.layoutControl.isVisible
        }

        binding.btnSelectAll.setOnClickListener {
            if (maintenanceVehicleAdapter.idDeleteList.size == maintenanceVehicleAdapter.itemList.size) {
                maintenanceVehicleAdapter.unSelectedAll()
            } else {
                maintenanceVehicleAdapter.selectedAll()
            }
        }

        binding.btnSort.setOnClickListener {
            maintenanceVehicleAdapter.reverseList()
        }

        binding.btnDelete.setOnClickListener {
            if (maintenanceVehicleAdapter.idDeleteList.size == 0) {
                Toast.makeText(
                    requireContext(),
                    "Chọn một thẻ bất kì để xóa!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                ConfirmDialog.show(requireContext()) {
                    historyMaintenanceViewModel.deleteVehicles(
                        requireContext(),
                        maintenanceVehicleAdapter.idDeleteList
                    ) {
                        maintenanceVehicleAdapter.removeVehicle()
                        maintenanceVehicleAdapter.hideSelected()
                        binding.layoutAppbar1.visibility = View.VISIBLE
                        binding.layoutAppbar2.visibility = View.INVISIBLE
                    }
                }
            }
        }

        binding.btnSave.setOnClickListener {
            if (maintenanceVehicleAdapter.itemList.size == 0) {
                Toast.makeText(
                    requireContext(),
                    "Danh sách trống",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                ExportExcelOptionDialog.show(
                    requireContext(),
                    selectDirectory = {
                        selectDirectoryLauncher.launch(null)
                    },
                    exportExcel = { uri, fileName ->
                        historyMaintenanceViewModel.exportExcel(
                            requireContext(),
                            fileName = fileName,
                            maintenanceVehicleAdapter.itemList,
                            uri = uri
                        )
                    }
                )
            }
        }

        binding.btnSearch.setOnClickListener {
            search()
        }

        binding.btnReset.setOnClickListener {
            resetAll()
            maintenanceVehicleAdapter.setItemList(
                itemList = historyMaintenanceViewModel.historyList.value ?: mutableListOf()
            )
        }

        binding.btnFilter.setOnClickListener {
            filter()
        }

        binding.qrCodeScanner.setOnClickListener {
            HistoryMaintenanceRoute.goToQrCodeScanner(this)
        }

        binding.dateStart.setOnClickListener {
            DateFunction.showDatePicker(requireContext()) { date ->
                binding.dateStart.setText(
                    DateFunction.formatDate(
                        date = date,
                        formatType = Constants.FORMAT2
                    )
                )
            }
        }

        binding.dateEnd.setOnClickListener {
            DateFunction.showDatePicker(requireContext()) { date ->
                binding.dateEnd.setText(
                    DateFunction.formatDate(
                        date = date,
                        formatType = Constants.FORMAT2
                    )
                )
            }
        }

    }

    override fun observerData() {
        super.observerData()

        historyMaintenanceViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        historyMaintenanceViewModel.historyList.observe(viewLifecycleOwner) { vehicleDetailList ->
            maintenanceVehicleAdapter.setItemList(vehicleDetailList)
        }
    }

    private fun search() {
        lifecycleScope.launch {
            historyMaintenanceViewModel.isLoading.value = true
            withContext(Dispatchers.IO) {
                val vehicleDetailList =
                    historyMaintenanceViewModel.historyList.value ?: mutableListOf()
                val vehicleDetailListSearch = vehicleDetailList.filter { vehicleDetail ->
                    val stringFilter = listOf(
                        vehicleDetail.vehicleId,
                        vehicleDetail.status ?: "",
                        vehicleDetail.reason ?: "",
                        vehicleDetail.solution ?: "",
                        vehicleDetail.supervisor ?: "",
                        vehicleDetail.note ?: ""
                    )
                    stringFilter.forEach { string ->
                        if (string.isNotEmpty() && string.lowercase()
                                .contains(binding.txtSearch.text.toString().lowercase())
                        ) {
                            return@filter true
                        }
                    }
                    false
                }.toMutableList()
                withContext(Dispatchers.Main) {
                    maintenanceVehicleAdapter.setItemList(vehicleDetailListSearch)
                    historyMaintenanceViewModel.isLoading.value = false
                }
            }
        }
    }

    private fun filter() {
        lifecycleScope.launch {
            historyMaintenanceViewModel.isLoading.value = true
            withContext(Dispatchers.IO) {
                val vehicleDetailList =
                    historyMaintenanceViewModel.historyList.value ?: mutableListOf()
                val vehicleDetailListSearch = vehicleDetailList.filter { vehicleDetail ->
                    val stringFilter = listOf(
                        vehicleDetail.vehicleId,
                        vehicleDetail.status ?: "",
                        vehicleDetail.reason ?: "",
                        vehicleDetail.solution ?: "",
                        vehicleDetail.supervisor ?: "",
                        vehicleDetail.note ?: ""
                    )
                    stringFilter.forEach { string ->
                        if (string.isNotEmpty() && string.lowercase()
                                .contains(binding.txtSearch.text.toString().lowercase())
                        ) {
                            return@filter true
                        }
                    }
                    false
                }.toMutableList()
                val vehicleDetailListFilter = vehicleDetailListSearch.filter { vehicleDetail ->
                    DateFunction.isDateInRange(
                        dateToCheck = DateFunction.formatDate(
                            dateString = vehicleDetail.createdAt ?: "",
                            dateStringFormat = Constants.FORMAT1,
                            formatType = Constants.FORMAT2
                        ),
                        format = Constants.FORMAT2,
                        startDate = binding.dateStart.text.toString(),
                        endDate = binding.dateEnd.text.toString()
                    )
                }.toMutableList()
                withContext(Dispatchers.Main) {
                    maintenanceVehicleAdapter.setItemList(vehicleDetailListFilter)
                    historyMaintenanceViewModel.isLoading.value = false
                }
            }
        }
    }

    private fun resetAll() {
        binding.txtSearch.setText("")
        if (binding.txtSearch.text.isEmpty()) {
            binding.dateStart.setText("")
            binding.dateEnd.setText("")
        }
    }

}