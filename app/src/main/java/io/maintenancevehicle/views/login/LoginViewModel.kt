package io.maintenancevehicle.views.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.User
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.data.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val loginFlag = MutableLiveData<Boolean>()

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val loginResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getDetailById<User>(
                        ref = "users",
                        id = userName + password
                    )
                }
                loginFlag.value = loginResult is DataResult.Success
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }
}