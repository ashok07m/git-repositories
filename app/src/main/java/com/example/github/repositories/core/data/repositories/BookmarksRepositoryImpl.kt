package com.example.github.repositories.core.data.repositories

import com.example.github.repositories.core.data.local.LocalDataStore
import com.example.github.repositories.core.domain.Repository
import javax.inject.Inject

class BookmarksRepositoryImpl @Inject constructor(private val localDataStore: LocalDataStore) :
    BookmarksRepository {

    override suspend fun isRepositoryBookmarked(repository: Repository): Boolean {
        return localDataStore.isRepositoryBookmarked(repository)
    }

    override suspend fun bookmarkRepository(repository: Repository): Boolean {
        return localDataStore.bookmarkRepository(repository)
    }

    override suspend fun unBookmarkRepository(repository: Repository): Boolean {
        return localDataStore.unBookmarkRepository(repository)
    }

    override suspend fun fetchBookmarkRepositories(): List<Repository> {
        return localDataStore.fetchBookmarkRepositories()
    }

}