package io.maintenancevehicle.data.source.local

import io.maintenancevehicle.data.model.VehicleDetail
import javax.inject.Inject

class MaintenanceVehicleDataSource @Inject constructor(
    private val maintenanceVehicleDao: MaintenanceVehicleDao
) {

    suspend fun saveVehicles(vehicleDetailList: List<VehicleDetail>) {
        maintenanceVehicleDao.saveVehicles(vehicleDetailList)
    }

    suspend fun getVehicles(): List<VehicleDetail> {
        return maintenanceVehicleDao.getVehicles()
    }

}