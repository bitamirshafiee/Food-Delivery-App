package com.fooddelivery.di

import com.fooddelivery.data.repository.RestaurantRepository
import com.fooddelivery.data.repository.RestaurantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun binRestaurantRepository(
        restaurantRepositoryImpl: RestaurantRepositoryImpl
    ) : RestaurantRepository
}