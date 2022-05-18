package com.example.github.repositories.core.domain.response

import com.example.github.repositories.core.domain.response.RepositoryDTO

data class ResponseDTO(
    val total_count: String,
    val incomplete_results: Boolean,
    val items: MutableList<RepositoryDTO>
)