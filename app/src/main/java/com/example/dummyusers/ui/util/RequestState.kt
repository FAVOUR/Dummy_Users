package com.example.dummyusers.ui.util

/**
 * A generic class that holds a loading signal or the result of an async operation.
 */
sealed class RequestState<out T> {

    object Loading : RequestState<Nothing>()

    data class Error(val errorMessage: Int) : RequestState<Nothing>()

    data class Success<out T>(val data: T) : RequestState<T>()
}
