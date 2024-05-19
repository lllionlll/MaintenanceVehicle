package io.maintenancevehicle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("users")
data class User(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    @ColumnInfo("user_name") var userName: String? = null,
    @ColumnInfo("password") var password: String? = null
)