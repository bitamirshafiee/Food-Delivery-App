package com.fooddelivery.ui.restaurantlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fooddelivery.R
import com.fooddelivery.ui.commonui.ScreenError
import com.fooddelivery.ui.commonui.ScreenLoading

@Composable
fun Restaurants(
    viewModel: RestaurantsViewModel, navigateToRestaurantDetails: (restaurant: Restaurant) -> Unit
) {

    val restaurantsUIState by viewModel.state.collectAsStateWithLifecycle()

    when (val uiState = restaurantsUIState) {
        is RestaurantsUIState.Loading -> {
            ScreenLoading()
        }

        is RestaurantsUIState.Error -> {
            ScreenError(onRetry = viewModel::onRetry, errorMessage = uiState.errorMessage)
        }

        is RestaurantsUIState.Restaurants -> {
            RestaurantsAndTags(
                restaurants = uiState.restaurants,
                tags = uiState.tags,
                addFilter = { tag -> viewModel.addFilterToSelectedFilterList(tag) },
                removeFilter = { tag -> viewModel.removeFilterFromSelectedFilterList(tag) },
                navigateToRestaurantDetails = navigateToRestaurantDetails
            )
        }
    }

}

@Composable
fun RestaurantsAndTags(
    tags: List<TagSelection>,
    restaurants: List<Restaurant>,
    addFilter: (TagSelection) -> Unit,
    removeFilter: (TagSelection) -> Unit,
    navigateToRestaurantDetails: (restaurant: Restaurant) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Logo()
        TagList(tags = tags,
            addFilter = { tag -> addFilter(tag) },
            removeFilter = { tag -> removeFilter(tag) })
        RestaurantList(restaurants, navigateToRestaurantDetails)
    }
}

@Composable
private fun RestaurantList(
    restaurants: List<Restaurant>,
    navigateToRestaurantDetails: (restaurant: Restaurant) -> Unit,
) {
    LazyColumn {

        items(items = restaurants, key = { item -> item.id }) { restaurant: Restaurant ->
            val modifier = Modifier.animateItem()
            RestaurantItem(
                restaurant = restaurant, navigate = navigateToRestaurantDetails, modifier = modifier
            )
        }
    }
}

@Composable
private fun TagList(
    tags: List<TagSelection>,
    addFilter: (TagSelection) -> Unit,
    removeFilter: (TagSelection) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier.padding(vertical = 16.dp)) {
        items(items = tags) { tagSelection: TagSelection ->
            FilterItem(tagSelection = tagSelection, onFilterSelected = { selectedTag ->
                addFilter(selectedTag)
            }, onFilterUnSelected = { unSelectedTag ->
                removeFilter(unSelectedTag)
            })
        }
    }
}

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(54.dp),
        painter = painterResource(id = R.drawable.umain_logo),
        contentDescription = null
    )
}

