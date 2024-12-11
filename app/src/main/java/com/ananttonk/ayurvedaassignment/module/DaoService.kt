package com.ananttonk.ayurvedaassignment.module

import com.ananttonk.ayurvedaassignment.repository.AyurvedaRepoImp

interface DaoService {
    val ayurvedaRepoImpl: AyurvedaRepoImp
}