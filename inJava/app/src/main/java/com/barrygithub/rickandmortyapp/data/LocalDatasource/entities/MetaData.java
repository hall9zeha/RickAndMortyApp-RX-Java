package com.barrygithub.rickandmortyapp.data.LocalDatasource.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 5/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
@Entity
public class MetaData {
    @PrimaryKey(autoGenerate = true) private long idMetadata;
    private int count;
    private int pages;

    public MetaData() {
    }

    public long getIdMetadata() {
        return idMetadata;
    }

    public void setIdMetadata(long idMetadata) {
        this.idMetadata = idMetadata;
    }

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
