package com.barrygithub.rickandmortyapp.data.RemoteDatasource;

import com.barrygithub.rickandmortyapp.common.Constants;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.EntityApi;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.Episode;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public interface ApiClient {
    @GET(Constants.CHARACTER)
    Single<EntityApi> getDataFromApi(@Query("page") int page);

    @GET(Constants.EPISODE + "{idEpisode}")
    Single<Episode> getEpisodeFromApi(@Path("idEpisode") int idEpisode);
}
