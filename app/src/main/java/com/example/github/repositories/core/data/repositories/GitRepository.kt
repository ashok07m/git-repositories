package com.example.github.repositories.core.data.repositories

import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User

interface GitRepository {
    suspend fun fetchGitRepos(): List<Repository>
    suspend fun fetchUserInfo(username: String): User?
    suspend fun fetchUserGitRepos(reposUrl: String): List<Repository>
}