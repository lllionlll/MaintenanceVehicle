package io.maintenancevehicle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("vehicles")
data class VehicleDetail(
    @PrimaryKey
    @ColumnInfo("id") val id: String,
    @ColumnInfo("status") val status: String? = null,
    @ColumnInfo("reason") val reason: String? = null,
    @ColumnInfo("solution") val solution: String? = null,
    @ColumnInfo("supervisor") val supervisor: String? = null,
    @ColumnInfo("created_at") val createdAt: String? = null,
    @ColumnInfo("note") val note: String? = null
)