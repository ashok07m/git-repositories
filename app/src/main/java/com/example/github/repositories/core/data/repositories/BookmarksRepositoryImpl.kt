package com.example.github.repositories.core.data.repositories

import com.example.github.repositories.core.data.local.LocalDataStore
import com.example.github.repositories.core.domain.Repository
import javax.inject.Inject

class BookmarksRepositoryImpl @Inject constructor(
    private val localDataStore: LocalDataStore,
    private val gitRepository: GitRepository
) :
    BookmarksRepository {

    override suspend fun isRepositoryBookmarked(repository: Repository): Boolean {
        return localDataStore.isRepositoryBookmarked(repository)
    }

    override suspend fun bookmarkRepository(repository: Repository): Boolean {
        val status = localDataStore.bookmarkRepository(repository)
        gitRepository.updateBookmarkStatus(repository,true)
        return status

    }

    override suspend fun unBookmarkRepository(repository: Repository): Boolean {
        val result = localDataStore.unBookmarkRepository(repository)
        gitRepository.updateBookmarkStatus(repository,false)
        return result
    }

    override suspend fun fetchBookmarkRepositories(): List<Repository> {
        return localDataStore.fetchBookmarkRepositories()
    }

}