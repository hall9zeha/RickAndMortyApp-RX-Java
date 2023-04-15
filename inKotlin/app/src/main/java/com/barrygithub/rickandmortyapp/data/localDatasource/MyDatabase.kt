package com.barrygithub.rickandmortyapp.data.localDatasource

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
@Database(entities = [Character::class, MetaData::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun myDAO():MyDAO
}