package com.fooddelivery.ui.restaurantdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.fooddelivery.R
import com.fooddelivery.ui.commonui.ScreenError
import com.fooddelivery.ui.commonui.ScreenLoading
import com.fooddelivery.ui.restaurantlist.Restaurant
import com.fooddelivery.ui.restaurantlist.Tag
import com.fooddelivery.utils.separateListWithDots


@Composable
fun RestaurantDetails(viewModel: RestaurantDetailsViewModel) {

    val restaurantDetailsUIState by viewModel.state.collectAsStateWithLifecycle()

    when (val uiState = restaurantDetailsUIState) {
        is RestaurantDetailsUIState.Loading -> { ScreenLoading() }
        is RestaurantDetailsUIState.Error -> {
            ScreenError(onRetry = viewModel::onRetry, errorMessage = uiState.errorMessage)}
        is RestaurantDetailsUIState.RestaurantDetails -> {
            RestaurantInfo(uiState.restaurant, uiState.isOpen)
        }
    }

}

@Composable
private fun RestaurantInfo(
    restaurant: Restaurant, isRestaurantOpen: Boolean, modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy((-60).dp)) {
        RestaurantImage(restaurant.imageUrl)
        RestaurantDetails(restaurant, isRestaurantOpen)
    }
}

@Composable
private fun RestaurantDetails(
    restaurant: Restaurant, isRestaurantOpen: Boolean, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            RestaurantName(restaurant.name)
            RestaurantTags(restaurant.tags)
            RestaurantStatus(isRestaurantOpen)
        }
    }
}

@Composable
fun RestaurantImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = modifier.fillMaxWidth(),
        model = imageUrl,
        contentScale = ContentScale.FillWidth,
        contentDescription = null
    )
}

@Composable
fun RestaurantName(name: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
        text = name,
        fontSize = 24.sp,
    )
}

@Composable
fun RestaurantTags(tagSelections: List<Tag>, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        text = separateListWithDots(tagSelections.map { it.name }),
        fontSize = 16.sp,
        color = Color(0xFF999999)
    )
}

@Composable
fun RestaurantStatus(isOpen: Boolean, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
        text = if (isOpen) stringResource(id = R.string.str_is_open)
        else stringResource(id = R.string.str_is_closed),
        fontSize = 18.sp,
        color = Color(0xFF2ECC71)
    )
}

