package io.maintenancevehicle.views.history.maintenance_vehicle

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.History
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MaintenanceVehicleViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

    fun saveVehicles(context: Context, historyList: List<History>) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.saveHistories(historyList)
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Lưu thất bại!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}