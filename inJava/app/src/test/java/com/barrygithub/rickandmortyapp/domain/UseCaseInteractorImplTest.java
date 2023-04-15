package com.barrygithub.rickandmortyapp.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData;
import com.barrygithub.rickandmortyapp.data.RepositoryImpl;

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

        EntityDb entity= new EntityDb();
        MetaData info = new MetaData();
        info.setIdMetadata(1);
        info.setPages(42);
        info.setCount(820);

        entity.setInfo(info);
        Character c= new Character();
        c.setIdMetadata(1);
        c.setEpisodes(new ArrayList<>());
        c.setId(12);
        c.setImage("");
        c.setGender("Male");
        c.setName("Morty");
        c.setSpecies("human");
        c.setStatus("alive");
        c.setType("normal");

        entity.setResults(new ArrayList<>(Collections.singletonList(c)));

        //given
        Mockito.doReturn(Single.just(entity)).when(repository).getDataFromApi(anyInt());
        //when
        useCaseInteractor.getData(anyInt());
        //then
        Mockito.verify(repository,times(1)).saveMetadataInDb(info);
        Mockito.verify(repository,times(1)).saveCharactersInDb(entity.getResults());

    }

    @Test
    public void callDatabaseIfResponseFromApiIsNullOrThrowable() throws Exception {

        //given
        Mockito.when(repository.getDataFromApi(anyInt())).thenReturn(
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