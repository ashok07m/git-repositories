package com.example.github.repositories.di

import com.example.github.repositories.core.data.remote.GitHubEndpoints
import com.example.github.repositories.core.data.remote.GitReposRemoteSource
import com.example.github.repositories.core.data.remote.GitReposRemoteSourceImpl
import com.example.github.repositories.core.data.repositories.GitRepository
import com.example.github.repositories.core.data.repositories.GitRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {

    @Binds
    @Singleton
    abstract fun bindGitRepository(impl: GitRepositoryImpl): GitRepository

    @Binds
    @Singleton
    abstract fun bindGitReposRemoteSource(impl: GitReposRemoteSourceImpl): GitReposRemoteSource
}