package io.maintenancevehicle.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import io.maintenancevehicle.data.model.VehicleDetail
import io.maintenancevehicle.data.source.local.MaintenanceVehicleDataSource
import io.maintenancevehicle.data.source.remote.ApiException
import io.maintenancevehicle.data.source.remote.ApiSuccess
import io.maintenancevehicle.data.source.remote.DataResult
import io.maintenancevehicle.data.source.remote.handleFirebaseTask
import javax.inject.Inject

class MaintenanceVehicleRepository @Inject constructor(
    private val maintenanceVehicleDataSource: MaintenanceVehicleDataSource
) {

    private val fb = FirebaseFirestore.getInstance()

    suspend fun saveVehicles(vehicleDetailList: List<VehicleDetail>) {
        maintenanceVehicleDataSource.saveVehicles(vehicleDetailList)
    }

    suspend fun getVehicles(): List<VehicleDetail> {
        return maintenanceVehicleDataSource.getVehicles()
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified T> getList(ref: String): DataResult<MutableList<T>> {
        return try {
            val collectionRef = fb.collection(ref)
            val querySnapshot = collectionRef.get()

            when (val handleFirebaseResult = handleFirebaseTask(querySnapshot)) {
                is ApiSuccess -> {
                    val dataList = handleFirebaseResult.data.toObjects(T::class.java)
                    DataResult.Success(dataList)
                }

                is ApiException -> {
                    val message = handleFirebaseResult.e.message
                    DataResult.Error(message = message)
                }

                else -> {
                    DataResult.Error()
                }
            }
        } catch (e: Exception) {
            DataResult.Error(message = e.message)
        }
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified T> getDetailById(
        ref: String,
        id: String
    ): DataResult<T> {
        return try {
            val getVocabularyList = fb.collection(ref)
                .whereEqualTo("id", id)
                .get()


            when (val handleFirebaseResult = handleFirebaseTask(getVocabularyList)) {

                is ApiSuccess -> {
                    val vocabularyList = handleFirebaseResult.data.toObjects(T::class.java)
                    DataResult.Success(vocabularyList.first())
                }

                is ApiException -> {
                    val message = handleFirebaseResult.e.message
                    DataResult.Error(message = message)
                }

                else -> {
                    DataResult.Error()
                }
            }
        } catch (e: Exception) {
            DataResult.Error(message = e.message)
        }
    }

    fun addData(ref: String, data: Any, id: String): DataResult<Boolean> {
        return try {
            val collectionRef = fb.collection(ref)
            val querySnapshot = collectionRef.document(
                id
            ).set(data)

            when (val handleFirebaseResult = handleFirebaseTask(querySnapshot)) {
                is ApiSuccess -> {
                    DataResult.Success(true)
                }

                is ApiException -> {
                    val message = handleFirebaseResult.e.message
                    DataResult.Error(message = message)
                }

                else -> {
                    DataResult.Error()
                }
            }
        } catch (e: Exception) {
            DataResult.Error(message = e.message)
        }
    }

    fun deleteData(ref: String, id: String) : DataResult<Any> {
        return try {
            val collectionRef = fb.collection(ref)
            val querySnapshot = collectionRef.document(
                id
            ).delete()

            when (val handleFirebaseResult = handleFirebaseTask(querySnapshot)) {
                is ApiSuccess -> {
                    DataResult.Success(true)
                }

                is ApiException -> {
                    val message = handleFirebaseResult.e.message
                    DataResult.Error(message = message)
                }

                else -> {
                    DataResult.Error()
                }
            }
        } catch (e: Exception) {
            DataResult.Error(message = e.message)
        }
    }
}