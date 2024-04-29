package io.maintenancevehicle.views.customer.customer_add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.DataResult
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerAddViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isAdd = MutableLiveData<Boolean>()

    fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val addCustomerResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.addData(
                        "customers",
                        data = customer,
                        id = customer.id
                    )
                }
                if (addCustomerResult is DataResult.Success) {
                    isAdd.value = true
                }
                isLoading.value = false
            } catch (e: Exception) {

            }
        }
    }

}