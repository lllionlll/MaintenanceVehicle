package io.maintenancevehicle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("customers")
data class Customer(
    @PrimaryKey @ColumnInfo("customer_id") var customerId: String = UUID.randomUUID().toString(),
    @ColumnInfo("name") var name: String? = null,
    @ColumnInfo("birthday") var birthday: String? = null,
    @ColumnInfo("gender") var gender: String? = null,
    @ColumnInfo("home_town") var homeTown: String? = null,
    @ColumnInfo("phone_number") var phoneNumber: String? = null,
    @ColumnInfo("image_url") var imageUrl: String? = null,
    @ColumnInfo("note") var note: String? = null,
    @ColumnInfo("created_at") var createdAt: String? = null,
    @ColumnInfo("updated_at") var updatedAt: String? = null
)