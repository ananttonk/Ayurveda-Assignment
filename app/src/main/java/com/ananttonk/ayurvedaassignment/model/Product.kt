package com.ananttonk.ayurvedaassignment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_list_table")
data class Product(
    @PrimaryKey @ColumnInfo("productId")
    val productId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "description")
    val description: String,
) {
    override fun equals(other: Any?): Boolean {
        return this.productId == (other as? Product)?.productId
    }

    override fun hashCode(): Int {
        return productId
    }
}
