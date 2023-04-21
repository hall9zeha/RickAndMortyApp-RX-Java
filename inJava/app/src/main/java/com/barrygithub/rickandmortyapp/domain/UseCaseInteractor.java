package com.barrygithub.rickandmortyapp.domain;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;

import io.reactivex.rxjava3.core.Observable;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 7/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public interface  UseCaseInteractor {
    void getData(int page);
    Observable<EntityDb> observerData();

}
