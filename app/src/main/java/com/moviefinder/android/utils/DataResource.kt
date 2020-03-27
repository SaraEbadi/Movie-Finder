package com.moviefinder.android.utils

sealed class DataResource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null) : DataResource<T>(data)

    class Error<T>(message: String, data: T? = null) : DataResource<T>(data, message)

    fun fold(responseSuccess: (Success<T>) -> Unit, responseError: (Error<T>) -> Unit) {
        when (this) {
            is Success -> responseSuccess(this)
            is Error -> responseError(this)
        }
    }
}