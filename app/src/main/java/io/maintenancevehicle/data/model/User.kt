package io.maintenancevehicle.data.model

import com.google.firebase.firestore.PropertyName

data class User(
    @set:PropertyName("id") @get:PropertyName("id") var id: String? = null,
    @set:PropertyName("user_name") @get:PropertyName("user_name") var userName: String? = null,
    @set:PropertyName("password") @get:PropertyName("password") var password: String? = null
)