package com.example.github.repositories.core.data.repositories

import com.example.github.repositories.core.domain.Repository

interface BookmarksRepository {
    suspend fun isRepositoryBookmarked(repository: Repository): Boolean
    suspend fun bookmarkRepository(repository: Repository): Boolean
    suspend fun unBookmarkRepository(repository: Repository): Boolean
    suspend fun fetchBookmarkRepositories(): List<Repository>
}