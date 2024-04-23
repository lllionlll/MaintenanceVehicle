package io.maintenancevehicle.views.maintenance_vehicle_view.history_maintenance

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.VehicleDetail
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.utils.ExcelFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryMaintenanceViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    val vehicleDetailList = MutableLiveData<MutableList<VehicleDetail>>()

    val isLoading = MutableLiveData<Boolean>()

    private val vehicleDetailTitle = listOf<Any>(
        "Mã thiết bị",
        "Trạng thái",
        "Nguyên nhân",
        "Nội dung xử lý",
        "Người giám sát",
        "Ngày tạo",
        "Ghi chú"
    )

    fun getVehicles() {
        viewModelScope.launch {
            try {
                isLoading.value = true
//                vehicleDetailList.value = withContext(Dispatchers.Default) {
//                    maintenanceVehicleRepository.getVehicles()
//                }.toMutableList()
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun exportExcel(
        context: Context,
        vehicleDetailList: MutableList<VehicleDetail>,
        filePath: String
    ) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.Default) {
                    val data = mutableListOf(
                        vehicleDetailTitle
                    )

                    vehicleDetailList.forEach { vehicleDetail ->
                        data.add(
                            mutableListOf(
                                vehicleDetail.id,
                                vehicleDetail.status ?: "",
                                vehicleDetail.reason ?: "",
                                vehicleDetail.solution ?: "",
                                vehicleDetail.supervisor ?: "",
                                vehicleDetail.createdAt ?: "",
                                vehicleDetail.note ?: ""
                            )
                        )
                    }
                    ExcelFunction.exportToExcel(data, filePath)
                }
                Toast.makeText(
                    context,
                    "Lưu thành công!",
                    Toast.LENGTH_SHORT,
                ).show()
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                Toast.makeText(
                    context,
                    "Lưu thất bại!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}