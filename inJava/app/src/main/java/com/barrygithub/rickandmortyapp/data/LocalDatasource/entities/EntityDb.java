package com.barrygithub.rickandmortyapp.data.LocalDatasource.entities;

import androidx.room.Embedded;
import androidx.room.Relation;


import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.CharacterApi;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.EntityApi;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.Episode;

import java.util.ArrayList;
import java.util.List;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 5/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/

public class EntityDb {
    @Embedded MetaData info = new MetaData();
    @Relation(
            parentColumn = "idMetadata",
            entityColumn = "idMetadata",
            entity =  Character.class
    )
    List<Character> results = new ArrayList<>();

    public EntityDb() {

    }

    public MetaData getInfo() {
        return info;
    }

    public void setInfo(MetaData info) {
        this.info = info;
    }

    public List<Character> getResults() {
        return results;
    }

    public void setResults(List<Character> results) {
        this.results = results;
    }

    public static EntityDb copyFromResponse(EntityApi entityApi){
        EntityDb entityDb= new EntityDb();
        MetaData meta= new MetaData();
        List<Character> characters= new ArrayList<>();
        meta.setCount(entityApi.getMetaData().getCount());
        meta.setPages(entityApi.getMetaData().getPages());

        for(CharacterApi c:entityApi.getResults() ){
            Character ct=new Character();
            ct.setId(c.getId());
            ct.setType(c.getType());
            ct.setStatus(c.getStatus());
            ct.setName(c.getName());
            ct.setGender(c.getGender());
            ct.setSpecies(c.getSpecies());
            ct.setImage(c.getImage());
            ct.setEpisodes(c.getEpisode());
            characters.add(ct);
        }

        entityDb.setInfo(meta);
        entityDb.setResults(characters);

        return entityDb;
    }
}
