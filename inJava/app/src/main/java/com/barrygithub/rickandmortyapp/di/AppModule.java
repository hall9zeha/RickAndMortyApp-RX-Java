package com.barrygithub.rickandmortyapp.di;

import android.app.Application;

import androidx.room.Room;

import com.barrygithub.rickandmortyapp.common.Constants;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.MyDAO;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.MyDatabase;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.ApiClient;
import com.barrygithub.rickandmortyapp.data.Repository;
import com.barrygithub.rickandmortyapp.data.RepositoryImpl;
import com.barrygithub.rickandmortyapp.domain.UseCaseInteractor;
import com.barrygithub.rickandmortyapp.domain.UseCaseInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public MyDAO databaseBuilder(Application app) {
        return Room.databaseBuilder(app.getApplicationContext(),
                        MyDatabase.class,
                        Constants.DATABASE_NAME
                ).fallbackToDestructiveMigration()
                 .build().myDAO()
                ;
    }

    @Singleton
    @Provides
    public Repository repositoryProvide(ApiClient apiClient, MyDAO db){return new RepositoryImpl(apiClient, db);}

    @Singleton
    @Provides
    public UseCaseInteractor interactorProvide(Repository repository) {return new UseCaseInteractorImpl(repository);}

}
