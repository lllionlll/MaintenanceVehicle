package io.maintenancevehicle.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.maintenancevehicle.data.model.VehicleDetail

@Dao
interface MaintenanceVehicleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVehicles(vehicleDetailList: List<VehicleDetail>)

    @Query("SELECT * FROM vehicles")
    suspend fun getVehicles(): List<VehicleDetail>

}