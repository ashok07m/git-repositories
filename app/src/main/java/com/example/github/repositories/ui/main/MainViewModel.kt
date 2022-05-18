package com.example.github.repositories.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.repositories.data.*
import com.example.github.repositories.data.source.remote.GitHubEndpoints
import com.example.github.repositories.data.source.remote.RepositoryDTO
import com.example.github.repositories.data.source.remote.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltViewModel
class MainViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: GitHubEndpoints = retrofit.create(GitHubEndpoints::class.java)

    val repositories = MutableLiveData<List<RepositoryDTO>>()

    init {
        fetchItems()
    }

    fun fetchItems() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1_000) // This is to simulate network latency, please don't remove!
            var response: Response?
            withContext(Dispatchers.IO) {
                response = service.searchRepositories(QUERY, SORT, ORDER).execute().body()
            }
            repositories.value = response?.items
        }
    }

    fun refresh() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1_000) // This is to simulate network latency, please don't remove!
            var response: Response?
            withContext(Dispatchers.IO) {
                response = service.searchRepositories(QUERY, SORT, ORDER).execute().body()
            }
            repositories.value = response?.items
        }
    }
}