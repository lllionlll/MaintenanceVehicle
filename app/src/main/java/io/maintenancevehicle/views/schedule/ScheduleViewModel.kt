package io.maintenancevehicle.views.schedule

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.History
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.ExcelFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(

) : ViewModel() {

    val historyList = MutableLiveData<MutableList<History>>()

    val isLoading = MutableLiveData<Boolean>()

    fun readExcel(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val deviceDetailListData = withContext(Dispatchers.Default) {
                    ExcelFunction.readExcel(context, uri)
                }
                val deviceList = mutableListOf<History>()
                deviceDetailListData.forEach { deviceDetail ->
                    deviceList.add(
                        History(
                            vehicleId = deviceDetail[0].toString(),
                            status = deviceDetail[1].toString(),
                            reason = deviceDetail[2].toString(),
                            solution = deviceDetail[3].toString(),
                            supervisor = deviceDetail[4].toString(),
                            createdAt = deviceDetail[5].toString(),
                            note = deviceDetail[6].toString()
                        )
                    )
                }
                deviceList.toMutableList()
                    .sortWith { deviceMaintenanceDetail1, deviceMaintenanceDetail2 ->
                        DateFunction.compareDates(
                            deviceMaintenanceDetail1.createdAt ?: "",
                            deviceMaintenanceDetail2.createdAt ?: ""
                        )
                    }
                historyList.value = deviceList.toMutableList()
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

}