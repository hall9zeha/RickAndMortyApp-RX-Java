package com.barrygithub.rickandmortyapp.data.remoteDatasource

import com.barrygithub.rickandmortyapp.data.localDatasource.*
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.Episode
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.toDatabase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
class RepositoryImpl(private val api:ApiClient, private val db:MyDAO) : Repository{

    override fun getDataFromApi(page: Int): Single<EntityDb> = api.getDataFromApi(page).map { it.toDatabase()}
    override fun saveMetadataInDb(metadata: MetaData) {
        //seteamos manualmente el id aunque esté como autogenrado en room
        //lo hacemos para que no vuelva a insertar un nuevo registro y reemplace el mismo que ya tenemos la primera vez
        //y que el método de insertar tiene como anotación OnConflictStrategy.REPLACE
        //Otra opción es  guardar solamente la lista de personajes (saveCharacters) obviando metadata solo para la API

        metadata.idMetadata =1
        db.saveMetadata(metadata);
    }

    override fun saveCharactersInDb(characters: List<Character>) {
        db.saveCharacters(characters)
    }

    override fun getDataFromDb() = db.getDataFromDatabase()
    override fun getEpisode(idEpisode: Int) = api.getEpisodeFromApi(idEpisode)
}