package com.fooddelivery.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberFoodDeliveryAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(navController, context) {
    FoodDeliveryAppState(navController)
}
    //in case of navigation between screens
class FoodDeliveryAppState(
    val navController: NavHostController
) {
    fun navigateTo(route: String) {
        navController.navigate(route = route)
    }
    fun navigateBack() {
        navController.popBackStack()
    }
}

sealed class NavControllerRoute(
    val route: String,
) {
    data object RestaurantList : NavControllerRoute("restaurantList")
}