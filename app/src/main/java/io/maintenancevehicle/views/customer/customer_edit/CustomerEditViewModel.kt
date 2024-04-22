package io.maintenancevehicle.views.customer.customer_edit

import androidx.lifecycle.ViewModel
<<<<<<< HEAD
import dagger.hilt.android.lifecycle.HiltViewModel
=======
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.data.source.remote.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
>>>>>>> 17089a69e6a40bd6cff4e05dcfd1e92cd9851f40
import javax.inject.Inject

@HiltViewModel
class CustomerEditViewModel @Inject constructor(
<<<<<<< HEAD

) : ViewModel() {

=======
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
>>>>>>> 17089a69e6a40bd6cff4e05dcfd1e92cd9851f40
}