package io.maintenancevehicle.data

class ApiResponse<T : Any>(val error: Boolean, val data: T, val message: String?)
