package com.example.github.repositories.core.data.repositories

import com.example.github.repositories.core.domain.ApiResult
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User
import kotlinx.coroutines.flow.SharedFlow

interface GitRepository {
    val gitReposSharedFlow: SharedFlow<List<Repository>>
    val userReposSharedFlow: SharedFlow<List<Repository>>
    suspend fun fetchGitRepos(): ApiResult<List<Repository>>
    suspend fun fetchUserInfo(username: String): ApiResult<User?>
    suspend fun fetchUserGitRepos(reposUrl: String): ApiResult<List<Repository>>
    suspend fun updateBookmarkStatus(repository: Repository, status: Boolean)
}