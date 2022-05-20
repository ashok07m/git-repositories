package com.example.github.repositories.ui.base

sealed class ViewStateResult {
    data class Loading(var isLoading: Boolean) : ViewStateResult()
    data class Error(var errorMsg: String) : ViewStateResult()
    data class Success<T>(var data: T) : ViewStateResult()
}