package io.maintenancevehicle.views.maintenance_vehicle_view.maintenance_vehicle

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.VehicleDetail
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MaintenanceVehicleViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

    fun saveVehicles(context: Context, vehicleDetailList: List<VehicleDetail>) {
        viewModelScope.launch {
            try {

//                Toast.makeText(
//                    context,
//                    "Lưu thành công!",
//                    Toast.LENGTH_SHORT
//                ).show()
            } catch (e: Exception) {
                e.message
                Toast.makeText(
                    context,
                    "Lưu thất bại!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}