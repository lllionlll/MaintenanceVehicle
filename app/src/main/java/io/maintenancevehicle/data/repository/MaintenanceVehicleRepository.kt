package io.maintenancevehicle.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.maintenancevehicle.data.ApiException
import io.maintenancevehicle.data.ApiSuccess
import io.maintenancevehicle.data.DataResult
import io.maintenancevehicle.data.handleFirebaseTask
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class MaintenanceVehicleRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) {

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified T> getList(ref: String): DataResult<MutableList<T>> {
        return try {
            val collectionRef = firebaseFirestore.collection(ref)
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
            val getList = firebaseFirestore.collection(ref)
                .whereEqualTo("id", id)
                .get()

            when (val handleFirebaseResult = handleFirebaseTask(getList)) {

                is ApiSuccess -> {
                    val list = handleFirebaseResult.data.toObjects(T::class.java)
                    DataResult.Success(list.first())
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
            val collectionRef = firebaseFirestore.collection(ref)
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
            val collectionRef = firebaseFirestore.collection(ref)
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

    fun uploadImage(context: Context, uri: Uri): DataResult<String> {
        return try {
            val newUri = compressImage(context, uri)
            val ref = firebaseStorage.reference.child("images/${uri.lastPathSegment}")
            val uploadTask = ref.putBytes(newUri)
            val urlTask = uploadTask.continueWithTask {
                ref.downloadUrl
            }

            when (val handleFirebaseResult = handleFirebaseTask(urlTask)) {
                is ApiSuccess -> {
                    val url = handleFirebaseResult.data
                    DataResult.Success(url.toString())
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

    private fun compressImage(context: Context, uri: Uri): ByteArray {
        val input = context.contentResolver.openInputStream(uri)
        val options = BitmapFactory.Options()
        options.inSampleSize = 4
        val bitmap = BitmapFactory.decodeStream(input, null, options)
        input?.close()
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val byteArray = outputStream.toByteArray()
        outputStream.close()
        return byteArray
    }

}