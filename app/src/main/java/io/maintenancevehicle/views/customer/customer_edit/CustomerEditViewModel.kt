package io.maintenancevehicle.views.customer.customer_edit

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
class CustomerEditViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    fun editCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                val editCustomerResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.addData(
                        "customers",
                        data = customer,
                        id = customer.id
                    )
                }
                if (editCustomerResult is DataResult.Success) {

                }
            } catch (e: Exception) {

            }
        }
    }
}