package com.example.github.repositories.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val useCases: UseCases) : ViewModel() {

    private val _bookmarks: MutableLiveData<List<Repository>> = MutableLiveData()
    val bookmarks: LiveData<List<Repository>> = _bookmarks

    private val _bookmarkStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    val bookmarkStatus: LiveData<Boolean> = _bookmarkStatus


    private fun getBookmarks() = viewModelScope.launch {
        val bookmarks = useCases.fetchBookmarksUseCase.invoke()
        _bookmarks.value = bookmarks
    }

    private fun bookmarkRepository(repository: Repository) = viewModelScope.launch {
        val isBookmarked = useCases.addBookmarkUseCase.invoke(repository)
        _bookmarkStatus.value = isBookmarked
    }

    private fun unBookmarkRepository(repository: Repository) = viewModelScope.launch {
        val isUnBookmarked = useCases.removeBookmarkUseCase.invoke(repository)
        _bookmarkStatus.value = isUnBookmarked.not() // set inverted result to show un-bookmarked state
    }

    fun isRepositoryBookmarked(repository: Repository) = viewModelScope.launch {
        val isBookmarked = useCases.isBookmarkedUseCase.invoke(repository)
        _bookmarkStatus.value = isBookmarked
    }

    fun toggleBookmarkStatus(repository: Repository) {
        if (bookmarkStatus.value == false) bookmarkRepository(repository)
        else unBookmarkRepository(repository)
    }

}