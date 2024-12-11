package com.ananttonk.ayurvedaassignment

import android.app.Application
import com.ananttonk.ayurvedaassignment.database.AyurvedaDatabase
import com.ananttonk.ayurvedaassignment.module.ModuleImpl

class AyurvedaApplication : Application() {
    companion object {
        lateinit var instance: AyurvedaApplication
    }

    private lateinit var moduleImpl: ModuleImpl
    override fun onCreate() {
        super.onCreate()
        val db = AyurvedaDatabase(this)
        instance = this
        moduleImpl = ModuleImpl(db.ayurvedaDao(), db.cartDao())
    }

    fun getModuleImpl() = moduleImpl

}