package io.maintenancevehicle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("histories")
data class History(
    @PrimaryKey
    @ColumnInfo("id") val id:String = UUID.randomUUID().toString(),
    @ColumnInfo("vehicle_id") val vehicleId: String,
    @ColumnInfo("status") val status: String? = null,
    @ColumnInfo("reason") val reason: String? = null,
    @ColumnInfo("solution") val solution: String? = null,
    @ColumnInfo("supervisor") val supervisor: String? = null,
    @ColumnInfo("created_at") val createdAt: String? = null,
    @ColumnInfo("note") val note: String? = null
)