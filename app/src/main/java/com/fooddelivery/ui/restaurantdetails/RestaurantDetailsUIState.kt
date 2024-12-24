package com.fooddelivery.ui.restaurantdetails

import com.fooddelivery.ui.restaurantlist.Restaurant

sealed interface RestaurantDetailsUIState {

    data object Loading : RestaurantDetailsUIState

    data class Error(
        val errorMessage: String? = null
    ) : RestaurantDetailsUIState

    data class RestaurantDetails(val restaurant: Restaurant, val isOpen: Boolean) :
        RestaurantDetailsUIState

}