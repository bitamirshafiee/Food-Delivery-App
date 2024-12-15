package com.fooddelivery.data.repository

import com.fooddelivery.data.model.RestaurantsResponse
import com.fooddelivery.data.model.TagRequest
import com.fooddelivery.data.model.TagResponse
import com.fooddelivery.data.service.ServiceProvider
import com.weatherapp.repository.NetworkResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class RestaurantRepository {

    abstract suspend fun getRestaurants(): NetworkResult<RestaurantsResponse>
    abstract suspend fun getTags(tagIds: List<String>): List<Deferred<TagResponse>>
}


//TODO add dispatcher parameter
//https://developer.android.com/kotlin/coroutines/coroutines-best-practices
class RestaurantRepositoryImpl @Inject constructor(
    serviceProvider: ServiceProvider) :
    RestaurantRepository() {

    private val restaurantService = serviceProvider.restaurantService

    override suspend fun getRestaurants(): NetworkResult<RestaurantsResponse> {

        return withContext(Dispatchers.IO){
            try {
                NetworkResult.Success(restaurantService.getRestaurants())
            } catch (exception: Exception) {
                NetworkResult.Failure(exception)
            }
        }
    }

     override suspend fun getTags(tagIds: List<String>): List<Deferred<TagResponse>> =
         coroutineScope {
             tagIds.map {
                 async(Dispatchers.IO) { restaurantService.getTag(it) }
             }
         }


}