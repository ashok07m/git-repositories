package com.example.github.repositories.core.data.repositories

import com.example.github.repositories.core.data.remote.GitReposRemoteSource
import com.example.github.repositories.core.domain.ApiResult
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User
import javax.inject.Inject

class GitRepositoryImpl @Inject constructor(
    private val gitReposRemoteSource: GitReposRemoteSource
) : GitRepository {

    override suspend fun fetchGitRepos(): ApiResult<List<Repository>> {
        return gitReposRemoteSource.fetchGitRepos()
    }

    override suspend fun fetchUserInfo(username: String): ApiResult<User?> {
        return gitReposRemoteSource.fetchUserInfo(username)
    }

    override suspend fun fetchUserGitRepos(reposUrl: String): ApiResult<List<Repository>> {
        return gitReposRemoteSource.fetchUserRepos(reposUrl)
    }

}