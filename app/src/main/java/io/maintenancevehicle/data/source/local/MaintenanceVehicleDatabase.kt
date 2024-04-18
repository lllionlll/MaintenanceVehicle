package io.maintenancevehicle.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.maintenancevehicle.data.model.VehicleDetail

@Database(
    entities = [VehicleDetail::class],
    version = 1,
    exportSchema = false
)

abstract class MaintenanceVehicleDatabase : RoomDatabase() {

    abstract fun maintenanceVehicleDao(): MaintenanceVehicleDao

}