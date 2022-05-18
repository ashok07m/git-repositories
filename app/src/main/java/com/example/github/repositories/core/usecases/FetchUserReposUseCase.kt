package com.example.github.repositories.core.usecases

import com.example.github.repositories.core.data.repositories.GitRepository
import com.example.github.repositories.core.domain.Repository
import javax.inject.Inject

class FetchUserReposUseCase @Inject constructor(private val gitRepository: GitRepository) {

    suspend operator fun invoke(reposUrl: String): List<Repository> {
        return gitRepository.fetchUserGitRepos(reposUrl)
    }
}