package io.maintenancevehicle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName
import java.util.UUID

@Entity("services")
data class Service(
    @PrimaryKey @ColumnInfo("id") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo("name") var name: String? = null,
    @ColumnInfo("image_url") var image: String? = null,
    @ColumnInfo("price") var price: Int? = null,
    @ColumnInfo("description") var description: String? = null,
    @ColumnInfo("time") var time: String? = null,
    @ColumnInfo("created_at") var createdAt: String? = null,
    @ColumnInfo("updated_at") var updatedAt: String? = null
)