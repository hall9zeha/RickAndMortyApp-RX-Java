package com.barrygithub.rickandmortyapp.data.LocalDatasource.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.util.TableInfo;

import com.google.gson.annotations.SerializedName;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 5/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
@Entity(indices = {@Index(value={"id"},unique = true)},
        foreignKeys = {
                @ForeignKey(entity = MetaData.class,
                parentColumns = {"idMetadata"},
                childColumns = {"idMetadata"},
                onDelete = ForeignKey.CASCADE
                )
        }
)
public class Character  implements Parcelable{

    @ColumnInfo(index = true)
    private  long idMetadata;
    @PrimaryKey
    private int id;
    private String name;
    private String status;
    private String species;
    private String gender;
    private String type;
    private String image;

    protected Character(Parcel in) {
        idMetadata = in.readLong();
        id = in.readInt();
        name = in.readString();
        status = in.readString();
        species = in.readString();
        gender = in.readString();
        type = in.readString();
        image = in.readString();
        episodes = in.createTypedArrayList(Episode.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idMetadata);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(status);
        dest.writeString(species);
        dest.writeString(gender);
        dest.writeString(type);
        dest.writeString(image);
        dest.writeTypedList(episodes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return id == character.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Ignore
    private List<Episode> episodes;

    public Character() {
    }



    public long getIdMetadata() {
        return idMetadata;
    }

    public void setIdMetadata(long idMetadata) {
        this.idMetadata = idMetadata;
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

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
