package com.example.appsdev.Home.LiveData.Data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleLiveData {

    @Binds
    abstract fun MinisterioRepo(repo: UsuarioModelRepositoryImpl): UsuarioModelRepository
}