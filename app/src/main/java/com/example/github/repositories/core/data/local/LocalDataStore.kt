package com.example.github.repositories.core.data.local

import com.example.github.repositories.core.data.repositories.GitRepository
import com.example.github.repositories.core.domain.Repository

interface LocalDataStore {
    suspend fun isRepositoryBookmarked(repository: Repository): Boolean
    suspend fun bookmarkRepository(repository: Repository): Boolean
    suspend fun unBookmarkRepository(repository: Repository): Boolean
    suspend fun fetchBookmarkRepositories(): List<Repository>
}