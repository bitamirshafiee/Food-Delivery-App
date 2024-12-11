package com.fooddelivery.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun FoodDeliveryApp(appState : FoodDeliveryAppState = rememberFoodDeliveryAppState()) {
    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.RestaurantList.route
    ) {
        composable(route = NavControllerRoute.RestaurantList.route) {}
    }
}
