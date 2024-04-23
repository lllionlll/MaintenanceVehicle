package io.maintenancevehicle.data.model

import com.google.firebase.firestore.PropertyName
import java.util.UUID

class Service(
    @set:PropertyName("id") @get:PropertyName("id") var id: String = UUID.randomUUID().toString(),
    @set:PropertyName("name") @get:PropertyName("name") var name: String? = null,
    @set:PropertyName("image_url") @get:PropertyName("image_url") var image: String? = null
)