package com.example.github.repositories.core.data.remote

import com.example.github.repositories.core.data.ORDER
import com.example.github.repositories.core.data.QUERY
import com.example.github.repositories.core.data.SORT
import com.example.github.repositories.core.data.mappers.toRepositories
import com.example.github.repositories.core.data.mappers.toUser
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User
import com.example.github.repositories.di.NetworkModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitReposRemoteSourceImpl @Inject constructor(
    private val gitReposApi: GitHubEndpoints,
    @NetworkModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @NetworkModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : GitReposRemoteSource {

    override suspend fun fetchGitRepos(): List<Repository> {
        return withContext(ioDispatcher) {
            val response = gitReposApi.searchRepositories(QUERY, SORT, ORDER).execute().body()
            val repos = withContext(defaultDispatcher) {
                response?.items?.toRepositories() ?: emptyList()
            }
            repos
        }
    }

    override suspend fun fetchUserRepos(reposUrl: String): List<Repository> {
        return withContext(ioDispatcher) {
            val response = gitReposApi.getUserRepositories(reposUrl).execute().body()
            val repos = withContext(defaultDispatcher) {
                response?.toRepositories() ?: emptyList()
            }
            repos
        }
    }

    override suspend fun fetchUserInfo(username: String): User? {
        return withContext(ioDispatcher) {
            val response = gitReposApi.getUser(username).execute().body()
            val user = withContext(defaultDispatcher) {
                response?.toUser()
            }
            user
        }
    }
}