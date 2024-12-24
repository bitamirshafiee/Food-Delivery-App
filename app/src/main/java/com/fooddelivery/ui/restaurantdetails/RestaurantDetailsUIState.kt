package com.fooddelivery.ui.restaurantdetails

import androidx.annotation.StringRes
import com.fooddelivery.ui.restaurantlist.Restaurant

sealed interface RestaurantDetailsUIState {

    data object Loading : RestaurantDetailsUIState

    data class Error(
        @StringRes val errorMessage: Int? = null
    ) : RestaurantDetailsUIState

    data class RestaurantDetails(val restaurant: Restaurant, val isOpen: Boolean) :
        RestaurantDetailsUIState

}