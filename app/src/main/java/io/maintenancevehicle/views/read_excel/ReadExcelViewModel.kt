package io.maintenancevehicle.views.read_excel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.VehicleDetail
import io.maintenancevehicle.utils.ExcelFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ReadExcelViewModel @Inject constructor(

) : ViewModel() {

    val vehicleDetailList = MutableLiveData<MutableList<VehicleDetail>>()

    val isLoading = MutableLiveData<Boolean>()

    fun readExcel(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.Default) {
                    val vehicleDetailListData = ExcelFunction.readExcel(context, uri)
                    val vehicleList = mutableListOf<VehicleDetail>()
                    vehicleDetailListData.forEach { vehicleDetail ->
                        vehicleList.add(
                            VehicleDetail(
                                id = vehicleDetail[0].toString(),
                                status = vehicleDetail[1].toString(),
                                reason = vehicleDetail[2].toString(),
                                solution = vehicleDetail[3].toString(),
                                supervisor = vehicleDetail[4].toString(),
                                createdAt = vehicleDetail[5].toString(),
                                note = vehicleDetail[6].toString()
                            )
                        )
                    }
                    withContext(Dispatchers.Main) {
                        vehicleDetailList.value = vehicleList
                        isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

}