package com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public class CharacterApi implements Parcelable {
    private @SerializedName("id") int id;
    private @SerializedName("name") String name;
    private @SerializedName("status") String status;
    private @SerializedName("species") String species;
    private @SerializedName("gender") String gender;
    private @SerializedName("type")String type;
    private @SerializedName("image")String image;
    private @SerializedName("episode")List<String> episode;

    protected CharacterApi(Parcel in) {
        id = in.readInt();
        name = in.readString();
        status = in.readString();
        species = in.readString();
        gender = in.readString();
        type = in.readString();
        image = in.readString();
        episode = in.createStringArrayList();
    }

    public static final Creator<CharacterApi> CREATOR = new Creator<CharacterApi>() {
        @Override
        public CharacterApi createFromParcel(Parcel in) {
            return new CharacterApi(in);
        }

        @Override
        public CharacterApi[] newArray(int size) {
            return new CharacterApi[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterApi characterApi = (CharacterApi) o;
        return id == characterApi.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getEpisode() {
        return episode;
    }

    public void setEpisode(List<String> episode) {
        this.episode = episode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(status);
        parcel.writeString(species);
        parcel.writeString(gender);
        parcel.writeString(type);
        parcel.writeString(image);
        parcel.writeStringList(episode);
    }
}
