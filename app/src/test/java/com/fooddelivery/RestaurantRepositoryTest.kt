package com.fooddelivery

import com.fooddelivery.data.model.RestaurantResponse
import com.fooddelivery.data.model.RestaurantsResponse
import com.fooddelivery.data.repository.NetworkResult
import com.fooddelivery.data.repository.RestaurantRepositoryImpl
import com.fooddelivery.data.service.RestaurantService
import com.fooddelivery.data.service.ServiceProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Test

//This is an example of how I will write tests for repository and api calls
class RestaurantRepositoryTest {

    //I usually prefer to mock classes without mockk library so I will have more freedom on changing
    // the test class, I used mockk here since I didn't have so much time and also this test is
    // written to show how injecting dispatchers is helpful in testing


    //This is why we inject dispatcher in repository, since it is best to not use other dispatchers
    // in code under the test
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    @Test
    fun `getRestaurants Result Success when api call is successful`() = runTest(
        testScheduler
    ) {

        val mockRestaurantService = mockk<RestaurantService>()
        coEvery { mockRestaurantService.getRestaurants() } returns RestaurantsResponse(
            getFakeRestaurants()
        )
        val mockServiceProvider = mockk<ServiceProvider>()
        every { mockServiceProvider.restaurantService } returns mockRestaurantService

        val repository = RestaurantRepositoryImpl(
            ioDispatcher = testDispatcher,
            serviceProvider = mockServiceProvider
        )

        val result = repository.getRestaurants()

        assertTrue(result is NetworkResult.Success)
        assertTrue((result as NetworkResult.Success).data.restaurants.isNotEmpty())
    }

    @Test
    fun `getRestaurants result is failure when api call throws exception`() = runTest(testScheduler) {

        val mockRestaurantService = mockk<RestaurantService>()
        coEvery { mockRestaurantService.getRestaurants() } throws RuntimeException("Network error")

        val mockServiceProvider = mockk<ServiceProvider>()
        every { mockServiceProvider.restaurantService } returns mockRestaurantService


    val repository = RestaurantRepositoryImpl(
        ioDispatcher = testDispatcher,
        serviceProvider = mockServiceProvider
    )

    val result = repository.getRestaurants()

    assertTrue(result is NetworkResult.Failure)
    assertTrue((result as NetworkResult.Failure).throwable.suppressedExceptions is RuntimeException)
}
}

fun getFakeRestaurants() = listOf(
    RestaurantResponse(
        id="id1",
        imageUrl = "",
        rating = 4.5,
        filterIds = listOf(),
        name = "restaurant1",
        deliveryTime = 2
    ),
    RestaurantResponse(
        id="id2",
        imageUrl = "",
        rating = 3.7,
        filterIds = listOf(),
        name = "restaurant2",
        deliveryTime = 4
    ),
    RestaurantResponse(
        id="id3",
        imageUrl = "",
        rating = 5.00,
        filterIds = listOf(),
        name = "restaurant3",
        deliveryTime = 7
    )
)