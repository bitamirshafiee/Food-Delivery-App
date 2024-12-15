package com.fooddelivery.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fooddelivery.ui.restaurantlist.Restaurants
import com.fooddelivery.ui.restaurantlist.RestaurantsViewModel


@Composable
fun FoodDeliveryApp(appState : FoodDeliveryAppState = rememberFoodDeliveryAppState()) {
    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.RestaurantList.route
    ) {
        composable(route = NavControllerRoute.RestaurantList.route) {
            val viewModel = hiltViewModel<RestaurantsViewModel>()
            Restaurants(viewModel)
        }
    }
}
