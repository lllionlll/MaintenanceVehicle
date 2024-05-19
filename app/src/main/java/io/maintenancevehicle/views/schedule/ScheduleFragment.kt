package io.maintenancevehicle.views.schedule

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentScheduleBinding
import io.maintenancevehicle.utils.Constants
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.history.maintenance_vehicle.MaintenanceVehicleAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ScheduleFragment : BaseFragment<FragmentScheduleBinding>(
    FragmentScheduleBinding::inflate
) {
    private val openFileLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                scheduleViewModel.readExcel(requireContext(), uri)
            }
        }

    private val scheduleViewModel by viewModels<ScheduleViewModel>()
    private var maintenanceVehicleAdapter = MaintenanceVehicleAdapter()

    override fun initView() {
        super.initView()
        binding.rcVehicle.adapter = maintenanceVehicleAdapter
        binding.rcVehicle.itemAnimator = null
        setFragmentResultListener(Constants.VEHICLE_ID) { _, result ->
            val vehicleId = result.getString(Constants.VEHICLE_ID)
            binding.txtSearch.setText(vehicleId ?: "")
        }
    }

    override fun handleEvent() {
        super.handleEvent()

        binding.btnOpenFile.setOnClickListener {
            openFileLauncher.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        }

        binding.btnBack.setOnClickListener {
            ScheduleRoute.backScreen(this)
        }

        binding.btnControl.setOnClickListener {
            binding.layoutControl.isVisible = !binding.layoutControl.isVisible
        }

        binding.btnSearch.setOnClickListener {
            search()
        }

        binding.btnSort.setOnClickListener {
            maintenanceVehicleAdapter.reverseList()
        }

        binding.btnReset.setOnClickListener {
            resetAll()
            maintenanceVehicleAdapter.setItemList(
                itemList = scheduleViewModel.historyList.value
                    ?: mutableListOf()
            )
        }

        binding.btnFilter.setOnClickListener {
            filter()
        }

        binding.qrCodeScanner.setOnClickListener {
            ScheduleRoute.goToQrCodeScanner(this)
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

        scheduleViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        scheduleViewModel.historyList.observe(viewLifecycleOwner) { vehicleDetailList ->
            maintenanceVehicleAdapter.setItemList(vehicleDetailList)
        }
    }

    private fun search() {
        lifecycleScope.launch {
            scheduleViewModel.isLoading.value = true
            withContext(Dispatchers.IO) {
                val vehicleDetailList =
                    scheduleViewModel.historyList.value ?: mutableListOf()
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
                    scheduleViewModel.isLoading.value = false
                }
            }
        }
    }

    private fun filter() {
        lifecycleScope.launch {
            scheduleViewModel.isLoading.value = true
            withContext(Dispatchers.IO) {
                val vehicleDetailList =
                    scheduleViewModel.historyList.value ?: mutableListOf()
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
                    scheduleViewModel.isLoading.value = false
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