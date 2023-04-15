package com.barrygithub.rickandmortyapp.di

import android.app.Application
import androidx.room.Room
import com.barrygithub.rickandmortyapp.common.Constants
import com.barrygithub.rickandmortyapp.data.localDatasource.MyDAO
import com.barrygithub.rickandmortyapp.data.localDatasource.MyDatabase
import com.barrygithub.rickandmortyapp.data.remoteDatasource.ApiClient
import com.barrygithub.rickandmortyapp.data.remoteDatasource.Repository
import com.barrygithub.rickandmortyapp.data.remoteDatasource.RepositoryImpl
import com.barrygithub.rickandmortyapp.domain.UseCaseInteractor
import com.barrygithub.rickandmortyapp.domain.UseCaseInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun databaseBuilder(app:Application):MyDAO{
        return Room.databaseBuilder(
            app.applicationContext,
            MyDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
            .myDAO()
    }

    @Singleton
    @Provides
    fun repositoryProvides(apiClient:ApiClient, db:MyDAO):Repository = RepositoryImpl(apiClient,db)

    @Provides
    fun useCaseProvides(repository: Repository):UseCaseInteractor = UseCaseInteractorImpl(repository)


}