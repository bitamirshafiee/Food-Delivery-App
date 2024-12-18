package com.fooddelivery.ui.restaurantdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.fooddelivery.data.repository.RestaurantRepository
import com.fooddelivery.ui.restaurantlist.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val repository: RestaurantRepository,
    savedStateHandle: SavedStateHandle,
    ) :
    ViewModel()  {

        init {
            val passedRestaurant: Restaurant? = savedStateHandle.get<Restaurant>(key = "restaurant")
            passedRestaurant?.name?.let { Log.d("RestaurantDetailsViewModel", it) }
        }

    private val _restaurantStatus = MutableStateFlow(false)
    val restaurants: StateFlow<Boolean> = _restaurantStatus


}