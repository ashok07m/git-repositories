package com.example.github.repositories.core.data.local

import com.example.github.repositories.core.data.mappers.toRepositories
import com.example.github.repositories.core.data.mappers.toRepositoryDTO
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.response.RepositoryDTO
import com.example.github.repositories.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataStoreImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : LocalDataStore {

    private val bookmarks = mutableMapOf<Int, RepositoryDTO>()

    override suspend fun isRepositoryBookmarked(repository: Repository): Boolean {
        return withContext(defaultDispatcher) {
            val repositoryDTO = repository.toRepositoryDTO()
            repositoryDTO.id?.let { bookmarks[it]?.let { true } ?: false } == true
        }
    }

    override suspend fun bookmarkRepository(repository: Repository): Boolean {
        return withContext(defaultDispatcher) {
            val repositoryDTO = repository.toRepositoryDTO()
            repositoryDTO.id?.let {
                bookmarks[it] = repositoryDTO
                true
            } == true
        }
    }

    override suspend fun unBookmarkRepository(repository: Repository): Boolean {
        return withContext(defaultDispatcher) {
            val repositoryDTO = repository.toRepositoryDTO()
            repositoryDTO.id?.let {
                bookmarks.remove(it)
                true
            } == true
        }
    }

    override suspend fun fetchBookmarkRepositories(): List<Repository> {
        return withContext(defaultDispatcher) {
            bookmarks.values.toList().toRepositories()
        }
    }
}