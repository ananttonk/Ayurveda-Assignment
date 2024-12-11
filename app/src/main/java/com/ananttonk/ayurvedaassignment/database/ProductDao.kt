package com.ananttonk.ayurvedaassignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ananttonk.ayurvedaassignment.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<Product>)

    @Query("SELECT * FROM product_list_table")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM product_list_table WHERE productId = :id")
    suspend fun getProductById(id: Int): Product

    @Query("DELETE FROM product_list_table")
    suspend fun deleteAllProducts()
}