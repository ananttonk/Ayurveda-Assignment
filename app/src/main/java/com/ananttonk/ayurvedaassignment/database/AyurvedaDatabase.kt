package com.ananttonk.ayurvedaassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ananttonk.ayurvedaassignment.model.CartItem
import com.ananttonk.ayurvedaassignment.model.Product

@Database(entities = [Product::class, CartItem::class], version = 1, exportSchema = false)
abstract class AyurvedaDatabase : RoomDatabase() {

    abstract fun ayurvedaDao(): ProductDao

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCES: AyurvedaDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCES ?: synchronized(LOCK) {
            INSTANCES ?: createDataBase(context).also {
                INSTANCES = it
            }
        }

        private fun createDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AyurvedaDatabase::class.java,
            "AyurvedaDatabase"
        ).fallbackToDestructiveMigration().build()
    }

}