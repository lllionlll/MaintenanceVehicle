package io.maintenancevehicle.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.data.model.User
import io.maintenancevehicle.data.model.History
import io.maintenancevehicle.data.model.Vehicle
import io.maintenancevehicle.data.model.Widget

@Dao
interface MaintenanceVehicleDao {

    // User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsers(vehicleDetailList: List<User>)

    @Query("SELECT * FROM users WHERE user_name IN (:userName) AND password IN (:password)")
    suspend fun getUsers(userName: String, password: String): User?

    @Query("DELETE FROM users")
    suspend fun deleteUsers()

    // History
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHistories(historyList: List<History>)

    @Query("SELECT * FROM histories")
    suspend fun getHistories(): List<History>

    @Query("DELETE FROM histories WHERE id IN (:historyIdList)")
    suspend fun deleteHistories(historyIdList: List<String>)

    // Customer
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCustomers(customerList: List<Customer>)

    @Query("SELECT * FROM customers")
    suspend fun getCustomers(): List<Customer>

    @Query("SELECT * FROM customers WHERE customer_id IN (:customerId) ")
    suspend fun getCustomerById(customerId: String): Customer?

    @Query("DELETE FROM customers WHERE customer_id IN (:customerIdList)")
    suspend fun deleteCustomers(customerIdList: List<String>)

    // Service

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveServices(serviceList: List<Service>)

    @Query("SELECT * FROM services")
    suspend fun getServices(): List<Service>

    @Query("SELECT * FROM services WHERE id IN (:serviceId) ")
    suspend fun getServiceById(serviceId: String): Service?

    @Query("DELETE FROM services WHERE id IN (:serviceIdList)")
    suspend fun deleteServices(serviceIdList: List<String>)

    // Vehicle

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVehicles(widgetList: List<Vehicle>)

    @Query("SELECT * FROM vehicles")
    suspend fun getVehicles(): List<Vehicle>

    @Query("SELECT * FROM vehicles WHERE vehicle_id IN (:vehicleId) ")
    suspend fun getVehicleById(vehicleId: String): Vehicle?

    @Query("DELETE FROM vehicles WHERE vehicle_id IN (:vehicleIdList)")
    suspend fun deleteVehicles(vehicleIdList: List<String>)

    // Widget

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWidgets(widgetList: List<Widget>)

    @Query("SELECT * FROM widgets")
    suspend fun getWidgets(): List<Widget>

    @Query("SELECT * FROM widgets WHERE widget_id IN (:widgetId) ")
    suspend fun getWidgetById(widgetId: String): Widget?

    @Query("DELETE FROM widgets WHERE widget_id IN (:widgetIdList)")
    suspend fun deleteWidgets(widgetIdList: List<String>)
}