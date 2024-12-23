package com.fooddelivery.ui.restaurantlist

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddelivery.data.repository.RestaurantRepository
import com.fooddelivery.data.repository.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
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
    val tags: StateFlow<List<TagSelection>> = _tags

    private val _selectedTags = MutableStateFlow(listOf<Tag>())

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
                        val tags = repository.getTags(restaurant.filterIds).awaitAll().map { tag ->
                            Tag(
                                id = tag.id, imageUrl = tag.imageUrl, name = tag.name
                            )
                        }
                        restaurant.copy(tags = tags)
                    }
                    _restaurants.value = updatedResultWithTags
                    _tags.value =
                        updatedResultWithTags.map { restaurant -> restaurant.tags }.flatten()
                            .distinct().map { TagSelection(tag = it) }
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
                if (selectedTags.isEmpty()) restaurants
                else restaurants.filter { restaurant ->
                    restaurant.tags.containsAll(
                        selectedTags
                    )
                }
            }.collect { filteredList ->
                _filteredList.value = filteredList
            }
        }
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
data class Tag(val id: String, val imageUrl: String, val name: String) : Parcelable

data class TagSelection(
    val tag: Tag, var isSelected: Boolean = false
)
