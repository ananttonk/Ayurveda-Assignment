package com.ananttonk.ayurvedaassignment.repository

import com.ananttonk.ayurvedaassignment.model.CartItem
import com.ananttonk.ayurvedaassignment.model.Product
import kotlinx.coroutines.flow.Flow

interface AyurvedaRepo {
    suspend fun getSavedProducts(): List<Product>
    suspend fun getProductById(id: Int): Product
    suspend fun addToCart(product: Product)
    fun getCartList(): Flow<List<CartItem>>
    suspend fun deleteItemFromCart(id: Int, deleteAll:Boolean)
    suspend fun insertSampleProduct()

}