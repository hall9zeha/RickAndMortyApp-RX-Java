package com.barrygithub.rickandmortyapp.data;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.Episode;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public interface Repository {

    Single<EntityDb> getDataFromApi(int page);
    void saveMetadataInDb(MetaData metadata);
    void saveCharactersInDb(List<Character> characters);
    Single<EntityDb> getDataFromDb();
    Single<Episode> getEpisode(int idEpisode);
}
