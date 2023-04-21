package com.barrygithub.rickandmortyapp.data.RemoteDatasource;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public interface ApiClient {
     @Headers("Content-Type: application/json")
    @POST("graphql")
    Single<String> getDataFromApiGraphql(@Body String body);
}
