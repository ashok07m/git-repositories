package com.example.github.repositories.core.data.remote

import com.example.github.repositories.core.domain.response.RepositoryDTO
import com.example.github.repositories.core.domain.response.ResponseDTO
import com.example.github.repositories.core.domain.response.UserDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubEndpoints {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Call<ResponseDTO>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<UserDTO>

    @GET
    fun getUserRepositories(
        @Url userRepo: String
    ): Call<MutableList<RepositoryDTO>>
}