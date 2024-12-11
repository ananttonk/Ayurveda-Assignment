package com.ananttonk.ayurvedaassignment.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ananttonk.ayurvedaassignment.model.CartItem
import com.ananttonk.ayurvedaassignment.model.Product
import com.ananttonk.ayurvedaassignment.repository.AyurvedaRepo
import com.ananttonk.ayurvedaassignment.ui.screen.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AyurvedaViewModel(private val repo: AyurvedaRepo) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val singleProductUiState = MutableStateFlow<Product?>(null)
    val singleProduct: StateFlow<Product?> = singleProductUiState.asStateFlow()

    init {
        getProductList()
    }

    fun insertDemoProductIfRequired(onDone: () -> Unit) {
        viewModelScope.launch {
            if (repo.getSavedProducts().isEmpty()) {
                repo.insertSampleProduct()
            }
            onDone()
        }
    }

    private fun getProductList() {
        viewModelScope.launch {
            val allProductsFromDB = repo.getSavedProducts()
            repo.getCartList().onEach {
                updateUiData(allProductsFromDB, it)
            }.launchIn(this)
        }
    }

    private fun updateUiData(products: List<Product>, cart: List<CartItem>) {
        val items = products.associateWith { product ->
            cart.firstOrNull {
                product.productId == it.productId
            }?.quantity ?: 0
        }
        _uiState.update {
            it.copy(productWithQuantity = items)
        }
    }

    fun getProductById(productId: Int) {
        viewModelScope.launch {
            val product = repo.getProductById(productId)
            singleProductUiState.value = product
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            repo.addToCart(product)
        }
    }

    fun deleteItemFromCart(productId: Int, deleteAll: Boolean) {
        viewModelScope.launch {
            repo.deleteItemFromCart(productId, deleteAll)
        }
    }

}