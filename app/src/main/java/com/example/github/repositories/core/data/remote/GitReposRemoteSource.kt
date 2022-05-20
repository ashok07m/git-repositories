package com.example.github.repositories.core.data.remote

import com.example.github.repositories.core.domain.ApiResult
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User

interface GitReposRemoteSource {
    suspend fun fetchGitRepos(): ApiResult<List<Repository>>
    suspend fun fetchUserRepos(reposUrl: String): ApiResult<List<Repository>>
    suspend fun fetchUserInfo(username: String): ApiResult<User?>
}