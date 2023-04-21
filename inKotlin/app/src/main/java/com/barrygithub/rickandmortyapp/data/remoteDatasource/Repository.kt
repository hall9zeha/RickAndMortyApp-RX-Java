package com.barrygithub.rickandmortyapp.data.remoteDatasource


import com.barrygithub.rickandmortyapp.data.localDatasource.Character
import com.barrygithub.rickandmortyapp.data.localDatasource.EntityDb
import com.barrygithub.rickandmortyapp.data.localDatasource.MetaData
import io.reactivex.rxjava3.core.Single

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
interface Repository {
    fun getDataFromGraphql(page:Int):Single<String>
    fun saveMetadataInDb(metadata: MetaData)
    fun saveCharactersInDb(characters: List<Character>)
    fun getDataFromDb(): Single<EntityDb>
}