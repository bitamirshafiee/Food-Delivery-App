package com.fooddelivery.ui.restaurantdetails

import androidx.lifecycle.ViewModel
import com.fooddelivery.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RestaurantDetailsViewModel @Inject constructor(private val repository: RestaurantRepository) :
    ViewModel()  {

    private val _restaurantStatus = MutableStateFlow(false)
    val restaurants: StateFlow<Boolean> = _restaurantStatus


}