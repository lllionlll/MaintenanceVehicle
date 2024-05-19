package io.maintenancevehicle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("vehicles")
data class Vehicle(
    @PrimaryKey
    @ColumnInfo("vehicle_id") val vehicleId: String = UUID.randomUUID().toString(),
    @ColumnInfo("customer_id") val customerId: String? = null,
    @ColumnInfo("customer_name") val customerName: String? = null,
    @ColumnInfo("type") val type: Int? = null,
    @ColumnInfo("name") val name: String? = null,
    @ColumnInfo("image") val image: String? = null,
    @ColumnInfo("number") val number: String? = null,
    @ColumnInfo("color") val color: String? = null,
    @ColumnInfo("brand") val brand: String? = null,
    @ColumnInfo("status") val status: String? = null,
    @ColumnInfo("description") val description: String? = null,
    @ColumnInfo("created_at") var createdAt: String? = null,
    @ColumnInfo("updated_at") var updatedAt: String? = null

)