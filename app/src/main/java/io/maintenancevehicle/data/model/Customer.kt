package io.maintenancevehicle.data.model

import com.google.firebase.firestore.PropertyName
import java.util.UUID

data class Customer(
    @set:PropertyName("id") @get:PropertyName("id") var id: String = UUID.randomUUID().toString(),
    @set:PropertyName("name") @get:PropertyName("name") var name: String? = null,
    @set:PropertyName("birthday") @get:PropertyName("birthday") var birthday: String? = null,
    @set:PropertyName("gender") @get:PropertyName("gender") var gender: String? = null,
    @set:PropertyName("home_town") @get:PropertyName("home_town") var homeTown: String? = null,
    @set:PropertyName("phone_number") @get:PropertyName("phone_number") var phoneNumber: String? = null,
    @set:PropertyName("image_url") @get:PropertyName("image_url") var imageUrl: String? = null,
    @set:PropertyName("note") @get:PropertyName("note") var note: String? = null,
    @set:PropertyName("created_at") @get:PropertyName("created_at") var createdAt: String? = null,
    @set:PropertyName("updated_at") @get:PropertyName("updated_at") var updatedAt: String? = null
)