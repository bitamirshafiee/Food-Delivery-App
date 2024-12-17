package com.fooddelivery.ui.restaurantlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.fooddelivery.R


@Composable
fun Restaurants(
    viewModel: RestaurantsViewModel, navigateToRestaurantDetails: (restaurant: Restaurant) -> Unit
) {

    val restaurants by viewModel.restaurants.collectAsState()

    LazyColumn {
        items(items = restaurants) { restaurant: Restaurant ->
            RestaurantItem(restaurant = restaurant, navigate = navigateToRestaurantDetails)
        }
    }

}

@Composable
fun RestaurantItem(
    restaurant: Restaurant, navigate: (restaurant: Restaurant) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { navigate(restaurant) }) {
        AsyncImage(
            model = restaurant.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Red)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = restaurant.name, Modifier.weight(1f)
            )

            Row(modifier = Modifier) {
                Icon(painter = painterResource(id = R.drawable.ic_star), contentDescription = null)
                Text(
                    text = restaurant.rating.toString()
                )
            }
        }
        LazyRow() {
            items(items = restaurant.tags) { tag: Tag ->
                Text(
                    text = tag.name
                )
            }
        }

        Row(modifier = Modifier) {
            Icon(painter = painterResource(id = R.drawable.img), contentDescription = null)
            Text(
                text = restaurant.deliveryTime.toString()
            )
        }

    }

}

@Composable
fun Filter() {

    Row() {
        AsyncImage(
            model = "https://food-delivery.umain.io/images/restaurant/burgers.png",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Red)
        )
        Text(
            text = "1 hour"
        )
    }

}

@Preview
@Composable
fun RestaurantPreview() {
    RestaurantItem(Restaurant(
        imageUrl = "",
        rating = 2.5,
        filterIds = listOf(),
        tags = listOf(),
        name = "bita",
        deliveryTime = 12
    ), {})
}

@Preview
@Composable
fun FilterPreview() {
}