package io.maintenancevehicle.views.customer.customer_edit

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
class CustomerEditViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isEditSuccess = MutableLiveData<Boolean>()
    val customer = MutableLiveData<Customer>()

    fun editCustomer(
        context: Context,
        customerEdit: Customer
    ) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                customerEdit.customerId = customer.value?.customerId ?: ""
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.saveCustomers(
                        listOf(customerEdit)
                    )
                }
                isEditSuccess.value = true
                ToastFunction.showMessage(context, "Sửa thành công!")
                isLoading.value = false
            } catch (e: Exception) {
                ToastFunction.showMessage(context, "Sửa thất bại!")
            }
        }
    }

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
}