package com.fooddelivery.ui.restaurantdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddelivery.data.repository.NetworkResult
import com.fooddelivery.data.repository.RestaurantRepository
import com.fooddelivery.ui.restaurantlist.Restaurant
import com.fooddelivery.utils.errorHandlerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val repository: RestaurantRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state =
        MutableStateFlow<RestaurantDetailsUIState>(RestaurantDetailsUIState.Loading)
    val state: StateFlow<RestaurantDetailsUIState>
        get() = _state

    init {
        val passedRestaurant: Restaurant? = savedStateHandle.get<Restaurant>(key = "restaurant")
        passedRestaurant?.let { restaurant ->
            getRestaurantStatus(restaurant)
        }
    }

    private fun getRestaurantStatus(restaurant: Restaurant) {
        viewModelScope.launch {
            when (val result = repository.isOpen(restaurant.id)) {
                is NetworkResult.Success -> {
                    _state.value = RestaurantDetailsUIState.RestaurantDetails(
                        restaurant = restaurant,
                        isOpen = result.data.isOpen
                    )
                }

                is NetworkResult.Failure -> {
                    val networkError = errorHandlerHelper(result.throwable)
                    _state.value = RestaurantDetailsUIState.Error(errorMessage = networkError.errorMessage)
                }
            }
        }
    }

    fun onRetry(){
        val passedRestaurant: Restaurant? = savedStateHandle.get<Restaurant>(key = "restaurant")
        passedRestaurant?.let { restaurant ->
            getRestaurantStatus(restaurant)
        }
    }

}
