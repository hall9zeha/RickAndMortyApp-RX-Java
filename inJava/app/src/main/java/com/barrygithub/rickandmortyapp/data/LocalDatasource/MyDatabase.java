package com.barrygithub.rickandmortyapp.data.LocalDatasource;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData;


/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 5/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
@Database(entities = {Character.class, MetaData.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract MyDAO myDAO();
}
