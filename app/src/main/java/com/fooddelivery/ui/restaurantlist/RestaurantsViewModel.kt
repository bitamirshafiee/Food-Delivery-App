package com.fooddelivery.ui.restaurantlist

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddelivery.data.model.RestaurantResponse
import com.fooddelivery.data.repository.NetworkResult
import com.fooddelivery.data.repository.RestaurantRepository
import com.fooddelivery.utils.errorHandlerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(private val repository: RestaurantRepository) :
    ViewModel() {

    private val _restaurants = MutableStateFlow(listOf<Restaurant>())

    private val _tags = MutableStateFlow(listOf<TagSelection>())

    private val _selectedTags = MutableStateFlow(listOf<Tag>())

    private val _filteredList = MutableStateFlow(listOf<Restaurant>())

    private val _state =
        MutableStateFlow<RestaurantsUIState>(RestaurantsUIState.Loading)
    val state: StateFlow<RestaurantsUIState>
        get() = _state

    init {
        getRestaurants()
    }

    private fun getRestaurants() {

        viewModelScope.launch {
            when (val result = repository.getRestaurants()) {
                is NetworkResult.Success -> {
                    val mainResult = getMainResult(result.data.restaurants)
                    observeTagFilters()
                    getUpdatedResultWithTags(mainResult)
                }
                is NetworkResult.Failure -> {
                    val networkError = errorHandlerHelper(result.throwable)
                    _state.value =
                        RestaurantsUIState.Error(errorMessage = networkError.errorMessage)
                }
            }
        }
    }

    private fun getMainResult(restaurants: List<RestaurantResponse>): List<Restaurant> {
        val mainResult = restaurants.map { restaurant ->
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
        return mainResult
    }

    private fun observeTagFilters() {
        viewModelScope.launch {
            combine(_restaurants, _selectedTags) { restaurants, selectedTags ->
                if (selectedTags.isEmpty()) restaurants
                else restaurants.filter { restaurant ->
                    restaurant.tags.containsAll(
                        selectedTags
                    )
                }
            }.collect { filteredList ->
                _filteredList.value = filteredList
                _state.value =
                    RestaurantsUIState.Restaurants(
                        restaurants = _filteredList.value,
                        tags = _tags.value
                    )

            }
        }
    }

    private suspend fun getUpdatedResultWithTags(mainResult: List<Restaurant>) {
        val updatedResultWithTags = mainResult.map { restaurant ->

            when (val result1 = repository.getAsyncTags(restaurant.filterIds)) {
                is NetworkResult.Success -> {
                    val tags = result1.data.map { tag ->
                        Tag(id = tag.id, imageUrl = tag.imageUrl, name = tag.name)
                    }
                    restaurant.copy(tags = tags)
                }

                is NetworkResult.Failure -> {
                    restaurant
                }
            }
        }
        showTopTagFilter(updatedResultWithTags)
        _restaurants.value = updatedResultWithTags
    }

    private fun showTopTagFilter(updatedResultWithTags: List<Restaurant>) {
        _tags.value =
            updatedResultWithTags.map { restaurant -> restaurant.tags }.flatten()
                .distinct().map { TagSelection(tag = it) }
    }

    fun addFilterToSelectedFilterList(tagSelection: TagSelection) {
        updateFilters(tagSelection)
        val list = _selectedTags.value
        if (!list.contains(tagSelection.tag)) {
            _selectedTags.value = list + tagSelection.tag
        }
    }

    fun removeFilterFromSelectedFilterList(tagSelection: TagSelection) {
        updateFilters(tagSelection)
        val list = _selectedTags.value
        if (list.contains(tagSelection.tag)) {
            _selectedTags.value = list - tagSelection.tag
        }
    }

    private fun updateFilters(tagSelection: TagSelection) {
        _tags.update { currentList ->
            currentList.map { item ->
                if (item.tag.id == tagSelection.tag.id) {
                    item.copy(isSelected = tagSelection.isSelected)
                } else {
                    item
                }
            }
        }
    }

    fun onRetry() {
        getRestaurants()
    }
}

@Parcelize
@Serializable
data class Restaurant(
    val id: String,
    val imageUrl: String,
    val rating: Double,
    val filterIds: List<String>,
    val tags: List<Tag> = persistentListOf(),
    val name: String,
    val deliveryTime: Int
) : Parcelable

@Parcelize
@Serializable
data class Tag(val id: String, val imageUrl: String, val name: String) : Parcelable

data class TagSelection(
    val tag: Tag, var isSelected: Boolean = false
)
