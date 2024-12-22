package com.fooddelivery.ui.restaurantlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fooddelivery.R

//TODO add font helvetica
@Composable
fun Restaurants(
    viewModel: RestaurantsViewModel, navigateToRestaurantDetails: (restaurant: Restaurant) -> Unit
) {
    val restaurants by viewModel.filteredList.collectAsState()
    val tags by viewModel.tags.collectAsState()

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Logo()
        LazyRow(modifier = Modifier.padding(vertical = 16.dp)) {
            items(items = tags) { tag: Tag ->
                FilterItem(
                    tag = tag,
                    onClick = { tag -> viewModel.updateSelectedTags(listOf(tag)) })
            }
        }
        LazyColumn {

            items(items = restaurants, key = {item -> item.id}) { restaurant: Restaurant ->
                val modifier = Modifier.animateItem()
                RestaurantItem(restaurant = restaurant, navigate = navigateToRestaurantDetails, modifier = modifier)
            }
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

