package com.ananttonk.ayurvedaassignment.module

import com.ananttonk.ayurvedaassignment.database.CartDao
import com.ananttonk.ayurvedaassignment.database.ProductDao
import com.ananttonk.ayurvedaassignment.repository.AyurvedaRepoImp

class ModuleImpl(dao: ProductDao, cartDao: CartDao) : DaoService {
    override val ayurvedaRepoImpl: AyurvedaRepoImp by lazy {
        AyurvedaRepoImp(dao, cartDao)
    }
}