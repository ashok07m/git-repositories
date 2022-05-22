package com.example.github.repositories.core.data.repositories

import com.example.github.repositories.core.data.remote.GitReposRemoteSource
import com.example.github.repositories.core.domain.ApiResult
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User
import com.example.github.repositories.di.DefaultDispatcher
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitRepositoryImpl @Inject constructor(
    private val gitReposRemoteSource: GitReposRemoteSource,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : GitRepository {

    private val gitReposList: MutableList<Repository> = mutableListOf()
    private val userReposList: MutableList<Repository> = mutableListOf()

    private val _gitReposSharedFlow: MutableSharedFlow<List<Repository>> = MutableSharedFlow()
    override val gitReposSharedFlow: SharedFlow<List<Repository>> = _gitReposSharedFlow

    private val _userReposSharedFlow: MutableSharedFlow<List<Repository>> = MutableSharedFlow()
    override val userReposSharedFlow: SharedFlow<List<Repository>> = _userReposSharedFlow

    override suspend fun fetchGitRepos(): ApiResult<List<Repository>> {
        val result = gitReposRemoteSource.fetchGitRepos()
        if (result is ApiResult.Success) {
            val updatedLst = updateBookmarksStatusInFreshData(result.data, gitReposList)
            gitReposList.clear()
            gitReposList.addAll(updatedLst)
            _gitReposSharedFlow.emit(gitReposList)
        }
        return result
    }

    override suspend fun fetchUserInfo(username: String): ApiResult<User?> {
        return gitReposRemoteSource.fetchUserInfo(username)
    }

    override suspend fun fetchUserGitRepos(reposUrl: String): ApiResult<List<Repository>> {
        val result = gitReposRemoteSource.fetchUserRepos(reposUrl)
        if (result is ApiResult.Success) {
            if (userReposList.isEmpty()) userReposList.addAll(gitReposList)
            val updatedLst = updateBookmarksStatusInFreshData(result.data, userReposList)
            userReposList.clear()
            userReposList.addAll(updatedLst)
            _userReposSharedFlow.emit(userReposList)
        }
        return result
    }

    override suspend fun updateBookmarkStatus(repository: Repository, status: Boolean) {
        var isListChanged = false
        coroutineScope {
            launch(defaultDispatcher) {
                // update bookmark status for git repos list
                gitReposList.indexOfFirst {
                    it.id == repository.id
                }.also {
                    if (it > -1) {
                        gitReposList[it].isBookMarked = status
                        isListChanged = true
                    }
                }
                if (isListChanged) {
                    _gitReposSharedFlow.emit(gitReposList)
                    isListChanged = false
                }

                // update bookmark status for user repos list
                userReposList.indexOfFirst {
                    it.id == repository.id
                }.also {
                    if (it > -1) {
                        userReposList[it].isBookMarked = status
                        isListChanged = true
                    }
                }
                if (isListChanged) {
                    _userReposSharedFlow.emit(userReposList)
                }

            }
        }
    }

    private suspend fun updateBookmarksStatusInFreshData(
        newList: List<Repository>,
        oldList: List<Repository>
    ): List<Repository> = withContext(defaultDispatcher) {
        newList.map { repo ->
            (oldList.find { it.id == repo.id })?.let {
                repo.isBookMarked = it.isBookMarked
            }
        }
        return@withContext newList
    }
}