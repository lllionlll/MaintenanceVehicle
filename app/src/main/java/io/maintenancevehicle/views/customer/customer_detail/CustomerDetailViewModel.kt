package io.maintenancevehicle.views.customer.customer_detail

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
import javax.inject.Inject

@HiltViewModel
class CustomerDetailViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val customer = MutableLiveData<Customer>()

    fun getCustomerDetail(customerId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val getCustomerListResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getDetailById<Customer>(
                        ref = "customers",
                        id = customerId
                    )
                }
                if (getCustomerListResult is DataResult.Success) {
                    val unitListData = getCustomerListResult.data
                    customer.value = unitListData
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun deleteCustomer(customerId: String) {
        viewModelScope.launch {
            try {
                val deleteCustomerResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.deleteData(
                        "customers",
                        id = customerId
                    )
                }
                if (deleteCustomerResult is DataResult.Success) {

                }
            } catch (e: Exception) {

            }
        }
    }

}