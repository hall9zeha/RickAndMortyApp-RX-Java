package com.barrygithub.rickandmortyapp.data.remoteDatasource

import com.barrygithub.rickandmortyapp.data.localDatasource.Character
import com.barrygithub.rickandmortyapp.data.localDatasource.MetaData
import com.barrygithub.rickandmortyapp.data.localDatasource.MyDAO
import com.barrygithub.rickandmortyapp.graphql.getCharacterQuery
import io.reactivex.rxjava3.core.Single
import org.json.JSONObject

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
class RepositoryImpl(private val api:ApiClient, private val db:MyDAO) : Repository{

    //gracias a la query de graphQl nos deveolverá una lista de capítulos de cada personaje
    //y no una lista de enlaces como en la rest API, así que ya no necesitaremos llamar a cada enlace de episodio
    //para obtener sus campos

    override fun getDataFromGraphql(page: Int): Single<String> {
        //Convertimos nuestra query en un objeto tipo json para la peticion a la api graphql
        val paramObject = JSONObject()
        paramObject.put("query", getCharacterQuery(page))
        return api.getDataFromApiGraphQl(paramObject.toString())

    }

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

}