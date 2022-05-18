package com.example.github.repositories.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.core.data.*
import com.example.github.repositories.core.data.remote.GitHubEndpoints
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = _repositories

    private val fetchReposUseCase = useCases.fetchReposUseCase

    init {
        fetchItems()
    }

    fun fetchItems(itemsToShow: Int = INITIAL_ITEMS_LOAD_COUNT, isForceFetch: Boolean = false) =
        viewModelScope.launch {
            if (repositories.value.isNullOrEmpty() || isForceFetch) {
                delay(NETWORK_DELAY) // This is to simulate network latency, please don't remove!
                var response = fetchReposUseCase.invoke()
                _repositories.value = response?.take(itemsToShow)
            }
        }
}