package com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public class EntityApi implements Parcelable {
    private @SerializedName("info") MetaData metaData;
    private @SerializedName("results") List<CharacterApi> results;

    public EntityApi() {
    }

    protected EntityApi(Parcel in) {
        metaData = in.readParcelable(MetaData.class.getClassLoader());
        results = in.createTypedArrayList(CharacterApi.CREATOR);
    }

    public static final Creator<EntityApi> CREATOR = new Creator<EntityApi>() {
        @Override
        public EntityApi createFromParcel(Parcel in) {
            return new EntityApi(in);
        }

        @Override
        public EntityApi[] newArray(int size) {
            return new EntityApi[size];
        }
    };

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public List<CharacterApi> getResults() {
        return results;
    }

    public void setResults(List<CharacterApi> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(metaData, i);
        parcel.writeTypedList(results);
    }


}
