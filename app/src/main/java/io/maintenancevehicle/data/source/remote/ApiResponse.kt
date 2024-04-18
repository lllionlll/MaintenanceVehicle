package io.maintenancevehicle.data.source.remote

class ApiResponse<T : Any>(val error: Boolean, val data: T, val message: String?)
