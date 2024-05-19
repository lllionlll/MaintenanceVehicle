package io.maintenancevehicle.views.history.history_maintenance

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.History
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.ExcelFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryMaintenanceViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    val historyList = MutableLiveData<MutableList<History>>()

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
                val maintenanceVehicleList = withContext(Dispatchers.Default) {
                    maintenanceVehicleRepository.getHistories()
                }
                maintenanceVehicleList.toMutableList()
                    .sortWith { vehicleMaintenanceDetail1, vehicleMaintenanceDetail2 ->
                        DateFunction.compareDates(
                            vehicleMaintenanceDetail1.createdAt ?: "",
                            vehicleMaintenanceDetail2.createdAt ?: ""
                        )
                    }
                historyList.value = maintenanceVehicleList.reversed().toMutableList()
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun deleteVehicles(
        context: Context,
        vehicleIdList: List<String>,
        deleteSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.deleteHistories(vehicleIdList)
                }
                Toast.makeText(
                    context,
                    "Xóa thành công!",
                    Toast.LENGTH_SHORT,
                ).show()
                deleteSuccess.invoke()
                isLoading.value = false
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Xóa thất bại!",
                    Toast.LENGTH_SHORT,
                ).show()
                isLoading.value = false
            }
        }
    }

    fun exportExcel(
        context: Context,
        fileName: String,
        historyList: MutableList<History>,
        uri: Uri
    ) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.Default) {
                    val data = mutableListOf(
                        vehicleDetailTitle
                    )

                    historyList.forEach { vehicleDetail ->
                        data.add(
                            mutableListOf(
                                vehicleDetail.vehicleId,
                                vehicleDetail.status ?: "",
                                vehicleDetail.reason ?: "",
                                vehicleDetail.solution ?: "",
                                vehicleDetail.supervisor ?: "",
                                vehicleDetail.createdAt ?: "",
                                vehicleDetail.note ?: ""
                            )
                        )
                    }
                    ExcelFunction.exportToExcel(
                        context,
                        data = data,
                        fileName = fileName,
                        uri = uri
                    )
                }
                Toast.makeText(
                    context,
                    "Xuất excel thành công!",
                    Toast.LENGTH_SHORT,
                ).show()
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                Toast.makeText(
                    context,
                    "Xuất excel thất bại!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}