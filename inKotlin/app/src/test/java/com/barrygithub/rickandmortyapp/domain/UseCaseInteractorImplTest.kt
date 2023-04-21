package com.barrygithub.rickandmortyapp.domain

import com.barrygithub.rickandmortyapp.data.remoteDatasource.RepositoryImpl
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.GraphqlEntity
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.convertToEntityDb
import com.google.gson.Gson
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

        val responseStringGraphql = """
            {"data":
                {"characters":
                    {"info":
                        {"count":826,"pages":42},
                        "results":[{"id":"1","name":"Rick Sanchez","status":"Alive","species":"Human","gender":"Male","type":"","image":"https://rickandmortyapi.com/api/character/avatar/1.jpeg","episode":[{"id":"1","episode":"S01E01","name":"Pilot","air_date":"December 2, 2013"}]
                        
                        }]}}}
        """
        val gson= Gson()
        val model = gson.fromJson(responseStringGraphql,GraphqlEntity::class.java).convertToEntityDb()

        //Given
        doReturn(Single.just(responseStringGraphql)).`when`(repository).getDataFromGraphql(anyInt())

        //When
        useCaseInteractor.getDataGraphql(anyInt())

        //Then
        verify(repository,times(1)).saveMetadataInDb(model.info)
        verify(repository,times(1)).saveCharactersInDb(model.results)
    }

    @Test
    fun `if An API Call Returns An Exception call The Database`(){

        //Given
        `when`(repository.getDataFromGraphql(anyInt())).thenReturn(
            Single.error(Throwable("unreachable host"))
        )
        //When
        useCaseInteractor.getDataGraphql(anyInt())
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