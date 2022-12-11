package com.imadev.plantindentifier.utils

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    class Error<T>(val throwable: Throwable?) : Resource<T>()
}
