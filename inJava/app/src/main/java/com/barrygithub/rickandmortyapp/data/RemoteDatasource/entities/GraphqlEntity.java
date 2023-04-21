package com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Episode;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 21/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public class GraphqlEntity {
    @SerializedName("data")Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        @SerializedName("characters") Characters characters;

        public Characters getCharacters() {
            return characters;
        }

        public void setCharacters(Characters characters) {
            this.characters = characters;
        }
    }
    public static class Characters{
        @SerializedName("info") Info info;
        @SerializedName("results") List<CharacterApi> results;

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public List<CharacterApi> getResults() {
            return results;
        }

        public void setResults(List<CharacterApi> results) {
            this.results = results;
        }
    }
    public static class Info{
        @SerializedName("count") int count;
        @SerializedName("pages") int pages;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }
    }
    public static class CharacterApi{
        @SerializedName("id")int id;
        @SerializedName("name")String name;
        @SerializedName("status")String status;
        @SerializedName("species")String species;
        @SerializedName("gender")String gender;
        @SerializedName("type")String type;
        @SerializedName("image")String image;
        @SerializedName("episode")List<EpisodeApi> episodes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<EpisodeApi> getEpisodes() {
            return episodes;
        }

        public void setEpisodes(List<EpisodeApi> episodes) {
            this.episodes = episodes;
        }
    }
    public static class EpisodeApi{
        @SerializedName("id")int id;
        @SerializedName("name") String name;
        @SerializedName("air_date")String airDate;
        @SerializedName("episode") String episode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    public static EntityDb convertToEntityDb(GraphqlEntity entity){
        EntityDb entityDb= new EntityDb();
        com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData meta= new com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData();
        List<Character> characters= new ArrayList<>();
        meta.setCount(entity.getData().getCharacters().getInfo().getCount());
        meta.setPages(entity.getData().getCharacters().getInfo().getPages());

        for(CharacterApi c :entity.getData().getCharacters().getResults()){
            ArrayList<Episode> episodesList= new ArrayList<>();
            Character ct=new Character();

            ct.setId(c.getId());
            ct.setType(c.getType());
            ct.setStatus(c.getStatus());
            ct.setName(c.getName());
            ct.setGender(c.getGender());
            ct.setSpecies(c.getSpecies());
            ct.setImage(c.getImage());
            for(EpisodeApi ep:c.getEpisodes()){
                Episode e = new Episode();

                e.setId(ep.getId());
                e.setName(ep.getName());
                e.setAirDate(ep.getAirDate());
                e.setEpisode(ep.getEpisode());
                episodesList.add(e);
            }
            ct.setEpisodes(episodesList);
            characters.add(ct);
        }

        entityDb.setInfo(meta);
        entityDb.setResults(characters);


        return entityDb;
    }
}
