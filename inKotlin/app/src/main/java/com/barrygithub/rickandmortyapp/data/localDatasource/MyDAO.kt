package com.barrygithub.rickandmortyapp.data.localDatasource

import androidx.room.*
import io.reactivex.rxjava3.core.Single

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
@Dao
interface MyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMetadata(metadata: MetaData)

    @Insert
    fun saveCharacters(characters: List<Character>)

    @Query("select * from Character")
    fun getCharacters(): Single<List<Character>>

    //con este método traemos la metadata como la lista de personajes relacionados
    @Transaction
    @Query("select * from MetaData")
    fun getDataFromDatabase(): Single<EntityDb>
}