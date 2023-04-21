package com.barrygithub.rickandmortyapp.data.remoteDatasource.entities

import com.barrygithub.rickandmortyapp.data.localDatasource.Character
import com.barrygithub.rickandmortyapp.data.localDatasource.EntityDb
import com.google.gson.annotations.SerializedName

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 20/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/

data class GraphqlEntity(
    @SerializedName("data")var data: Data
)
data class Data(
    @SerializedName("characters") var characters: Characters
)
data class Characters(
    @SerializedName("info")var info: MetaData,
    @SerializedName("results")var results: List<CharacterApi>
)
data class MetaData(
   @SerializedName("count")var count:Int,
   @SerializedName("pages")var pages:Int
)

data class CharacterApi(
    @SerializedName("id")var id:Int,
    @SerializedName("name")var name:String,
    @SerializedName("status")var status:String,
    @SerializedName("species")var species:String,
    @SerializedName("gender")var gender:String,
    @SerializedName("type")var type:String,
    @SerializedName("image")var image:String,
    @SerializedName("episode")var episode:List<Episode>
)
data class Episode(
    @SerializedName("id")var id:Int,
    @SerializedName("name") var name:String,
    @SerializedName("air_date")var airDate:String,
    @SerializedName("episode") var episode:String
)

fun GraphqlEntity.convertToEntityDb() = EntityDb(
    info = com.barrygithub.rickandmortyapp.data.localDatasource.MetaData(
        count = data.characters.info.count,
        pages = data.characters.info.pages
    ),
    results = data.characters.results.map { Character(
        id=it.id,
        name=it.name,
        status=it.status,
        species = it.species, gender = it.gender,
        type = it.type,
        image = it.image,
        episodes = it.episode.map { ep->
            com.barrygithub.rickandmortyapp.data.localDatasource.Episode(id=ep.id,
                name=ep.name,
                airDate = ep.airDate,
                episode = ep.episode)
        }) }

)

