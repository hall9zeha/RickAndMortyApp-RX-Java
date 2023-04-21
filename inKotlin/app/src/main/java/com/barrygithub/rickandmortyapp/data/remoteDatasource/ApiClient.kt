package com.barrygithub.rickandmortyapp.data.remoteDatasource

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
interface ApiClient {

    //con graphql
    @Headers("Content-Type: application/json")
    @POST("graphql")
    fun getDataFromApiGraphQl(@Body body:String):Single<String>
}