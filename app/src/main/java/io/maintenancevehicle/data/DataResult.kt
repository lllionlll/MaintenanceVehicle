package io.maintenancevehicle.data

sealed class DataResult<out R> {

    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val message: String? = null) : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$message]"
        }
    }
}