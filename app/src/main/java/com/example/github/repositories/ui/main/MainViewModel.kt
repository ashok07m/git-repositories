package com.example.github.repositories.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.core.data.*
import com.example.github.repositories.core.domain.ApiResult
import com.example.github.repositories.core.usecases.UseCases
import com.example.github.repositories.ui.base.BaseViewModel
import com.example.github.repositories.ui.base.ViewStateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    useCases: UseCases
) : BaseViewModel(appContext) {

    private val fetchReposUseCase = useCases.fetchReposUseCase

    init {
        viewModelScope.launch {
            fetchReposUseCase.observeRepos().collect { repos ->
                Log.d("TAG", "flow called..")
                val updatedList = fetchReposUseCase.transformListData(repos)
                _viewStateResult.value =
                    ViewStateResult.Success(updatedList.take(INITIAL_ITEMS_LOAD_COUNT))
            }
        }
    }

    fun fetchItems(isForceFetch: Boolean = false) =
        viewModelScope.launch {
            if (viewStateResult.value !is ViewStateResult.Success<*> || isForceFetch) {
                when (val response = getApiResponse { fetchReposUseCase.invoke() }) {
                    is ApiResult.Success -> {}
                    is ApiResult.Error -> {
                        val errorMessage = getNetworkErrorMessage(response.exception)
                        _viewStateResult.value = ViewStateResult.Error(errorMessage)
                    }
                }
            }
        }
}