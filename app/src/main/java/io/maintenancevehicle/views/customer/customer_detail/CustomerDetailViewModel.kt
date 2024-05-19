package io.maintenancevehicle.views.customer.customer_detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.utils.ToastFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerDetailViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val customer = MutableLiveData<Customer>()
    val isDeleteSuccess = MutableLiveData<Boolean>()

    fun getCustomerDetail(customerId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                customer.value = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getCustomerById(
                        customerId
                    )
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun deleteCustomer(context: Context, customerId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.deleteCustomers(
                        listOf(customerId)
                    )
                }
                ToastFunction.showMessage(context, "Xóa thành công!")
                isDeleteSuccess.value = true
                isLoading.value = false
            } catch (e: Exception) {
                ToastFunction.showMessage(context, "Xóa thất bại!")
                isLoading.value = false
            }
        }
    }

}