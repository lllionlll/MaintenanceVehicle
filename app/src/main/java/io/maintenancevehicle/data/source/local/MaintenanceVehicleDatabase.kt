package io.maintenancevehicle.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.data.model.User
import io.maintenancevehicle.data.model.History
import io.maintenancevehicle.data.model.Vehicle
import io.maintenancevehicle.data.model.Widget

@Database(
    entities = [
        History::class,
        Customer::class,
        User::class,
        Service::class,
        Widget::class,
        Vehicle::class
    ],
    version = 1,
    exportSchema = false
)

abstract class MaintenanceVehicleDatabase : RoomDatabase() {

    abstract fun maintenanceVehicleDao(): MaintenanceVehicleDao

}