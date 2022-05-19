package com.example.github.repositories.core.usecases

import com.example.github.repositories.core.data.repositories.BookmarksRepository
import com.example.github.repositories.core.data.repositories.GitRepository
import com.example.github.repositories.core.domain.Repository
import javax.inject.Inject

class FetchBookmarksUseCase @Inject constructor(private val bookmarksRepository: BookmarksRepository) {

    suspend operator fun invoke(): List<Repository> {
        return bookmarksRepository.fetchBookmarkRepositories()
    }
}