package io.maintenancevehicle.data.model

import com.google.firebase.firestore.PropertyName
import java.util.UUID

class Service(
    @set:PropertyName("id") @get:PropertyName("id") var id: String = UUID.randomUUID().toString()
)