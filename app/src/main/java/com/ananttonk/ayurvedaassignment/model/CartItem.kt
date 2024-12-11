package com.ananttonk.ayurvedaassignment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey val productId: Int,
    @ColumnInfo(defaultValue = "1") val quantity: Int
)