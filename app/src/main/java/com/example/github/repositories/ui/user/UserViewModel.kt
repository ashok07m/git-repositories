package com.example.github.repositories.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.data.GITHUB_URL
import com.example.github.repositories.data.NETWORK_DELAY
import com.example.github.repositories.data.source.remote.GitHubEndpoints
import com.example.github.repositories.data.source.remote.RepositoryDTO
import com.example.github.repositories.data.source.remote.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: GitHubEndpoints = retrofit.create(GitHubEndpoints::class.java)

    private val _user = MutableLiveData<UserDTO>()
    val user: LiveData<UserDTO> = _user

    private val _repositories = MutableLiveData<List<RepositoryDTO>>()
    val repositories: LiveData<List<RepositoryDTO>> = _repositories

    fun fetchUser(username: String) = viewModelScope.launch {
        if (user.value == null) {
            delay(NETWORK_DELAY) // This is to simulate network latency, please don't remove!
            val response = withContext(Dispatchers.IO) {
                service.getUser(username).execute()
            }
            response.body()?.let { _user.value = it }
        }
    }

    fun fetchRepositories(reposUrl: String) = viewModelScope.launch {
        if (repositories.value.isNullOrEmpty()) {
            delay(NETWORK_DELAY) // This is to simulate network latency, please don't remove!
            val response = withContext(Dispatchers.IO) {
                service.getUserRepositories(reposUrl).execute()
            }
            response.body()?.let { _repositories.value = it }
        }
    }
}