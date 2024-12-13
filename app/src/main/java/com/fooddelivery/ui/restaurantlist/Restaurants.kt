package com.fooddelivery.ui.restaurantlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.fooddelivery.R


@Composable
fun Restaurants() {

}

@Composable
fun Restaurant() {
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = "https://food-delivery.umain.io/images/restaurant/burgers.png",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Red)
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)) {
            Text(
                text = "name",
                Modifier.weight(1f)
            )

            Row(modifier = Modifier) {
                Icon(painter = painterResource(id = R.drawable.ic_star), contentDescription = null)
                Text(
                    text = "3.6"
                )
            }
        }
        Text(
            text = "tags"
        )
        Row(modifier = Modifier) {
            Icon(painter = painterResource(id = R.drawable.img), contentDescription = null)
            Text(
                text = "1 hour"
            )
        }

    }

}

@Composable
fun Filter(){

    Row(){
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
    Restaurant()
}

@Preview
@Composable
fun FilterPreview(){}