package io.maintenancevehicle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("widgets")
data class Widget(
    @PrimaryKey @ColumnInfo("widget_id") var widgetId: String = UUID.randomUUID().toString(),
    @ColumnInfo("name") var name: String? = null,
    @ColumnInfo("image_url") var image: String? = null,
    @ColumnInfo("brand") var brand: String? = null,
    @ColumnInfo("color") var color: String? = null,
    @ColumnInfo("price") var price: Int? = null,
    @ColumnInfo("count") var count: Int? = null,
    @ColumnInfo("from") var from: String? = null,
    @ColumnInfo("created_at") var createdAt: String? = null,
    @ColumnInfo("updated_at") var updatedAt: String? = null
)