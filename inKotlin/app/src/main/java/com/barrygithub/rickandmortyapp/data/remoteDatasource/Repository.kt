package com.barrygithub.rickandmortyapp.data.remoteDatasource


import com.barrygithub.rickandmortyapp.data.localDatasource.*
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.Episode
import io.reactivex.rxjava3.core.Single

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
interface Repository {

    fun getDataFromApi(page: Int): Single<EntityDb>
    fun saveMetadataInDb(metadata: MetaData)
    fun saveCharactersInDb(characters: List<Character>)
    fun getDataFromDb(): Single<EntityDb>
    fun getEpisode(idEpisode: Int): Single<Episode>
}