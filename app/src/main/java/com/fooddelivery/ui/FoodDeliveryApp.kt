package com.fooddelivery.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fooddelivery.ui.restaurantlist.Restaurants


@Composable
fun FoodDeliveryApp(appState : FoodDeliveryAppState = rememberFoodDeliveryAppState()) {
    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.RestaurantList.route
    ) {
        composable(route = NavControllerRoute.RestaurantList.route) {
            Restaurants()
        }
    }
}
