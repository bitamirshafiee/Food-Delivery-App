package com.fooddelivery.ui.restaurantlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddelivery.data.repository.RestaurantRepository
import com.fooddelivery.data.repository.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(private val repository: RestaurantRepository) :
    ViewModel() {

    private val _restaurants = MutableStateFlow(listOf<Restaurant>())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants

    init {
        getRestaurants()
    }

    private fun getRestaurants() {


        viewModelScope.launch {
            when (val result = repository.getRestaurants()) {
                is NetworkResult.Success -> {
                    val mainResult = result.data.restaurants.map { restaurant ->
                        Restaurant(
                            imageUrl = restaurant.imageUrl,
                            rating = restaurant.rating,
                            filterIds = restaurant.filterIds,
                            name = restaurant.name,
                            deliveryTime = restaurant.deliveryTime
                        )
                    }
                    _restaurants.value = mainResult

                    val updatedResultWithTags = mainResult.map { restaurant ->
                        val tags = repository.getTags(restaurant.filterIds).awaitAll()
                            .map { tag -> Tag(imageUrl = tag.imageUrl, name = tag.name) }

                        restaurant.copy(tags = tags)
                    }
                    _restaurants.value = updatedResultWithTags
                }

                is NetworkResult.Failure -> {
                    _restaurants.value
                }

                else -> {}
            }

        }
    }
}

data class Restaurant(
    val imageUrl: String,
    val rating: Double,
    val filterIds : List<String>,
    val tags: List<Tag> = listOf(),
    val name: String,
    val deliveryTime: Int
)

data class Tag(val imageUrl: String, val name: String)
