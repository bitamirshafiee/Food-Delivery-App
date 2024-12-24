package com.fooddelivery.ui.restaurantlist

import androidx.annotation.StringRes
import kotlinx.collections.immutable.persistentListOf

sealed interface RestaurantsUIState {

    data object Loading : RestaurantsUIState

    data class Error(
        @StringRes val errorMessage: Int? = null
    ) : RestaurantsUIState

    data class Restaurants(
        val restaurants: List<Restaurant>,
        val tags: List<TagSelection> = persistentListOf()
    ) : RestaurantsUIState

}