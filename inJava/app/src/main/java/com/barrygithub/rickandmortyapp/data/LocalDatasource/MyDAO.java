package com.barrygithub.rickandmortyapp.data.LocalDatasource;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 5/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
@Dao
public interface MyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Void saveMetadata(MetaData metadata);

    @Update
    Single<Integer> updateMetaData(MetaData metaData);

    @Insert
    Void saveCharacters(List<Character> characters);

    @Query("select * from MetaData")
    Single<List<MetaData>> getMetadata();


    @Query("select * from Character")
    Single<List<Character>> getCharacters();

    @Transaction
    @Query("select * from MetaData")
    Single<EntityDb> getDataFromDatabase();


}
