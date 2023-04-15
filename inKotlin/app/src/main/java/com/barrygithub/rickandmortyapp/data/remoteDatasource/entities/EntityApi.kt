package com.barrygithub.rickandmortyapp.data.remoteDatasource.entities

import com.barrygithub.rickandmortyapp.data.localDatasource.Character
import com.barrygithub.rickandmortyapp.data.localDatasource.EntityDb
import com.google.gson.annotations.SerializedName

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/

data class EntityApi(
    @SerializedName("info") var metaData:MetaData = MetaData(),
    @SerializedName("results") var results:List<CharacterApi> = arrayListOf()
)

data class MetaData(
    @SerializedName("count") var count:Int=0,
    @SerializedName("pages") var pages:Int=0
)

data class CharacterApi(
    @SerializedName("id") var id:Int=0,
    @SerializedName("name") var name:String,
    @SerializedName("status") var status:String="",
    @SerializedName("species")var species:String="",
    @SerializedName("gender") var gender:String="",
    @SerializedName("type") var type:String="",
    @SerializedName("image") var image:String="",
    @SerializedName("episode") var episode:List<String> = arrayListOf()
)
data class Episode(
    @SerializedName("name") var name:String="",
    @SerializedName("air_date")var airDate:String="",
    @SerializedName("episode") var episode:String=""
)

fun EntityApi.toDatabase() = EntityDb(
    info = com.barrygithub.rickandmortyapp.data.localDatasource.MetaData(
        count = metaData.count,
        pages = metaData.pages
    ),
    results = results.map { Character(
        id=it.id,
        name=it.name,
        status=it.status,
        species = it.species, gender = it.gender,
        type = it.type,
        image = it.image, episodes = it.episode) }

)


