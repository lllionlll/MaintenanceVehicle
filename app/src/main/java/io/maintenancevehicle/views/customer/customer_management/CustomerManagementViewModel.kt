package io.maintenancevehicle.views.customer.customer_management

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.data.source.remote.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CustomerManagementViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val customerList = MutableLiveData<MutableList<Customer>>()

    fun getCustomers() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val getCustomerListResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getList<Customer>("customers")
                }
                if (getCustomerListResult is DataResult.Success) {
                    val customerListData = getCustomerListResult.data
                    customerList.value = customerListData
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }
}