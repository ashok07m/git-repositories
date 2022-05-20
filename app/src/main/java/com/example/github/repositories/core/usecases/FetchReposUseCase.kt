package com.example.github.repositories.core.usecases

import com.example.github.repositories.core.data.repositories.GitRepository
import com.example.github.repositories.core.domain.ApiResult
import com.example.github.repositories.core.domain.Repository
import javax.inject.Inject

class FetchReposUseCase @Inject constructor(private val gitRepository: GitRepository) {
    suspend operator fun invoke(): ApiResult<List<Repository>> {
        return gitRepository.fetchGitRepos()
    }
}