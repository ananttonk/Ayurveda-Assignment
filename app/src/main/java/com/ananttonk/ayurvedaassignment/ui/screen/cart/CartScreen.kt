package com.ananttonk.ayurvedaassignment.ui.screen.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ananttonk.ayurvedaassignment.AyurvedaApplication
import com.ananttonk.ayurvedaassignment.R
import com.ananttonk.ayurvedaassignment.factory.viewModelFactory
import com.ananttonk.ayurvedaassignment.model.CartItem
import com.ananttonk.ayurvedaassignment.model.Product
import com.ananttonk.ayurvedaassignment.ui.ext.AyurvedaAppBar
import com.ananttonk.ayurvedaassignment.ui.screen.AyurvedaViewModel

@Composable
fun CartScreen(onBackPress: () -> Unit) {
    val viewModel: AyurvedaViewModel =
        viewModel<AyurvedaViewModel>(
            factory = viewModelFactory
            { AyurvedaViewModel(AyurvedaApplication.instance.getModuleImpl().ayurvedaRepoImpl) })
    val uiState by viewModel.uiState.collectAsState()
    val nonZeroCartItems by remember {
        derivedStateOf{
            uiState.productWithQuantity.filter { it.value > 0 }
        }
    }
    Scaffold(topBar = {
        AyurvedaAppBar(
            title = "Cart",
            backPressCallBack = { onBackPress.invoke() },
        )
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (nonZeroCartItems.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .background(Color.White)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.cart_icon),
                        contentDescription = "Cart",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = "Cart is empty",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .background(Color.White)
                        .fillMaxSize()
                ) {
                    CartMainContent(cartList = nonZeroCartItems) {
                        viewModel.deleteItemFromCart(it, true)
                    }
                }
            }
        }
    }
}

@Composable
fun CartMainContent(
    cartList: Map<Product, Int>,
    removeItem: (id: Int) -> Unit
) {
    LazyColumn {
        items(cartList.keys.toList()) {
            CartItem(cart = it, quantity = cartList[it] ?: 0) { id ->
                removeItem(id)
            }
        }
    }
}

@Composable
fun CartItem(
    cart: Product,
    quantity: Int,
    removeItem: (id: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxWidth()
            .clickable {},
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(120.dp),
                shape = RoundedCornerShape(14.dp),
            ) {
                Image(
                    painterResource(id = R.drawable.ayurveda_image),
                    contentDescription = "Image",
                    modifier = Modifier.padding(20.dp),
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 4.dp, top = 16.dp)
                    .weight(0.9f)
            ) {
                Text(
                    text = cart.title, color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "₹ ${cart.price}", color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.padding(top = 10.dp))

                Text(
                    text = "Quantity: $quantity", color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            IconButton(
                onClick = { removeItem(cart.productId) },
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

    }
}