package com.barrygithub.rickandmortyapp.data;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.MyDAO;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.ApiClient;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.Episode;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/

public class RepositoryImpl  implements Repository{
    ApiClient apiClient;
    MyDAO db;
    public RepositoryImpl(ApiClient apiClient, MyDAO db){
        this.apiClient=apiClient;
        this.db= db;
    }

    @Override
    public Single<EntityDb> getDataFromApi(int page) {
       return apiClient.getDataFromApi(page).map(EntityDb::copyFromResponse);
   }
    @Override
    public Single<Episode> getEpisode(int idEpisode) {
        return apiClient.getEpisodeFromApi(idEpisode);
    }
    @Override
    public Single<EntityDb> getDataFromDb() {
        return  db.getDataFromDatabase();

    }

    @Override
    public void saveMetadataInDb(MetaData metadata) {
        //seteamos manualmente el id aunque esté como autogenrado en room
        //lo hacemos para que no vuelva a insertar un nuevo registro y reemplace el mismo que ya tenemos la primera vez
        //y que el método de insertar tiene como anotación OnConflictStrategy.REPLACE
        //Otra opción es  guardar solamente la lista de personajes (saveCharacters) obviando metadata solo para la API
        metadata.setIdMetadata(1);
        db.saveMetadata(metadata);
    }

    @Override
    public void saveCharactersInDb(List<Character> characters) {
      db.saveCharacters(characters);
    }


}
