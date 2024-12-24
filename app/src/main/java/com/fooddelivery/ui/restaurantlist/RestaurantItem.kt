package com.fooddelivery.ui.restaurantlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.fooddelivery.R
import com.fooddelivery.utils.minuteToHour
import com.fooddelivery.utils.separateListWithDots


//Better way to handle styles in Texts would be to create typography for each style in theme Type
@Composable
fun RestaurantItem(
    restaurant: Restaurant, navigate: (restaurant: Restaurant) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { navigate(restaurant) }) {
            RestaurantImage(restaurant.imageUrl)
            val restaurantDetailsModifier = Modifier.padding(horizontal = 8.dp)
            RestaurantDetails(restaurant = restaurant, modifier = restaurantDetailsModifier)
        }
    }
}

@Composable
fun RestaurantImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = modifier.fillMaxWidth().height(132.dp),
        model = imageUrl,
        contentScale = ContentScale.FillWidth,
        contentDescription = null
    )
}

@Composable
fun RestaurantDetails(restaurant: Restaurant, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        RestaurantNameAndRating(
            name = restaurant.name, rating = restaurant.rating
        )
        RestaurantTags(restaurant.tags)
        RestaurantDeliveryTime(
            deliveryTime = restaurant.deliveryTime
        )
    }
}

@Composable
fun RestaurantNameAndRating(name: String, rating: Double, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(top = 8.dp)
    ) {
        val nameModifier = Modifier.weight(1f)
        RestaurantName(name = name, modifier = nameModifier)
        RestaurantRating(rating)
    }
}

@Composable
fun RestaurantName(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier,
        fontSize = 18.sp,
    )
}

@Composable
fun RestaurantRating(rating: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier
                .width(12.dp)
                .height(12.dp),
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Text(
            text = rating.toString(),
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 11.sp,
            color = Color(0xFF50555C)
        )
    }
}

@Composable
fun RestaurantTags(tagSelections: List<Tag>, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = separateListWithDots(tagSelections.map { it.name }),
        fontSize = 12.sp,
        color = Color(0xFF999999)
    )
}

@Composable
fun RestaurantDeliveryTime(deliveryTime: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .width(10.dp)
                .height(10.dp),
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = minuteToHour(deliveryTime),
            fontSize = 11.sp,
            color = Color(0xFF50555C)
        )
    }
}
@Preview
@Composable
fun RestaurantItemPreview() {
    RestaurantItem(Restaurant(
        id = "",
        imageUrl = "",
        rating = 2.5,
        filterIds = listOf(),
        tags = listOf(),
        name = "bita",
        deliveryTime = 12
    ), {})
}