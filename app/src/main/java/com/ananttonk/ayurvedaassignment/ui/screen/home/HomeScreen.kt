package com.ananttonk.ayurvedaassignment.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ananttonk.ayurvedaassignment.model.Product
import com.ananttonk.ayurvedaassignment.ui.ext.AyurvedaAppBar
import com.ananttonk.ayurvedaassignment.ui.screen.AyurvedaViewModel

@Composable
fun HomeScreen(openDetailsScreen: (id: Int) -> Unit, openCartScreen: () -> Unit) {
    val viewModel: AyurvedaViewModel =
        viewModel<AyurvedaViewModel>(factory = viewModelFactory
        { AyurvedaViewModel(AyurvedaApplication.instance.getModuleImpl().ayurvedaRepoImpl) })
    val uiState by viewModel.uiState.collectAsState()
    val cartListCount by remember { derivedStateOf { uiState.productWithQuantity.values.sum() }}

    Scaffold(topBar = {
        AyurvedaAppBar(
            title = "Ayurveda",
            actions = {
                BadgedBox(badge = {
                    if (cartListCount > 0) {
                        Badge { Text(text = "$cartListCount") }
                    }
                }) {
                    IconButton(
                        onClick = {
                            openCartScreen()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Add to Wishlist",
                            tint = Color.White,
                        )
                    }
                }
            },
        )
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(Color.White)
                .fillMaxSize()
        ) {
            ProductMainContent(
                uiState.productWithQuantity,
                openDetailsScreen = { productId ->
                    openDetailsScreen(productId)
                },
                addCart = { product -> viewModel.addToCart(product) },
                removeCart = { product -> viewModel.deleteItemFromCart(product.productId, false) })
        }
    }
}


@Composable
fun ProductMainContent(
    productList: Map<Product, Int>,
    openDetailsScreen: (id: Int) -> Unit,
    addCart: (product: Product) -> Unit,
    removeCart: (product: Product) -> Unit,
) {
    LazyColumn {
        items(productList.keys.toList(), key = { it.productId.toString() + ":"+ productList[it] }) {
            ProductItem(
                product = it,
                openDetailsScreen = { productId ->
                    openDetailsScreen(productId)
                },
                addCart = { addCart(it) },
                removeCart = { removeCart(it) },
                quantityAddedToCart = productList[it] ?: 0
            )
        }
    }

}

@Composable
fun ProductItem(
    product: Product,
    quantityAddedToCart: Int,
    openDetailsScreen: (id: Int) -> Unit,
    addCart: () -> Unit,
    removeCart: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxWidth()
            .clickable {
                openDetailsScreen(product.productId)
            },
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
                    modifier = Modifier.padding(20.dp),
                    contentDescription = "Image",
                )
            }
            Column(
                modifier = Modifier.padding(start = 4.dp, top = 16.dp)
            ) {
                Text(
                    text = product.title, color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "₹ ${product.price}", color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.padding(top = 10.dp))
                if (quantityAddedToCart > 0) {
                    UpdateQuantityButton(
                        currentQuantity = quantityAddedToCart,
                        onAdd = addCart,
                        onRemove = removeCart
                    )
                } else {
                    Button(
                        onClick = addCart,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1B6D0B)
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .height(40.dp)
                            .width(140.dp)
                    ) {
                        Text(
                            text = "Add to cart",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UpdateQuantityButton(
    currentQuantity: Int,
    onAdd: () -> Unit,
    onRemove:() -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(40.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color(0xFF1B6D0B),
                contentColor = Color.White
            )
        ) {
            Icon(
                painterResource(id = R.drawable.remove),
                contentDescription = "Decrease"
            )
        }

        Text(
            text = currentQuantity.toString(),
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        IconButton(
            onClick = onAdd,
            modifier = Modifier.size(40.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color(0xFF1B6D0B),
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase"
            )
        }
    }
}