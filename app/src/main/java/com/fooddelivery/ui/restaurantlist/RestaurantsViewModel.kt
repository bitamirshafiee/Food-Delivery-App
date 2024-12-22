package com.fooddelivery.ui.restaurantlist

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddelivery.data.repository.RestaurantRepository
import com.fooddelivery.data.repository.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(private val repository: RestaurantRepository) :
    ViewModel() {

    private val _restaurants = MutableStateFlow(listOf<Restaurant>())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants

    private val _tags = MutableStateFlow(listOf<Tag>())
    val tags: StateFlow<List<Tag>> = _tags

    private val _selectedTags = MutableStateFlow(listOf<Tag>())
    val selectedTags: StateFlow<List<Tag>> = _selectedTags

    private val _filteredList = MutableStateFlow(listOf<Restaurant>())
    val filteredList: StateFlow<List<Restaurant>> = _filteredList

    init {
        observeTagFilters()
        getRestaurants()
    }

    private fun getRestaurants() {

        viewModelScope.launch {
            when (val result = repository.getRestaurants()) {
                is NetworkResult.Success -> {
                    val mainResult = result.data.restaurants.map { restaurant ->
                        Restaurant(
                            id = restaurant.id,
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
                    _tags.value = updatedResultWithTags.map { it.tags }.flatten().distinct()
                }

                is NetworkResult.Failure -> {
                    _restaurants.value
                }

            }

        }
    }

    private fun observeTagFilters() {
        viewModelScope.launch {
            combine(_restaurants, _selectedTags) { restaurants, selectedTags ->
                if (selectedTags.isEmpty())
                    restaurants
                else
                    restaurants.filter { it.tags.containsAll(selectedTags) }
            }.collect { filteredList ->
                _filteredList.value = filteredList
            }
        }
    }

    fun updateSelectedTags(newTags: List<Tag>) {
        _selectedTags.value = newTags
    }
}

@Parcelize
@Serializable
data class Restaurant(
    val id: String,
    val imageUrl: String,
    val rating: Double,
    val filterIds: List<String>,
    val tags: List<Tag> = listOf(),
    val name: String,
    val deliveryTime: Int
) : Parcelable

@Parcelize
@Serializable
data class Tag(val imageUrl: String, val name: String, val isSelected: Boolean = false) : Parcelable
