package com.fooddelivery.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fooddelivery.ui.FoodDeliveryDestination.RESTAURANT_DETAILS
import com.fooddelivery.ui.restaurantdetails.RestaurantDetails
import com.fooddelivery.ui.restaurantdetails.RestaurantDetailsViewModel
import com.fooddelivery.ui.restaurantlist.Restaurant
import com.fooddelivery.ui.restaurantlist.Restaurants
import com.fooddelivery.ui.restaurantlist.RestaurantsViewModel


@Composable
fun FoodDeliveryApp(appState: FoodDeliveryAppState = rememberFoodDeliveryAppState()) {
    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.RestaurantList.route
    ) {
        composable(route = NavControllerRoute.RestaurantList.route) {
            val viewModel = hiltViewModel<RestaurantsViewModel>()
            Restaurants(
                viewModel = viewModel,
                navigateToRestaurantDetails = { restaurant: Restaurant ->
                    appState.navigateTo(RESTAURANT_DETAILS)
                })
        }

        composable(route = NavControllerRoute.RestaurantDetails.route) {
            val viewModel = hiltViewModel<RestaurantDetailsViewModel>()
            RestaurantDetails(viewModel = viewModel)
        }
    }
}
