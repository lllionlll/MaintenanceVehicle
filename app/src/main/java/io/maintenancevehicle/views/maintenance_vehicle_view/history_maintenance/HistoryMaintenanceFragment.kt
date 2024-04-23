package io.maintenancevehicle.views.maintenance_vehicle_view.history_maintenance

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentHistoryMaintenanceBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.maintenance_vehicle_view.maintenance_vehicle.MaintenanceVehicleAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HistoryMaintenanceFragment : BaseFragment<FragmentHistoryMaintenanceBinding>(
    FragmentHistoryMaintenanceBinding::inflate
) {

    private val historyMaintenanceViewModel by viewModels<HistoryMaintenanceViewModel>()
    private var maintenanceVehicleAdapter = MaintenanceVehicleAdapter()

    private val selectDirectoryLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
            uri?.let {
                val pickedDirectory = DocumentFile.fromTreeUri(requireContext(), uri)
                val directoryPath = pickedDirectory?.uri?.path ?: ""
                historyMaintenanceViewModel.exportExcel(
                    requireContext(),
                    maintenanceVehicleAdapter.itemList,
                    directoryPath
                )
            }
        }

    override fun initView() {
        super.initView()
        historyMaintenanceViewModel.getVehicles()
        binding.rcVehicleDetail.adapter = maintenanceVehicleAdapter
        binding.rcVehicleDetail.itemAnimator = null
        binding.txtSearch.addTextChangedListener {
            if (binding.txtSearch.text.isEmpty()) {
                binding.btnSearch.text = "Reset"
            } else {
                binding.btnSearch.text = "Tìm"
            }
        }
    }

    override fun handleEvent() {
        super.handleEvent()

        binding.btnBack.setOnClickListener {
            HistoryMaintenanceRoute.backScreen(this)
        }

        binding.btnControl.setOnClickListener {
            binding.layoutControl.isVisible = !binding.layoutControl.isVisible
        }

        binding.btnSearch.setOnClickListener {
            lifecycleScope.launch {
                historyMaintenanceViewModel.isLoading.value = true
                withContext(Dispatchers.IO) {
                    val vehicleDetailList =
                        historyMaintenanceViewModel.vehicleDetailList.value ?: mutableListOf()
                    val vehicleDetailListFilter = vehicleDetailList.filter { vehicleDetail ->
                        val stringFilter = listOf(
                            vehicleDetail.id,
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
                        maintenanceVehicleAdapter.setItemList(vehicleDetailListFilter)
                        historyMaintenanceViewModel.isLoading.value = false
                        binding.txtSearch.setText("")
                        if (binding.txtSearch.text.isEmpty()) {
                            binding.dateStart.setText("")
                            binding.dateEnd.setText("")
                        }
                    }
                }
            }
        }

        binding.btnSave.setOnClickListener {
            selectDirectoryLauncher.launch(null)
        }

        binding.btnFilter.setOnClickListener {
            lifecycleScope.launch {
                historyMaintenanceViewModel.isLoading.value = true
                withContext(Dispatchers.IO) {
                    val vehicleDetailList =
                        historyMaintenanceViewModel.vehicleDetailList.value ?: mutableListOf()
                    val vehicleDetailListFilter = vehicleDetailList.filter { vehicleDetail ->
                        DateFunction.isDateInRange(
                            vehicleDetail.createdAt ?: "",
                            "dd/MM/yyyy",
                            binding.dateStart.text.toString(),
                            binding.dateEnd.text.toString()
                        )
                    }.toMutableList()
                    withContext(Dispatchers.Main) {
                        maintenanceVehicleAdapter.setItemList(vehicleDetailListFilter)
                        historyMaintenanceViewModel.isLoading.value = false
                    }
                }
            }
        }

        binding.dateStart.setOnClickListener {
            DateFunction.showDatePicker(requireContext()) { date ->
                binding.dateStart.setText(
                    DateFunction.formatDate(
                        date,
                        "dd/MM/yyyy"
                    )
                )
            }
        }

        binding.dateEnd.setOnClickListener {
            DateFunction.showDatePicker(requireContext()) { date ->
                binding.dateEnd.setText(
                    DateFunction.formatDate(
                        date,
                        "dd/MM/yyyy"
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

        historyMaintenanceViewModel.vehicleDetailList.observe(viewLifecycleOwner) { vehicleDetailList ->
            maintenanceVehicleAdapter.setItemList(vehicleDetailList)
        }
    }

}