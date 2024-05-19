package io.maintenancevehicle.views.widget

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.data.model.Widget
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.utils.ToastFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WidgetViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val widgetList = MutableLiveData<MutableList<Widget>>()
    val isAddSuccess = MutableLiveData<Boolean>()
    val isDeleteSuccess = MutableLiveData<Boolean>()
    val isEditSuccess = MutableLiveData<Boolean>()
    val widget = MutableLiveData<Widget>()

    fun editWidgets(context: Context, widget: Widget) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.saveWidgets(listOf(widget))
                }
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                ToastFunction.showMessage(context, "Sửa thành công!")
                isEditSuccess.value = true
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                ToastFunction.showMessage(context, "Sửa thất bại!")
            }
        }
    }

    fun saveWidgets(context: Context, widget: Widget) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.saveWidgets(listOf(widget))
                }
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                ToastFunction.showMessage(context, "Thêm thành công!")
                isAddSuccess.value = true
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                ToastFunction.showMessage(context, "Thêm thất bại!")
            }
        }
    }

    fun getWidgets() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                widgetList.value = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getWidgets()
                }.toMutableList()
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun getWidgetDetail(widgetId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                widget.value = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getWidgetById(widgetId)
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun deleteWidget(context: Context, widgetId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.deleteWidgets(
                        listOf(widgetId)
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