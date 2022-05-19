package com.example.github.repositories.core.usecases

import javax.inject.Inject

data class UseCases @Inject constructor(
    val fetchUserReposUseCase: FetchUserReposUseCase,
    val fetchUserInfoUseCase: FetchUserInfoUseCase,
    val fetchReposUseCase: FetchReposUseCase,
    val addBookmarkUseCase: AddBookmarkUseCase,
    val removeBookmarkUseCase: RemoveBookmarkUseCase,
    val fetchBookmarksUseCase: FetchBookmarksUseCase,
    val isBookmarkedUseCase: IsBookmarkedUseCase
)
