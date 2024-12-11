package com.ananttonk.ayurvedaassignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ananttonk.ayurvedaassignment.ui.screen.cart.CartScreen
import com.ananttonk.ayurvedaassignment.ui.screen.home.HomeScreen
import com.ananttonk.ayurvedaassignment.ui.screen.productdetails.ProductDetails

@Composable
fun AyurvedaNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationScreen.HomeScreen.name) {
        composable(route = NavigationScreen.HomeScreen.name) {
            HomeScreen(
                openDetailsScreen = { productId ->
                    navController.navigate(NavigationScreen.ProductDetailsScreen.name + "/$productId")
                },
                openCartScreen = { navController.navigate(NavigationScreen.CartScreen.name) })
        }
        composable(
            route = NavigationScreen.ProductDetailsScreen.name + "/{productId}", arguments = listOf(
                navArgument(name = "productId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            ProductDetails(productId = backStackEntry.arguments?.getInt("productId") ?: 0,
                onBackPress = {
                    navController.popBackStack()
                }, openCartScreen = {
                    navController.navigate(NavigationScreen.CartScreen.name)
                })
        }
        composable(route = NavigationScreen.CartScreen.name) {
            CartScreen() {
                navController.popBackStack()
            }
        }
    }
}