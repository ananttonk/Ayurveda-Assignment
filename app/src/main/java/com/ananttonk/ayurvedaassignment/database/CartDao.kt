package com.ananttonk.ayurvedaassignment.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.ananttonk.ayurvedaassignment.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Upsert
    suspend fun addToCart(cartItem: CartItem)

    @Delete
    suspend fun deleteFromCart(cartItem: CartItem)

    @Query("SELECT * FROM cart WHERE productId = :productId")
    suspend fun getById(productId: Int): CartItem?

    @Query("SELECT * FROM cart")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Query("UPDATE cart SET quantity = :newQuantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, newQuantity: Int)
}