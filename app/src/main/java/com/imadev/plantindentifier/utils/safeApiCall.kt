package com.imadev.plantindentifier.utils

import kotlinx.coroutines.flow.flow


inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T
) = flow {

    emit(Resource.Loading)

    try {
        emit(Resource.Success(apiCall.invoke()))
    } catch (throwable: Throwable) {
        emit(Resource.Error(throwable))
    }
}
