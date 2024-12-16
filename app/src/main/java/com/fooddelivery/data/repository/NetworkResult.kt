package com.fooddelivery.data.repository

sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val throwable: Throwable) : NetworkResult<T>()
}
