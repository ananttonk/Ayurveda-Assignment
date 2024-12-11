package com.ananttonk.ayurvedaassignment.ui.screen.productdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun ProductDetails(
    productId: Int,
    onBackPress: () -> Unit,
    openCartScreen: () -> Unit
) {
    val viewModel: AyurvedaViewModel = viewModel<AyurvedaViewModel>(factory = viewModelFactory
    { AyurvedaViewModel(AyurvedaApplication.instance.getModuleImpl().ayurvedaRepoImpl) })
    LaunchedEffect(Unit) {
        viewModel.getProductById(productId)
    }
    val uiState by viewModel.uiState.collectAsState()
    val cartListCount by remember { derivedStateOf{
        uiState.productWithQuantity.values.sum()
    } }
    val singleProduct by viewModel.singleProduct.collectAsState()
    Scaffold(topBar = {
        AyurvedaAppBar(
            title = "Ayurveda",
            actions = {
                BadgedBox(modifier = Modifier.padding(end = 18.dp), badge = {
                    if (cartListCount > 0) {
                        Badge { Text(text = "$cartListCount") }
                    }
                }) {
                    IconButton(
                        onClick = {
                            openCartScreen()
                        }, modifier = Modifier
                            .size(30.dp)
                            .padding(end = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Add to Wishlist",
                            tint = Color.White,
                        )
                    }
                }
            },
            backPressCallBack = { onBackPress() }
        )
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(Color.White)
                .fillMaxSize()
        ) {
            ProductDetails(singleProduct)
        }
    }
}

@Composable
fun ProductDetails(product: Product?) {
    Card(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth().background(Color.White)
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(14.dp),
            ) {
                Image(
                    painterResource(id = R.drawable.ayurveda_image),
                    contentDescription = "Image",
                    modifier = Modifier.padding(20.dp),
                )
            }

            Spacer(Modifier.padding(top = 10.dp))

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = product?.title.orEmpty(), color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.padding(top = 10.dp))
                Text(
                    text = "₹${product?.price.toString()}", color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Spacer(Modifier.padding(top = 15.dp))
                Text(
                    text = "Description",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                )
                Text(
                    text = product?.description.orEmpty(),
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                )
            }
        }
    }
}