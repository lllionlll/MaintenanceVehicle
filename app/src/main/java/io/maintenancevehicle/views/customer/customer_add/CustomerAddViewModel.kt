package io.maintenancevehicle.views.customer.customer_add

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.DataResult
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.utils.ToastFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerAddViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isAddSuccess = MutableLiveData<Boolean>()

    fun addCustomer(
        context: Context,
        uri: Uri?,
        customerEdit: Customer
    ) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                var url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRNuiBY5AGqRjC890IW9MQ5_p5NYSysbFSBfs8LIcl8DSYx7sTngU8xpzyHuwitNfUmV4&usqp=CAU"
                customerEdit.imageUrl = url
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
                        id = customerEdit.id
                    )
                }
                ToastFunction.showMessage(context, "Thêm thành công!")
                isAddSuccess.value = true
                isLoading.value = false
            } catch (e: Exception) {
                ToastFunction.showMessage(context, "Thêm thất bại!")
            }
        }
    }

}