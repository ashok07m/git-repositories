package com.example.github.repositories.core.data.mappers

import com.example.github.repositories.core.domain.Owner
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.response.RepositoryDTO
import com.example.github.repositories.core.domain.User
import com.example.github.repositories.core.domain.response.OwnerDTO
import com.example.github.repositories.core.domain.response.UserDTO

fun RepositoryDTO.toRepository(): Repository {
    return Repository(
        id = this.id,
        name = this.name,
        full_name = this.full_name,
        private = this.private,
        owner = this.owner?.toOwner(),
        html_url = this.html_url,
        description = this.description,
        created_at = this.created_at
    )
}

fun List<RepositoryDTO>.toRepositories(): List<Repository> {
    return this.map {
        it.toRepository()
    }
}

fun UserDTO.toUser(): User {
    return User(
        id = this.id,
        repos_url = this.repos_url,
        twitter_username = this.twitter_username,
    )
}

fun OwnerDTO.toOwner(): Owner {
    return Owner(
        id = this.id,
        login = this.login,
        avatar_url = this.avatar_url
    )
}

fun Owner.toOwnerDTO(): OwnerDTO {
    return OwnerDTO(
        id = this.id,
        login = this.login,
        avatar_url = this.avatar_url
    )
}

fun Repository.toRepositoryDTO(): RepositoryDTO {
    return RepositoryDTO(
        id = this.id,
        name = this.name,
        full_name = this.full_name,
        private = this.private,
        owner = this.owner?.toOwnerDTO(),
        html_url = this.html_url,
        description = this.description,
        created_at = this.created_at
    )
}