package com.fooddelivery.data.repository

import com.fooddelivery.data.model.RestaurantStatusResponse
import com.fooddelivery.data.model.RestaurantsResponse
import com.fooddelivery.data.model.TagResponse
import com.fooddelivery.data.service.ServiceProvider
import com.fooddelivery.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class RestaurantRepository {

    abstract suspend fun getRestaurants(): NetworkResult<RestaurantsResponse>
    abstract suspend fun getAsyncTags(tagIds: List<String>): NetworkResult<List<TagResponse>>
    abstract suspend fun isOpen(id: String): NetworkResult<RestaurantStatusResponse>
}

class RestaurantRepositoryImpl @Inject constructor(
    serviceProvider: ServiceProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) :
    RestaurantRepository() {

    private val restaurantService = serviceProvider.restaurantService

    override suspend fun getRestaurants(): NetworkResult<RestaurantsResponse> {

        return withContext(ioDispatcher) {
            try {
                NetworkResult.Success(restaurantService.getRestaurants())
            } catch (exception: Exception) {
                NetworkResult.Failure(exception)
            }
        }
    }

    override suspend fun getAsyncTags(tagIds: List<String>): NetworkResult<List<TagResponse>> =
        coroutineScope {
            try {
                val tags = tagIds.map {
                    async(ioDispatcher) { restaurantService.getTag(it) }
                }.awaitAll()
                NetworkResult.Success(tags)

            } catch (exception: Exception) {
                NetworkResult.Failure(exception)
            }
        }

    override suspend fun isOpen(id: String): NetworkResult<RestaurantStatusResponse> {
        return withContext(ioDispatcher) {
            try {
                NetworkResult.Success(restaurantService.getRestaurantStatus(id))
            } catch (exception: Exception) {
                NetworkResult.Failure(exception)
            }
        }
    }




}