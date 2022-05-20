package com.example.github.repositories.core.data.remote

import com.example.github.repositories.core.data.ORDER
import com.example.github.repositories.core.data.QUERY
import com.example.github.repositories.core.data.SORT
import com.example.github.repositories.core.data.mappers.toRepositories
import com.example.github.repositories.core.data.mappers.toUser
import com.example.github.repositories.core.domain.ApiResult
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User
import com.example.github.repositories.di.DefaultDispatcher
import com.example.github.repositories.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitReposRemoteSourceImpl @Inject constructor(
    private val gitReposApi: GitHubEndpoints,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : GitReposRemoteSource, BaseRepository(ioDispatcher) {

    override suspend fun fetchGitRepos(): ApiResult<List<Repository>> {

        val response =
            executeRequest { gitReposApi.searchRepositories(QUERY, SORT, ORDER) }

        return when (response) {
            is ApiResult.Success -> {
                val repos = withContext(defaultDispatcher) {
                    response.data.items.toRepositories()
                }
                ApiResult.Success(repos)
            }
            is ApiResult.Error -> {
                ApiResult.Error(response.exception)
            }
        }
    }

    override suspend fun fetchUserRepos(reposUrl: String): ApiResult<List<Repository>> {
        val response =
            executeRequest { gitReposApi.getUserRepositories(reposUrl) }

        return when (response) {
            is ApiResult.Success -> {
                val repos = withContext(defaultDispatcher) {
                    response.data.toRepositories()
                }
                ApiResult.Success(repos)
            }
            is ApiResult.Error -> {
                ApiResult.Error(response.exception)
            }
        }
    }

    override suspend fun fetchUserInfo(username: String): ApiResult<User?> {
        val response =
            executeRequest { gitReposApi.getUser(username) }

        return when (response) {
            is ApiResult.Success -> {
                val repos = withContext(defaultDispatcher) {
                    response.data.toUser()
                }
                ApiResult.Success(repos)
            }
            is ApiResult.Error -> {
                ApiResult.Error(response.exception)
            }
        }
    }
}