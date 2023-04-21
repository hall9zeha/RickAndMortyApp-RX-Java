package com.barrygithub.rickandmortyapp.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.util.Log;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.GraphqlEntity;
import com.barrygithub.rickandmortyapp.data.RepositoryImpl;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.mockk.impl.annotations.InjectMockKs;
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import io.reactivex.rxjava3.subscribers.TestSubscriber;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 12/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/

public class UseCaseInteractorImplTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Mock
    public RepositoryImpl repository;


    private UseCaseInteractorImpl useCaseInteractor;

    @Before
    public void setUp(){

        RxJavaPlugins.reset();
        MockitoAnnotations.initMocks(this);
        useCaseInteractor = new UseCaseInteractorImpl(repository);
    }

    @Test
    public void loadResponseFromApiIfConnectionIsSuccessAndSaveToDataBase(){
        //Simulamos la respuesta de la API graphql
        final String response = " {\"data\":{\"characters\":{\"info\":{\"count\":826,\"pages\":42},\"results\":[{\"id\":\"1\",\"name\":\"Rick Sanchez\",\"status\":\"Alive\",\"species\":\"Human\",\"gender\":\"Male\",\"type\":\"\",\"image\":\"https://rickandmortyapi.com/api/character/avatar/1.jpeg\",\"episode\":[{\"id\":\"1\",\"episode\":\"S01E01\",\"name\":\"Pilot\",\"air_date\":\"December 2, 2013\"}]}]}}}";

        //luego la convertimos en el modelo de datos que necesitamos que es EntityDb, 
        Gson gson = new Gson();
        EntityDb entityDbModel  = GraphqlEntity.convertToEntityDb(gson.fromJson(response, GraphqlEntity.class));

        //given
        Mockito.doReturn(Single.just(response)).when(repository).getDataFromApiGraphql(anyInt());

        //when
        useCaseInteractor.getData(anyInt());

        //then

        //ponemos el tipo de objeto como any(), ya que no nos importa realmente la exactitud del objeto que reciba
        //también porque al invocar al método real la instancia de las entidades a guardar serán diferentes y lanzará
        //una excepción -> "los argumentos son diferentes"
        Mockito.verify(repository,times(1)).saveMetadataInDb(any(MetaData.class));
        Mockito.verify(repository,times(1)).saveCharactersInDb(any(List.class));

    }

    @Test
    public void callDatabaseIfResponseFromApiIsNullOrThrowable() throws Exception {

        //given
        Mockito.when(repository.getDataFromApiGraphql(anyInt())).thenReturn(
                Single.error(new Throwable("Unreachable host"))
        );
        //when
        useCaseInteractor.getData(anyInt());
        //then
        /* todo corregir la verificación del método getDataFromDb, genera un punto de excepción nulo porque la función devuelve un observable
         * pero aún así el test aparentemente esta pasando la comprobación
         */
        Mockito.verify(repository,times(1)).getDataFromDb();


    }
    @After
    public void after(){
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}