package com.barrygithub.rickandmortyapp.domain

import com.barrygithub.rickandmortyapp.data.localDatasource.EntityDb
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.Episode
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
interface UseCaseInteractor {
    fun getData(page:Int)
    fun getObservableData():BehaviorSubject<EntityDb>
    fun getEpisodeFromApi(idEpisode:Int): Single<Episode>
}