package io.maintenancevehicle.data.repository

import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.data.model.User
import io.maintenancevehicle.data.model.History
import io.maintenancevehicle.data.model.Vehicle
import io.maintenancevehicle.data.model.Widget
import io.maintenancevehicle.data.source.local.MaintenanceVehicleDataSource
import javax.inject.Inject

class MaintenanceVehicleRepository @Inject constructor(
    private val maintenanceVehicleDataSource: MaintenanceVehicleDataSource
) {

    // User
    suspend fun saveUser(userList: List<User>) {
        maintenanceVehicleDataSource.saveUsers(userList)
    }

    suspend fun getUser(userName: String, password: String): User? {
        return maintenanceVehicleDataSource.getUsers(userName, password)
    }

    suspend fun deleteUser() {
        maintenanceVehicleDataSource.deleteUsers()
    }

    // Customer
    suspend fun saveCustomers(customerList: List<Customer>) {
        maintenanceVehicleDataSource.saveCustomers(customerList)
    }

    suspend fun getCustomers(): List<Customer> {
        return maintenanceVehicleDataSource.getCustomers()
    }

    suspend fun getCustomerById(customerId: String): Customer? {
        return maintenanceVehicleDataSource.getCustomerById(customerId)
    }

    suspend fun deleteCustomers(customerIdList: List<String>) {
        return maintenanceVehicleDataSource.deleteCustomers(customerIdList)
    }

    // History
    suspend fun saveHistories(historyList: List<History>) {
        maintenanceVehicleDataSource.saveHistories(historyList)
    }

    suspend fun getHistories(): List<History> {
        return maintenanceVehicleDataSource.getHistories()
    }

    suspend fun deleteHistories(vehicleIdList: List<String>) {
        return maintenanceVehicleDataSource.deleteHistories(vehicleIdList)
    }

    // Service

    suspend fun saveServices(serviceList: List<Service>) {
        maintenanceVehicleDataSource.saveServices(serviceList)
    }

    suspend fun getServices(): List<Service> {
        return maintenanceVehicleDataSource.getServices()
    }

    suspend fun getServiceById(serviceId: String): Service? {
        return maintenanceVehicleDataSource.getServiceById(serviceId)
    }

    suspend fun deleteServices(serviceIdList: List<String>) {
        return maintenanceVehicleDataSource.deleteServices(serviceIdList)
    }

    // Vehicle

    suspend fun saveVehicles(vehicleList: List<Vehicle>) {
        maintenanceVehicleDataSource.saveVehicles(vehicleList)
    }

    suspend fun getVehicles(): List<Vehicle> {
        return maintenanceVehicleDataSource.getVehicles()
    }

    suspend fun getVehicleById(vehicleId: String): Vehicle? {
        return maintenanceVehicleDataSource.getVehicleById(vehicleId)
    }

    suspend fun deleteVehicles(vehicleIdList: List<String>) {
        return maintenanceVehicleDataSource.deleteVehicles(vehicleIdList)
    }

    // Widget

    suspend fun saveWidgets(widgetList: List<Widget>) {
        maintenanceVehicleDataSource.saveWidgets(widgetList)
    }

    suspend fun getWidgets(): List<Widget> {
        return maintenanceVehicleDataSource.getWidgets()
    }

    suspend fun getWidgetById(widgetId: String): Widget? {
        return maintenanceVehicleDataSource.getWidgetById(widgetId)
    }

    suspend fun deleteWidgets(widgetIdList: List<String>) {
        return maintenanceVehicleDataSource.deleteWidgets(widgetIdList)
    }

}