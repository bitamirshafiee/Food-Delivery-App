package com.fooddelivery.ui.restaurantlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun FilterItem(tag: Tag, onClick: (Tag) -> Unit) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .padding(end = 4.dp)
            .clickable { onClick(tag) }
    ) {
        TagCard(name = tag.name)
        TagImage(tag.imageUrl)
    }
}

@Composable
fun TagCard(name: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 24.dp),
        shape = RoundedCornerShape(0.dp, 15.dp, 15.dp, 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = name,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun TagImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape),
        model = imageUrl,
        contentScale = ContentScale.Fit,
        contentDescription = null
    )
}