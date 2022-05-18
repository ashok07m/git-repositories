package com.example.github.repositories.core.usecases

import com.example.github.repositories.core.data.repositories.GitRepository
import com.example.github.repositories.core.domain.User
import javax.inject.Inject

class FetchUserInfoUseCase @Inject constructor(private val gitRepository: GitRepository) {

    suspend operator fun invoke(username: String): User? {
        return gitRepository.fetchUserInfo(username)
    }
}