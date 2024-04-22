package io.maintenancevehicle.views.customer.customer_add

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Customer
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CustomerAddViewModel"

@HiltViewModel
class CustomerAddViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    fun saveCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                customerRepository.saveCustomer<Customer>(
                    ref = "customers",
                    customer = customer
                )

                Log.e(TAG, "saveCustomer: ")
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }
    fun saveImage(customer: Customer) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                customerRepository.saveCustomer<Customer>(
                    ref = "customers",
                    customer = customer
                )
                Log.e(TAG, "saveCustomer: ")
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }
}