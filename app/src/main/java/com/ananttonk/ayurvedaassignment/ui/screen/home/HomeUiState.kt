package com.ananttonk.ayurvedaassignment.ui.screen.home

import com.ananttonk.ayurvedaassignment.model.Product

data class HomeUiState(
    val productWithQuantity: Map<Product, Int> = emptyMap(),
)