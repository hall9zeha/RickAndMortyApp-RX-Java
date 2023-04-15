package com.barrygithub.rickandmortyapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
@HiltAndroidApp
class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}