package com.example.github.repositories.core.usecases

import com.example.github.repositories.core.data.repositories.GitRepository
import com.example.github.repositories.core.domain.Repository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchReposUseCase @Inject constructor(private val gitRepository: GitRepository) {
    suspend operator fun invoke(): List<Repository> {
        return gitRepository.fetchGitRepos()
    }
}