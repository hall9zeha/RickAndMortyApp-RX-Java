package com.barrygithub.rickandmortyapp.data.LocalDatasource.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

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


}
