package com.example.github.repositories.core.data.repositories

import com.example.github.repositories.core.domain.ApiResult
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User

interface GitRepository {
    suspend fun fetchGitRepos(): ApiResult<List<Repository>>
    suspend fun fetchUserInfo(username: String): ApiResult<User?>
    suspend fun fetchUserGitRepos(reposUrl: String): ApiResult<List<Repository>>
}