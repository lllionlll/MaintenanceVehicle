package io.maintenancevehicle.data.source.remote

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

sealed interface ApiResult<T : Any>

class ApiSuccess<T : Any>(val data: T) : ApiResult<T>
class ApiError<T : Any>(val code: Int, val message: String?) : ApiResult<T>
class ApiException<T : Any>(val e: Throwable) : ApiResult<T>

fun <T : Any> handleFirebaseTask(
    task: Task<T>
): ApiResult<T> {
    return try {
        val result = Tasks.await(task)
        ApiSuccess(result)
    } catch (e: Exception) {
        ApiException(e)
    }
}