package com.fooddelivery.ui

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fooddelivery.ui.restaurantdetails.RestaurantDetails
import com.fooddelivery.ui.restaurantdetails.RestaurantDetailsViewModel
import com.fooddelivery.ui.restaurantlist.Restaurant
import com.fooddelivery.ui.restaurantlist.Restaurants
import com.fooddelivery.ui.restaurantlist.RestaurantsViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf


@Composable
fun FoodDeliveryApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {

        composable<Route.RestaurantList> {
            val viewModel = hiltViewModel<RestaurantsViewModel>()
            Restaurants(
                viewModel = viewModel,
                navigateToRestaurantDetails = { restaurant: Restaurant ->
                    navController.navigate(Route.RestaurantDetails(restaurant))
                })
        }
        composable<Route.RestaurantDetails>(
            typeMap = mapOf(
                typeOf<Restaurant>() to navType<Restaurant>(),
                typeOf<Restaurant?>() to navType<Restaurant?>()
            )
        ) {
            val viewModel = hiltViewModel<RestaurantDetailsViewModel>()
            RestaurantDetails(viewModel = viewModel)
        }
    }
}

sealed class Route {
    @Serializable
    data object RestaurantList : Route()

    @Serializable
    data class RestaurantDetails(val restaurant: Restaurant? = null) : Route()
}

inline fun <reified T : Parcelable?> navType(
    isNullableAllowed: Boolean = true,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION") bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): T {
        val deserializedResult = json.decodeFromString<T>(value)
        return deserializedResult
    }

    override fun serializeAsValue(value: T): String {
        return if (value == null) {
            ""
        } else {
            json.encodeToString(value)
        }
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        if (value == null) {
            bundle.putParcelable(key, null)
        } else {
            bundle.putParcelable(key, value)
        }
    }
}


