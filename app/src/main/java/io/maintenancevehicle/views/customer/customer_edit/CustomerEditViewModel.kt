package io.maintenancevehicle.views.customer.customer_edit

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.data.DataResult
import io.maintenancevehicle.utils.ToastFunction
import kotlinx.coroutines.Dispatchers
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
        uri: Uri?,
        customerEdit: Customer
    ) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                var url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRNuiBY5AGqRjC890IW9MQ5_p5NYSysbFSBfs8LIcl8DSYx7sTngU8xpzyHuwitNfUmV4&usqp=CAU"

                if (uri != null) {
                    val uploadImageResult = withContext(Dispatchers.IO) {
                        maintenanceVehicleRepository.uploadImage(context, uri)
                    }
                    if (uploadImageResult is DataResult.Success) {
                        url = uploadImageResult.data
                    }
                    customerEdit.imageUrl = url
                }

                val addCustomerResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.addData(
                        "customers",
                        data = customerEdit,
                        id = customer.value?.id ?: ""
                    )
                }
                ToastFunction.showMessage(context, "Sửa thành công!")
                isEditSuccess.value = true
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
                val getCustomerDetailResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getDetailById<Customer>(
                        ref = "customers",
                        id = customerId
                    )
                }
                if (getCustomerDetailResult is DataResult.Success) {
                    val customerData = getCustomerDetailResult.data
                    customer.value = customerData
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }
}