package io.maintenancevehicle.views.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val loginFlag = MutableLiveData<Boolean>()

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val user = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getUser(
                        userName,
                        password
                    )
                }
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                loginFlag.value = user != null
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }
}