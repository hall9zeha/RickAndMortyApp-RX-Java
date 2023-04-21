package com.barrygithub.rickandmortyapp.data;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public interface Repository {

    Single<String> getDataFromApiGraphql(int page);
    void saveMetadataInDb(MetaData metadata);
    void saveCharactersInDb(List<Character> characters);
    Single<EntityDb> getDataFromDb();

}
