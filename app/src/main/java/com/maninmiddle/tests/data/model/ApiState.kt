package com.maninmiddle.tests.data.model

sealed class ApiState<out T> {
    data object Empty : ApiState<Nothing>()
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val errorMessage: String) : ApiState<Nothing>()
}