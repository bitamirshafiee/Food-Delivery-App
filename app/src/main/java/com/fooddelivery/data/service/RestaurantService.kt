package com.fooddelivery.data.service

import com.fooddelivery.data.model.RestaurantStatusResponse
import com.fooddelivery.data.model.RestaurantsResponse
import com.fooddelivery.data.model.TagResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantService {

    @GET("restaurants")
    suspend fun getRestaurants(): RestaurantsResponse

    @GET("filter/{id}")
    suspend fun getTag(
        @Path("id") id: String
    ): TagResponse

    @GET
    suspend fun getRestaurantStatus(
        @Path("id") id: String
    ) : RestaurantStatusResponse


}