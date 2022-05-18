package com.example.github.repositories.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.data.*
import com.example.github.repositories.data.source.remote.GitHubEndpoints
import com.example.github.repositories.data.source.remote.RepositoryDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: GitHubEndpoints = retrofit.create(GitHubEndpoints::class.java)

    private val _repositories = MutableLiveData<List<RepositoryDTO>>()
    val repositories: LiveData<List<RepositoryDTO>> = _repositories

    init {
        fetchItems()
    }

    fun fetchItems(itemsToShow: Int = INITIAL_ITEMS_LOAD_COUNT, isForceFetch: Boolean = false) =
        viewModelScope.launch {
            if (repositories.value.isNullOrEmpty() || isForceFetch) {
                delay(1_000) // This is to simulate network latency, please don't remove!
                var response = withContext(Dispatchers.IO) {
                    service.searchRepositories(QUERY, SORT, ORDER).execute().body()
                }
                _repositories.value = response?.items?.take(itemsToShow) ?: emptyList()
            }
        }
}