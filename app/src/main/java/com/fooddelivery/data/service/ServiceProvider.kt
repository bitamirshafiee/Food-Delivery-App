package com.fooddelivery.data.service

import retrofit2.Retrofit
import javax.inject.Inject

class ServiceProvider @Inject constructor(retrofit: Retrofit) {
    val restaurantService: RestaurantService = retrofit.create(RestaurantService::class.java)
}