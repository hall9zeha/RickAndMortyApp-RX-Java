package com.barrygithub.rickandmortyapp;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
@HiltAndroidApp
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
