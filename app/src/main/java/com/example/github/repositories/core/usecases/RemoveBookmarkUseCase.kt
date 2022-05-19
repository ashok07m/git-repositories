package com.example.github.repositories.core.usecases

import com.example.github.repositories.core.data.repositories.BookmarksRepository
import com.example.github.repositories.core.domain.Repository
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(private val bookmarksRepository: BookmarksRepository) {

    suspend operator fun invoke(repository: Repository): Boolean {
        return bookmarksRepository.unBookmarkRepository(repository)
    }
}