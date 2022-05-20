package com.example.github.repositories.core.domain

sealed class ApiResult<T>(
    data: T? = null,
    exception: Exception? = null
) {
    data class Success<T>(val data: T) : ApiResult<T>(data, null)

    data class Error<T>(
        val exception: Exception
    ) : ApiResult<T>(null, exception)
}