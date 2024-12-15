package com.fooddelivery.data.model

import com.google.gson.annotations.SerializedName

data class RestaurantsResponse(@SerializedName("restaurants") val restaurants: List<RestaurantResponse>)

data class RestaurantResponse(
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("filterIds") val filterIds: List<String>,
    @SerializedName("name") val name: String,
    @SerializedName("delivery_time_minutes") val deliveryTime: Int
)

data class TagResponse(
    @SerializedName("id") val id: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("name") val name: String
)

data class TagRequest(@SerializedName("id") val id: String)
