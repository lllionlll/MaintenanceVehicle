package io.maintenancevehicle.data.source.local

import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.data.model.User
import io.maintenancevehicle.data.model.History
import io.maintenancevehicle.data.model.Vehicle
import io.maintenancevehicle.data.model.Widget
import javax.inject.Inject

class MaintenanceVehicleDataSource @Inject constructor(
    private val maintenanceVehicleDao: MaintenanceVehicleDao
) {

    // User
    suspend fun saveUsers(userList: List<User>) {
        maintenanceVehicleDao.saveUsers(userList)
    }

    suspend fun getUsers(userName: String, password: String): User? {
        return maintenanceVehicleDao.getUsers(userName, password)
    }

    suspend fun deleteUsers() {
        return maintenanceVehicleDao.deleteUsers()
    }

    // History
    suspend fun saveHistories(historyList: List<History>) {
        maintenanceVehicleDao.saveHistories(historyList)
    }

    suspend fun getHistories(): List<History> {
        return maintenanceVehicleDao.getHistories()
    }

    suspend fun deleteHistories(vehicleIdList: List<String>) {
        return maintenanceVehicleDao.deleteHistories(vehicleIdList)
    }

    // Customer
    suspend fun saveCustomers(customerList: List<Customer>) {
        maintenanceVehicleDao.saveCustomers(customerList)
    }

    suspend fun getCustomers(): List<Customer> {
        return maintenanceVehicleDao.getCustomers()
    }

    suspend fun getCustomerById(customerId: String): Customer? {
        return maintenanceVehicleDao.getCustomerById(customerId)
    }

    suspend fun deleteCustomers(customerIdList: List<String>) {
        return maintenanceVehicleDao.deleteCustomers(customerIdList)
    }

    // Service

    suspend fun saveServices(serviceList: List<Service>) {
        maintenanceVehicleDao.saveServices(serviceList)
    }

    suspend fun getServices(): List<Service> {
        return maintenanceVehicleDao.getServices()
    }

    suspend fun getServiceById(serviceId: String): Service? {
        return maintenanceVehicleDao.getServiceById(serviceId)
    }

    suspend fun deleteServices(serviceIdList: List<String>) {
        return maintenanceVehicleDao.deleteServices(serviceIdList)
    }

    // Vehicle

    suspend fun saveVehicles(historyList: List<Vehicle>) {
        maintenanceVehicleDao.saveVehicles(historyList)
    }

    suspend fun getVehicles(): List<Vehicle> {
        return maintenanceVehicleDao.getVehicles()
    }

    suspend fun deleteVehicles(vehicleIdList: List<String>) {
        return maintenanceVehicleDao.deleteVehicles(vehicleIdList)
    }

    suspend fun getVehicleById(vehicleId: String): Vehicle? {
        return maintenanceVehicleDao.getVehicleById(vehicleId)
    }

    // Widget

    suspend fun saveWidgets(widgetList: List<Widget>) {
        maintenanceVehicleDao.saveWidgets(widgetList)
    }

    suspend fun getWidgets(): List<Widget> {
        return maintenanceVehicleDao.getWidgets()
    }

    suspend fun getWidgetById(widgetId: String): Widget? {
        return maintenanceVehicleDao.getWidgetById(widgetId)
    }

    suspend fun deleteWidgets(widgetIdList: List<String>) {
        return maintenanceVehicleDao.deleteWidgets(widgetIdList)
    }

}