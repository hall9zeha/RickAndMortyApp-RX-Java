package com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 10/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public class Episode {
    @SerializedName("name") String name;
    @SerializedName("air_date")String airDate;
    @SerializedName("episode")String episode;

    public Episode() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}
