package com.barrygithub.rickandmortyapp.data.RemoteDatasource;

import com.barrygithub.rickandmortyapp.common.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
@Module
@InstallIn(SingletonComponent.class)
public class ApiServiceImpl implements ApiService {

    OkHttpClient.Builder clientHttp = new OkHttpClient.Builder();

    private Retrofit retrofitBuilder(){

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_GRAPHQL_URL)
                .addConverterFactory(ScalarsConverterFactory.create())//ScalarConvert for graphql url
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Override
    public ApiClient getApiService() {
        return retrofitBuilder().create(ApiClient.class);
    }
}
