package com.barrygithub.rickandmortyapp.data.remoteDatasource

import com.barrygithub.rickandmortyapp.common.Constants
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.EntityApi
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.Episode
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
interface ApiClient {
    @GET(Constants.CHARACTER)
    fun getDataFromApi(@Query("page") page: Int): Single<EntityApi>

    @GET(Constants.EPISODE + "{idEpisode}")
    fun getEpisodeFromApi(@Path("idEpisode") idEpisode: Int): Single<Episode>
}