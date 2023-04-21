package com.barrygithub.rickandmortyapp.data.LocalDatasource.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 21/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public class Episode  implements Parcelable {

      int id;
      String name;
      String airDate;
      String episode;

    protected Episode(Parcel in) {
        id = in.readInt();
        name = in.readString();
        airDate = in.readString();
        episode = in.readString();
    }

    public Episode() {
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(airDate);
        parcel.writeString(episode);
    }
}
