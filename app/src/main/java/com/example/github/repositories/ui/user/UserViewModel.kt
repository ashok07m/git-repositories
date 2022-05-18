package com.example.github.repositories.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.core.data.GITHUB_URL
import com.example.github.repositories.core.data.NETWORK_DELAY
import com.example.github.repositories.core.data.remote.GitHubEndpoints
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User
import com.example.github.repositories.core.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = _repositories

    fun fetchUser(username: String) = viewModelScope.launch {
        if (user.value == null) {
            delay(NETWORK_DELAY) // This is to simulate network latency, please don't remove!
            val response = useCases.fetchUserInfoUseCase.invoke(username)
            response?.let { _user.value = it }
        }
    }

    fun fetchRepositories(reposUrl: String) = viewModelScope.launch {
        if (repositories.value.isNullOrEmpty()) {
            delay(NETWORK_DELAY) // This is to simulate network latency, please don't remove!
            val response = useCases.fetchUserReposUseCase.invoke(reposUrl)
            response?.let { _repositories.value = it }
        }
    }
}