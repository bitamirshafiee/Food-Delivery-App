package com.fooddelivery.ui.restaurantlist

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.fooddelivery.R

//Using Chip like FilterChip would have been nice, but it was not possible here according to
// the ui provided
@Composable
fun FilterItem(tagSelection: TagSelection, onFilterSelected: (TagSelection) -> Unit, onFilterUnSelected: (TagSelection) -> Unit) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .padding(end = 4.dp)
            .clickable {
                if (tagSelection.isSelected) {
                    onFilterUnSelected(tagSelection.copy(isSelected = false))
                } else
                    onFilterSelected(tagSelection.copy(isSelected = true))
            }
    ) {
        TagCard(name = tagSelection.tag.name, isSelected = tagSelection.isSelected)
        TagImage(imageUrl = tagSelection.tag.imageUrl, isSelected = tagSelection.isSelected)
    }
}

@Composable
fun TagCard(name: String, isSelected: Boolean, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 24.dp),
        shape = RoundedCornerShape(0.dp, 15.dp, 15.dp, 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFd6d2d2) else Color.White,
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
fun TagImage(imageUrl: String, isSelected: Boolean, modifier: Modifier = Modifier) {
    Box {
        AsyncImage(
            modifier = modifier
                .size(48.dp)
                .clip(CircleShape),
            model = imageUrl,
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
        if (isSelected)
            Image(
                modifier = Modifier.fillMaxHeight(),
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null
            )
    }
}