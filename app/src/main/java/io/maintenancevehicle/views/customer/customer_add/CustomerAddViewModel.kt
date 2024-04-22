package io.maintenancevehicle.views.customer.customer_add

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

    fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                val addCustomerResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.addData(
                        "customers",
                        data = customer,
                        id = customer.id
                    )
                }
                if (addCustomerResult is DataResult.Success) {

                }
            } catch (e: Exception) {

            }
        }
    }

}