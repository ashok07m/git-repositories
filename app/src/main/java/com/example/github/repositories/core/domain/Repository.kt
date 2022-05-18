package com.example.github.repositories.core.domain

import com.example.github.repositories.core.domain.response.OwnerDTO

data class Repository(
    val id: Int?,
    var name: String?,
    val full_name: String?,
    val private: Boolean?,
    val owner: OwnerDTO?,
    val html_url: String?,
    val description: String?,
    var created_at: String?,
)