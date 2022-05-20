package com.example.github.repositories.ui.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.repositories.R
import com.example.github.repositories.core.data.NETWORK_DELAY
import com.example.github.repositories.core.domain.ApiResult
import kotlinx.coroutines.delay
import java.io.IOException

abstract class BaseViewModel(
    private val appContext: Context,
) : ViewModel() {

    protected val _viewStateResult = MutableLiveData<ViewStateResult>()
    val viewStateResult: LiveData<ViewStateResult> = _viewStateResult


    protected suspend fun <T> getApiResponse(
        delay: Long = NETWORK_DELAY,
        call: suspend () -> ApiResult<T>
    ): ApiResult<T> {
        _viewStateResult.value = ViewStateResult.Loading(true)
        delay(delay) // This is to simulate network latency, please don't remove!
        var response = call.invoke()
        _viewStateResult.value = ViewStateResult.Loading(false)
        return response
    }

    protected fun getNetworkErrorMessage(exception: Exception): String {
        val errorMessage = if (exception is IOException) {
            appContext.getString(R.string.error_no_internet)
        } else {
            appContext.getString(R.string.error_unable_to_load)
        }
        return errorMessage
    }
}