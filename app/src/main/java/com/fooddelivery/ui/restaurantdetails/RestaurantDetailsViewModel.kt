package com.fooddelivery.ui.restaurantdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddelivery.data.repository.NetworkResult
import com.fooddelivery.data.repository.RestaurantRepository
import com.fooddelivery.ui.restaurantlist.Restaurant
import com.fooddelivery.ui.restaurantlist.TagSelection
import com.fooddelivery.ui.restaurantlist.getDefaultRestaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val repository: RestaurantRepository,
    savedStateHandle: SavedStateHandle,
) :
    ViewModel() {

    private val _restaurant = MutableStateFlow(getDefaultRestaurant())
    val restaurant: StateFlow<Restaurant> = _restaurant


    private val _restaurantStatus = MutableStateFlow(false)
    val restaurantStatus: StateFlow<Boolean> = _restaurantStatus

    init {
        val passedRestaurant: Restaurant? = savedStateHandle.get<Restaurant>(key = "restaurant")
        passedRestaurant?.let {
            _restaurant.value = it
            getRestaurantStatus(it)
        }
    }

    private fun getRestaurantStatus(restaurant: Restaurant) {
        viewModelScope.launch {
            when (val result = repository.isOpen(restaurant.id)) {
                is NetworkResult.Success -> {
                    _restaurantStatus.value = result.data.isOpen
                }

                is NetworkResult.Failure -> {}
            }
        }
    }

}