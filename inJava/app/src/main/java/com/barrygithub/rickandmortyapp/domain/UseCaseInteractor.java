package com.barrygithub.rickandmortyapp.domain;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.Episode;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 7/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public interface  UseCaseInteractor {
    void getData(int page);
    Observable<EntityDb> observerData();
    Single<Episode> getEpisodeFromApi(int idEpisode);
}
