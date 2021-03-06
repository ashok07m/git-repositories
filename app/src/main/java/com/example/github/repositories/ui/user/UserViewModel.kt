package com.example.github.repositories.ui.user

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
class UserViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val useCases: UseCases
) : BaseViewModel(appContext) {

    private val fetchUserReposUseCase = useCases.fetchUserReposUseCase
    private val fetchReposUseCase = useCases.fetchReposUseCase

    private val _userViewStateResult = MutableLiveData<ViewStateResult>()
    val userViewStateResult: LiveData<ViewStateResult> = _userViewStateResult

    private var reposUrl: String? = null

    init {
        viewModelScope.launch {
            fetchUserReposUseCase.observeRepos().collect { repos ->
                Log.d("TAG", "user flow called..")
                val updatedList = fetchReposUseCase.transformListData(repos)
                _viewStateResult.value = ViewStateResult.Success(updatedList)
            }
        }
    }

    fun fetchUser(username: String?) = viewModelScope.launch {
        username ?: return@launch
        if (userViewStateResult.value is ViewStateResult.Success<*>) return@launch

        when (val response = getApiResponse { useCases.fetchUserInfoUseCase.invoke(username) }) {
            is ApiResult.Success -> {
                _userViewStateResult.value = ViewStateResult.Success(response.data)
                reposUrl = response.data?.repos_url
            }
            is ApiResult.Error -> {
                val errorMessage = getNetworkErrorMessage(response.exception)
                _userViewStateResult.value = ViewStateResult.Error(errorMessage)
            }
        }
    }

    fun fetchRepositories(reposUrl: String?) = viewModelScope.launch {
        reposUrl ?: return@launch
        if (viewStateResult.value is ViewStateResult.Success<*>) return@launch

        when (val response = getApiResponse { useCases.fetchUserReposUseCase.invoke(reposUrl) }) {
            is ApiResult.Success -> {
            }
            is ApiResult.Error -> {
                val errorMessage = getNetworkErrorMessage(response.exception)
                _viewStateResult.value = ViewStateResult.Error(errorMessage)
            }
        }
    }

    fun getReposUrl() = reposUrl
}