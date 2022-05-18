package com.example.github.repositories.core.data.remote

import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User

interface GitReposRemoteSource {
    suspend fun fetchGitRepos(): List<Repository>
    suspend fun fetchUserRepos(reposUrl: String): List<Repository>
    suspend fun fetchUserInfo(username: String): User?
}