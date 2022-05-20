package com.example.github.repositories.core.data.remote

import com.example.github.repositories.core.domain.response.RepositoryDTO
import com.example.github.repositories.core.domain.response.ResponseDTO
import com.example.github.repositories.core.domain.response.UserDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubEndpoints {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Response<ResponseDTO>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): Response<UserDTO>

    @GET
    suspend fun getUserRepositories(
        @Url userRepo: String
    ): Response<MutableList<RepositoryDTO>>
}