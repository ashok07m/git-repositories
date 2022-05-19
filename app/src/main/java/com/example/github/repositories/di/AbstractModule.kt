package com.example.github.repositories.di

import com.example.github.repositories.core.data.local.LocalDataStore
import com.example.github.repositories.core.data.local.LocalDataStoreImpl
import com.example.github.repositories.core.data.remote.GitReposRemoteSource
import com.example.github.repositories.core.data.remote.GitReposRemoteSourceImpl
import com.example.github.repositories.core.data.repositories.BookmarksRepository
import com.example.github.repositories.core.data.repositories.BookmarksRepositoryImpl
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
    abstract fun bindBookmarksRepository(impl: BookmarksRepositoryImpl): BookmarksRepository

    @Binds
    @Singleton
    abstract fun bindGitReposRemoteSource(impl: GitReposRemoteSourceImpl): GitReposRemoteSource

    @Binds
    @Singleton
    abstract fun bindLocalDataStore(impl: LocalDataStoreImpl): LocalDataStore

}