package com.example.github.repositories.core.usecases

import android.content.Context
import com.example.github.repositories.R
import com.example.github.repositories.core.data.MAX_CHAR_COUNT
import com.example.github.repositories.core.data.repositories.GitRepository
import com.example.github.repositories.core.domain.ApiResult
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.di.DefaultDispatcher
import com.example.github.repositories.ui.utils.AppUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class FetchReposUseCase @Inject constructor(
    @ApplicationContext context: Context,
    private val gitRepository: GitRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    private val ellipses = context.getString(R.string.ellipses)

    suspend operator fun invoke(): ApiResult<List<Repository>> {
        return gitRepository.fetchGitRepos()
    }

    fun observeRepos(): SharedFlow<List<Repository>> {
        return gitRepository.gitReposSharedFlow
    }

    suspend fun transformListData(list: List<Repository>): List<Repository> =
        withContext(defaultDispatcher) {
            return@withContext list.map { repo ->
                repo.description = AppUtil.addEllipsesToDescriptionField(repo, ellipses)
                repo.full_name = repo.full_name?.uppercase(Locale.US)
                repo
            }
        }

}