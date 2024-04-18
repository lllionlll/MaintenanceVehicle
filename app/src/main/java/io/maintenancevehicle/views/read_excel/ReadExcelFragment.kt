package io.maintenancevehicle.views.read_excel

import android.annotation.SuppressLint
import android.net.Uri
import android.view.MotionEvent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentReadExcelBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.utils.hideKeyboard
import io.maintenancevehicle.views.maintenance_vehicle.MaintenanceVehicleAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ReadExcelFragment : BaseFragment<FragmentReadExcelBinding>(
    FragmentReadExcelBinding::inflate
) {
    private val openFileLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                readExcelViewModel.readExcel(requireContext(), uri)
            }
        }

    private val readExcelViewModel by viewModels<ReadExcelViewModel>()
    private var maintenanceVehicleAdapter = MaintenanceVehicleAdapter()
    private var isScroll = false

    override fun initView() {
        super.initView()
        binding.rcVehicle.adapter = maintenanceVehicleAdapter
        binding.rcVehicle.itemAnimator = null
        binding.txtSearch.addTextChangedListener {
            if (binding.txtSearch.text.isEmpty()) {
                binding.btnSearch.text = "Reset"
            } else {
                binding.btnSearch.text = "Tìm"
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun handleEvent() {
        super.handleEvent()

        binding.btnOpenFile.setOnClickListener {
            binding.root.hideKeyboard()
            openFileLauncher.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        }

        binding.btnBack.setOnClickListener {
            binding.root.hideKeyboard()
            ReadExcelRoute.backScreen(this)
        }

        binding.btnControl.setOnClickListener {
            binding.root.hideKeyboard()
            binding.layoutControl.isVisible = !binding.layoutControl.isVisible
        }

        binding.btnSearch.setOnClickListener {
            binding.root.hideKeyboard()
            readExcelViewModel.isLoading.value = true
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val vehicleDetailList =
                        readExcelViewModel.vehicleDetailList.value ?: mutableListOf()
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
                    }
                    binding.txtSearch.setText("")
                    if (binding.txtSearch.text.isEmpty()) {
                        binding.dateStart.setText("")
                        binding.dateEnd.setText("")
                    }
                }
            }
            readExcelViewModel.isLoading.value = false
        }

        binding.btnFilter.setOnClickListener {
            binding.root.hideKeyboard()
            readExcelViewModel.isLoading.value = true
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val vehicleDetailList =
                        readExcelViewModel.vehicleDetailList.value ?: mutableListOf()
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
                    }
                }
            }
            readExcelViewModel.isLoading.value = false
        }

        binding.rcVehicle.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {

                MotionEvent.ACTION_MOVE -> {
                    isScroll = true
                }

                MotionEvent.ACTION_UP -> {
                    if (!isScroll) {
                        binding.root.hideKeyboard()
                    }
                    isScroll = false
                }

                else -> {

                }
            }
            false
        }

    }

    override fun observerData() {
        super.observerData()

        readExcelViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        readExcelViewModel.vehicleDetailList.observe(viewLifecycleOwner) { vehicleDetailList ->
            maintenanceVehicleAdapter.setItemList(vehicleDetailList)
        }
    }
}