package com.barrygithub.rickandmortyapp.domain

import com.barrygithub.rickandmortyapp.data.localDatasource.Character
import com.barrygithub.rickandmortyapp.data.localDatasource.EntityDb
import com.barrygithub.rickandmortyapp.data.localDatasource.MetaData
import com.barrygithub.rickandmortyapp.data.remoteDatasource.RepositoryImpl
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 */

class UseCaseInteractorImplTest{
    @Mock
    lateinit  var repository: RepositoryImpl

    private lateinit  var useCaseInteractor: UseCaseInteractorImpl

    @Before
    fun setUp(){
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
        MockitoAnnotations.initMocks(this)
        useCaseInteractor = UseCaseInteractorImpl(repository)
    }

    @Test
    fun `load Response From Api If Connection Is Success And Save To DataBase`() {

        val  characters:List<Character> = arrayListOf(Character(
            id = 1,
            name = "Morty", status = "alive", gender = "male", species = "human",
        ))
        val metaData = MetaData( count = 840, pages = 42)
        val entity= EntityDb(info=metaData, results = characters)

        //Given
        doReturn(Single.just(entity)).`when`(repository).getDataFromApi(anyInt())

        //When
        useCaseInteractor.getData(anyInt())

        //Then
        verify(repository,times(1)).saveMetadataInDb(metaData)
        verify(repository,times(1)).saveCharactersInDb(characters)
    }

    @Test
    fun `if An API Call Returns An Exception call The Database`(){

        //Given
        `when`(repository.getDataFromApi(anyInt())).thenReturn(
            Single.error(Throwable("unreachable host"))
        )
        //When
        useCaseInteractor.getData(anyInt())
        //Then
        //todo mejorar la estructura de casos de uso interactor para verificar la llamada a la base de datos
        //con el test devuelve null ya que getDataFromDb()  devuelve un singleObservable
        verify(repository,times(1)).getDataFromDb()
    }

    @After
    fun after(){
        RxJavaPlugins.reset()

    }
}